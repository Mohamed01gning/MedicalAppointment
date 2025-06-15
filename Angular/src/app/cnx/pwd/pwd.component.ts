import { AfterViewInit, ChangeDetectorRef, Component, ElementRef, Renderer2, ViewChild } from '@angular/core';

import { Router } from '@angular/router';
import { HttpClient, HttpClientModule, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatDialog } from '@angular/material/dialog';
import { CnxDialogComponent } from '../cnx-dialog/cnx-dialog.component';

interface User
{
  matricule : string,
  fonction : string,
  pwd : string,
  pwdConf : string
}

@Component({
  standalone : true,
  selector: 'app-pwd',
  imports: [FormsModule,HttpClientModule,CommonModule],
  templateUrl: './pwd.component.html',
  styleUrl: './pwd.component.css'
})

export class PwdComponent implements AfterViewInit
{
  user : User={matricule : "", fonction : "" , pwd : "" , pwdConf : ""};
  inputError!:string;
  @ViewChild("inPwdConf") inPwdConf! : ElementRef;
  @ViewChild("inPwd") inPwd! : ElementRef;
 
  constructor(
    private cd : ChangeDetectorRef,
    private router : Router,
    private renderer : Renderer2,
    private httpClient : HttpClient,
    private dialog : MatDialog
  ){}

  ngAfterViewInit(): void 
  {

  }

  private goBack()
  {
    this.router.navigate(['cnx']);
  }

  changeIconPwd(e : Event) 
  {
    let spanElt=e.target as HTMLSpanElement;
    spanElt.textContent=(spanElt.textContent == "visibility") ? "visibility_off" : "visibility";
    let inType=(spanElt.textContent == "visibility") ? "password" : "text";
    this.renderer.setProperty(this.inPwd.nativeElement,"type",inType);
    this.cd.detectChanges();
  }

  changeIconPwdConf(e : Event) 
  {
    let spanElt=e.target as HTMLSpanElement;
    spanElt.textContent=(spanElt.textContent == "visibility") ? "visibility_off" : "visibility";
    let inType=(spanElt.textContent == "visibility") ? "password" : "text";
    this.renderer.setProperty(this.inPwdConf.nativeElement,"type",inType);
    this.cd.detectChanges();
  }

  definePasword()
  {
    let person="";
    if(this.user.fonction=="medecin"){person="med";}
    else if(this.user.fonction=="secretaire"){person="sec";}
    else if(this.user.fonction=="administrateur"){person="admin";}
    else 
    {
      let errorMsg="Fonction incorrect : Veuillez entrez une fonction valide";
      this.dialog.open(CnxDialogComponent,{data:{message : errorMsg},disableClose : true});
    }

    if(this.inputError==null)
    {
      let url="http://localhost:8080/"+person+"/addPwd"
      const options={
        headers:new HttpHeaders({
          "Content-Type" : "application/json"
        })
      }
      this.httpClient.post<{msg:string}>(url,this.user,options).subscribe({
        next : res => {
          console.log(res);
          this.goBack();
          this.cd.detectChanges();
        },
        error : (err : HttpErrorResponse) => {
          if(err.status==400)
          {
            console.log(err.error)
            console.log(err.error.error);
            let errorMsg=null;
            if(err.error.matricule)
            {
              errorMsg=err.error.matricule;
            }else{
              errorMsg=err.error.error;
            }
            this.dialog.open(CnxDialogComponent,{data:{message : errorMsg},disableClose : true});
          }
          this.cd.detectChanges();
        }
      });
    }

  }


}
