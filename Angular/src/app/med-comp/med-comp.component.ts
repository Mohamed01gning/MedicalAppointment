import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { UserService } from '../services/user.service';
import { JwtServiceService } from '../services/jwt-service.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';

interface Medecin{
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
  selector: 'app-med-comp',
  imports: [RouterLink,RouterOutlet],
  templateUrl: './med-comp.component.html',
  styleUrl: './med-comp.component.css'
})
export class MedCompComponent implements OnInit
{
 
  med : Medecin={matricule : "",nom : "",prenom : "",email:"",tel:"",role:"",pwd:""};
  nomComplet : string=""; 
  matricule : string="";
  imageUrl : string="";

  constructor(
    private userService : UserService,
    private tokenService : JwtServiceService,
    private httpClient : HttpClient,
    private cd : ChangeDetectorRef
  ){}

  ngOnInit(): void 
  {
    this.getProfileImage();
    this.getConnectedMedecin();
  }

  getProfileImage()
  {
    let connectedMat=this.userService.getMatricule();
    let url="http://localhost:8080/med/profil/"+connectedMat;
    let token=this.tokenService.getToken();
    const options={
      headers : new HttpHeaders({
        'Authorization' : 'Bearer '+token
      }),
      responseType : 'blob' as 'blob'
    };
    this.httpClient.get(url,options).subscribe({
      next : (blob) => {
        let url=window.URL.createObjectURL(blob);
        this.imageUrl=url;
      }
    });
  }

  getConnectedMedecin()
  {
    let token=this.tokenService.getToken();
    let connectedMat=this.userService.getMatricule();
    let url="http://localhost:8080/med/medMat/"+connectedMat;
    const options={
      headers : new HttpHeaders({
        'Authorization' : 'Bearer '+token
      })
    };

    this.httpClient.get<Medecin>(url,options).subscribe({
      next : (data) => {
        this.med=data;
        this.nomComplet=this.med.prenom+" "+this.med.nom;
        this.matricule=this.med.matricule;
        this.cd.detectChanges();
      }
    });
  }
}
