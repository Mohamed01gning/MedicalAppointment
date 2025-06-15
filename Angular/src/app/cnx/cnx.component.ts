import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { AfterViewInit, ChangeDetectorRef, Component, ElementRef, Renderer2, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { JwtServiceService } from '../services/jwt-service.service';
import { UserService } from '../services/user.service';

interface User{
  matricule : string,
  pwd : string
}


@Component({
  standalone : true,
  selector: 'app-cnx',
  imports: [RouterLink,FormsModule,HttpClientModule,CommonModule],
  templateUrl: './cnx.component.html',
  styleUrl: './cnx.component.css'
})

export class CnxComponent implements AfterViewInit{

  cnxErr : boolean=false;
  serverRes! : { token : string};
  errorRes! : string;
  resp! : {msg : string};
  user : User={matricule : "",pwd : ""};
  @ViewChild("inPwd") inPwd! : ElementRef;
 
  constructor(
    private renderer : Renderer2,
    private cd : ChangeDetectorRef,
    private httpClient : HttpClient,
    private jwtService : JwtServiceService,
    private userService : UserService,
    private router : Router
  ){}
  
  ngAfterViewInit(): void 
  {

  }

  onSubmit() 
  {
    let url="http://localhost:8080/cnx";
    const options={
      Headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
      withCredentials:true
    };
    this.httpClient.post<{token : string}>(url,this.user,options).subscribe({
      next : res =>{
        this.serverRes=res;
        console.log(this.serverRes);
        this.jwtService.setToken(res.token);
        this.userService.setMatricule(this.user.matricule);
        this.sendRedirect(this.user.matricule);
        this.cd.detectChanges();
      },
      error : (err : HttpErrorResponse) =>{
        if(err.status==400)
        {
          this.errorRes=err.error.error;
          console.log(this.errorRes);
          setTimeout(()=> this.errorRes="",3000);
          this.cd.detectChanges();
        }
      }
    })
  }

  changeIconPwd(e : Event) 
  {
    let spanElt=e.target as HTMLSpanElement;
    spanElt.textContent=(spanElt.textContent == "visibility") ? "visibility_off" : "visibility";
    let inType=(spanElt.textContent == "visibility") ? "password" : "text";
    this.renderer.setProperty(this.inPwd.nativeElement,"type",inType);
    this.cd.detectChanges();
  }

  private sendRedirect(matricule : string)
  {
    let pageToRedirect="";
    if(matricule.startsWith("MED")){pageToRedirect="med"}
    else if(matricule.startsWith("ADMIN")) {pageToRedirect="admin"}
    else {pageToRedirect="sec"}

    this.router.navigate([pageToRedirect]);
  }



}
