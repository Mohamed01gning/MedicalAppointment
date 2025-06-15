import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { JwtServiceService } from '../../services/jwt-service.service';
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
  selector: 'app-med-home',
  imports: [],
  templateUrl: './med-home.component.html',
  styleUrl: './med-home.component.css'
})
export class MedHomeComponent implements OnInit
{
  
  med : Medecin={matricule : "",nom : "",prenom : "",email:"",tel:"",role:"",pwd:""};

  constructor(
    private userService : UserService,
    private tokenService : JwtServiceService,
    private httpClient : HttpClient,
    private cd : ChangeDetectorRef
  ){}

  ngOnInit(): void 
  {
    this.getConnectedMedecin();
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
        console.log(this.med);
        this.cd.detectChanges();
      }
    });
  }
}
