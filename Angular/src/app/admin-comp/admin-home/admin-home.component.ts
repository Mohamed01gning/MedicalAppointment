import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { JwtServiceService } from '../../services/jwt-service.service';
import { HttpClient, HttpClientModule, HttpErrorResponse, HttpHeaders } from '@angular/common/http';

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
  selector: 'app-admin-home',
  imports: [HttpClientModule],
  templateUrl: './admin-home.component.html',
  styleUrl: './admin-home.component.css'
})
export class AdminHomeComponent implements OnInit
{
  admin : Admin={matricule : "",nom : "",prenom : "",email:"",tel:"",role:"",pwd:""};
  

  constructor(
    private userService : UserService,
    private tokenService : JwtServiceService,
    private httpClient : HttpClient
  ){}


  ngOnInit(): void 
  {
    this.getConnectedAdmin();

  }

  getConnectedAdmin()
  {
    console.log("token envoyé : "+this.tokenService.getToken());
    console.log("matricule envoyé : "+this.userService.getMatricule());
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



}
