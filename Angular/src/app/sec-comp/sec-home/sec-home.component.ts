import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { JwtServiceService } from '../../services/jwt-service.service';
import { UserService } from '../../services/user.service';
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
  selector: 'app-sec-home',
  imports: [],
  templateUrl: './sec-home.component.html',
  styleUrl: './sec-home.component.css'
})
export class SecHomeComponent implements OnInit
{
  sec : Secretaire={matricule : "",nom : "",prenom : "",email:"",tel:"",role:"",pwd:""};

  constructor(
    private tokenService : JwtServiceService,
    private userService : UserService,
    private httpClient : HttpClient,
    private cd : ChangeDetectorRef
  ){}

  ngOnInit(): void 
  {
    this.getConnectedSec();
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

}
