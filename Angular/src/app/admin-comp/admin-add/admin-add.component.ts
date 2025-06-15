import { HttpClient, HttpClientModule, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { ChangeDetectorRef, Component, ElementRef, ViewChild, viewChild } from '@angular/core';
import { UserService } from '../../services/user.service';
import { JwtServiceService } from '../../services/jwt-service.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { AdminDialogComponent } from '../admin-dialog/admin-dialog.component';

interface User{
  nom : string,
  prenom : string,
  email : string,
  tel : string,
  role : string
}
interface Error{
  nom : string,
  prenom : string,
  email : string,
  tel : string,
  role : string,
  error : string
}

@Component({
  selector: 'app-admin-add',
  imports: [CommonModule,FormsModule,HttpClientModule],
  templateUrl: './admin-add.component.html',
  styleUrl: './admin-add.component.css'
})
export class AdminAddComponent
{

  validationError :Error={nom : "",prenom : "", email : "",  tel : "",role : "",error : ""};
  user : User={nom : "",prenom : "", email : "",  tel : "",role : ""};
  selectedFile : File | null=null;
  role : string="";
  fileUrl="";
  profilError : {error : string}={error : ""};
  @ViewChild("profilIn") profilInput! : ElementRef;

  constructor(
    private httpClient : HttpClient,
    private userService : UserService,
    private tokenService : JwtServiceService,
    private cd : ChangeDetectorRef,
    private dialog : MatDialog
  ){}

  selectFile(event : Event)
  {
    let inputElt=event.target as HTMLInputElement;
    if(inputElt.files && inputElt.files.length>0)
    {
      this.selectedFile=inputElt.files[0];
      console.log(this.selectedFile.name);
      this.fileUrl=window.URL.createObjectURL(this.selectedFile);
    }
  }

  onClick()
  {
    this.profilInput.nativeElement.click();
  }


  onSubmit() 
  {
    if(this.role.trim()=="")
    {
      console.log("role "+this.role);
      let error="Cochez une fonction s'il vous plait";
      console.log(error);
      this.dialog.open(AdminDialogComponent,{
        data : {content:error},
        disableClose:true
      });
      this.cd.detectChanges();
    }
    else if(!this.selectedFile)
    {
      let error="Inserer Une Image De Profil";
      console.log(error);
      this.dialog.open(AdminDialogComponent,{
        data : {content:error},
        disableClose:true
      });
      this.cd.detectChanges();
    }
    else
    {
      this.addPersonnal();
    }
  }

  addPersonnal()
  {
    this.user.role=this.role;
      let roleToUrl=this.role.toLowerCase();
      let url="http://localhost:8080/"+roleToUrl+"/add";
      let token=this.tokenService.getToken();
      const options={
        headers : new HttpHeaders({
          "Authorization" : "Bearer "+token
        }),
        responseType : 'blob' as 'blob'
      };
      console.log("TOKEN "+token);
      console.log("URL : "+url);
      let formData=new FormData();
      formData.append(roleToUrl,JSON.stringify(this.user));
      if(this.selectedFile!=null)
      {
        formData.append("profil",this.selectedFile);
      }
      
      this.httpClient.post(url,formData,options).subscribe({
        next : (file) => {
          let url = window.URL.createObjectURL(file);
          console.log("TEMP URL IMAGE : "+this.fileUrl);
          window.open(url);
          window.URL.revokeObjectURL(url);
          this.user={nom : "",prenom : "", email : "",  tel : "",role : ""};
          window.URL.revokeObjectURL(this.fileUrl);
          this.fileUrl="";
          this.role="";
          console.log("URL IMAGE : "+this.fileUrl);
          this.validationError={nom : "",prenom : "", email : "",  tel : "",role : "",error : ""};
          this.profilInput.nativeElement.value="";
          this.selectedFile=null;
          this.cd.detectChanges();
        },
        error : (err : HttpErrorResponse) => {

          if(err.status == 400)
          {
            if(err.error)
            {
              const reader=new FileReader();
              reader.onload=()=>{
                let textResult=reader.result as string;
                let jsonResult: Error=JSON.parse(textResult);
                console.log(jsonResult);
                this.validationError=jsonResult;
                this.cd.detectChanges();
              }
              reader.readAsText(err.error);
              console.log(this.validationError);
              this.cd.detectChanges();
            }
          }
          if(err.status == 403)
          {
            let unAuthError={error : ""};
            const reader=new FileReader();
            reader.onload=()=>{
              let textResult=reader.result as string;
              let jsonResult: {error : string}=JSON.parse(textResult);
              console.log(jsonResult);
              this.dialog.open(AdminDialogComponent,{
              data : {content:jsonResult.error},
              disableClose:true
            });
              this.cd.detectChanges();
            }
            reader.readAsText(err.error);;
            
            this.cd.detectChanges();
          }
        }
      });
  }
}
