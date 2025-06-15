import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { JwtServiceService } from '../services/jwt-service.service';
import { UserService } from '../services/user.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';

interface Secretaire{
  matricule:string,
  nom:string,
  prenom:string,
  email:string,
  tel:string,
  role:string,
  pwd:string
}

@Component({
  selector: 'app-sec-comp',
  imports: [RouterLink,RouterOutlet],
  templateUrl: './sec-comp.component.html',
  styleUrl: './sec-comp.component.css'
})
export class SecCompComponent implements OnInit
{
  

  sec : Secretaire={matricule : "",nom : "",prenom : "",email:"",tel:"",role:"",pwd:""};
  imageUrl : string="";

  constructor(
    private tokenService : JwtServiceService,
    private userService : UserService,
    private httpClient : HttpClient,
    private cd : ChangeDetectorRef
  ){}

  ngOnInit(): void 
  {
    this.getConnectedSec();
    this.getSecretaireImage();
  }


  getConnectedSec()
  {
    let mat=this.userService.getMatricule();
    let url="http://localhost:8080/sec/matSec/"+mat;
    let token=this.tokenService.getToken();
    const options={
      headers : new HttpHeaders({
        'Authorization' : 'Bearer '+token
      })
    };

    this.httpClient.get<Secretaire>(url,options).subscribe({
      next : (secretaire) => {
        this.sec=secretaire;
        this.cd.detectChanges();
      }
    });
  }

  getSecretaireImage()
  {
    let mat=this.userService.getMatricule();
    let url="http://localhost:8080/sec/profil/"+mat;
    let token=this.tokenService.getToken();
    const options={
      headers : new HttpHeaders({
        'Authorization' : 'Bearer '+token
      }),
      responseType : 'blob' as 'blob'
    };

    this.httpClient.get(url,options).subscribe({
      next : (image) => {
        this.imageUrl=window.URL.createObjectURL(image);
        this.cd.detectChanges();
      }
    });
  }


  

}
