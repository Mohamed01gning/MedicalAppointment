import { CommonModule, DatePipe } from '@angular/common';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { ChangeDetectorRef, Component, Inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialog, MatDialogActions, MatDialogContent, MatDialogModule, MatDialogRef, MatDialogTitle } from '@angular/material/dialog';
import { JwtServiceService } from '../../services/jwt-service.service';
import { UserService } from '../../services/user.service';

interface User{
  idSec : string,
  idMed : string,
  nomPatient:string,
  prenomPatient:string,
  tel:string,
  date:string | null,
  time:string
}

interface ErrorRes{
  nomPatient:string,
  prenomPatient:string,
  tel:string
}

@Component({
  selector: 'app-add-rv-dialog',
  imports: [MatDialogModule,MatDialogTitle,MatDialogActions,MatDialogContent,CommonModule,FormsModule],
  providers : [DatePipe],
  templateUrl: './add-rv-dialog.component.html',
  styleUrl: './add-rv-dialog.component.css'
})
export class AddRvDialogComponent
{
  user: User={idSec:"",idMed:"",nomPatient :"",prenomPatient :"",tel:"",date:"",time:""};
  error : ErrorRes={nomPatient :"",prenomPatient :"",tel:""};

  constructor(
    private dialogRef : MatDialogRef<AddRvDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data : {idSec:string,idMed:string},
    private httpClient : HttpClient,
    private tokenService : JwtServiceService,
    private cd : ChangeDetectorRef,
    private dateFormatter : DatePipe,
  ){}

  onSubmit() 
  {
    this.addRv();
  }

  addRv()
  {
    this.user.idSec=this.data.idSec;
    this.user.idMed=this.data.idMed;
    this.user.date=this.parseDate(this.user.date);
    
    let token=this.tokenService.getToken();
    let url="http://localhost:8080/rv/add";
    const options={
      headers : new HttpHeaders({
        'Authorization' : 'Bearer '+token
      })
    };
    this.httpClient.post<{msg:string}>(url,this.user,options).subscribe({
      next : (res) => {
        console.log(res);

        this.dialogRef.close(res);
        this.cd.detectChanges();
      },
      error : (err : HttpErrorResponse) => {
        if(err.status == 400 && err.error)
        {
          console.log(err.error);
          this.error=err.error;
          console.log(this.error);
        }
      }
    });
  }

  parseDate(isoDate :string | null) : string | null
  {
    return this.dateFormatter.transform(isoDate,"dd-MM-yyyy");
  }

  onClose()
  {
    this.dialogRef.close();
  }

}
