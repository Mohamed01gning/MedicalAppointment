import { Component, OnInit } from '@angular/core';
import { JwtServiceService } from '../services/jwt-service.service';
import { UserService } from '../services/user.service';
import { RouterLink, RouterOutlet } from '@angular/router';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';

interface Admin{
  matricule:string,
  nom:string,
  prenom:string,
  email:string,
  tel:string,
  role:string,
  pwd:string
}

@Component({
  standalone:true,
  selector: 'app-admin-comp',
  imports: [RouterLink,RouterOutlet],
  templateUrl: './admin-comp.component.html',
  styleUrl: './admin-comp.component.css'
})
export class AdminCompComponent implements OnInit
{
  admin : Admin={matricule : "",nom : "",prenom : "",email:"",tel:"",role:"",pwd:""};
  imageUrl="";
  nomComplet:string="";
  matricule! : string;
  token! : string;

  constructor(
    private tokenService : JwtServiceService,
    private userService : UserService,
    private httpClient : HttpClient
  ){}


  ngOnInit(): void 
  {
    this.matricule=this.userService.getMatricule();
    this.token=this.tokenService.getToken();
    this.getConnectedAdmin();
    this.getProfilImage();
  }

  getConnectedAdmin()
  {
    let url="http://localhost:8080/admin/matAdmin/"+this.userService.getMatricule();
    const options={
      headers:new HttpHeaders({
        'Authorization' : 'Bearer '+this.tokenService.getToken()
      })
    };
    this.httpClient.get<Admin>(url,options).subscribe({
      next : data => {
        this.admin=data;
        console.log(this.admin);
      },
      error : (err : HttpErrorResponse) => {
        
      }
    });
  }

  getProfilImage()
  {
    let url="http://localhost:8080/admin/profil/"+this.userService.getMatricule();
    const options={
      headers : new HttpHeaders({
        'Authorization' : 'Bearer '+this.tokenService.getToken()
      }),
      responseType : "blob" as "blob"
    };

    this.httpClient.get(url,options).subscribe({
      next : image =>{
        const url=window.URL.createObjectURL(image);
        this.imageUrl=url;
      }
    });
  }



}
