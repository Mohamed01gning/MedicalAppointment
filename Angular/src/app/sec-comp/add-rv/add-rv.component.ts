import { HttpClient,HttpHeaders } from '@angular/common/http';
import { AfterViewInit, ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog} from '@angular/material/dialog';
import { JwtServiceService } from '../../services/jwt-service.service';
import { MatTable, MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { AddRvDialogComponent } from '../add-rv-dialog/add-rv-dialog.component';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../services/user.service';
import { SecDialogComponent } from '../sec-dialog/sec-dialog.component';

interface Medecin{
  matricule:string,
  nom:string,
  prenom:string,
  email:string,
  tel:string
}

interface User{
  idSec : string,
  idMed : string,
  nomPatient:string,
  prenomPatient:string,
  tel:string,
  date:string | null,
  time:string
}

@Component({
  selector: 'app-add-rv',
  imports: [MatTable,MatTableModule,MatPaginator,CommonModule,FormsModule],
  providers : [DatePipe],
  templateUrl: './add-rv.component.html',
  styleUrl: './add-rv.component.css'
})

export class AddRvComponent implements OnInit ,AfterViewInit 
{

  user: User={idSec:"",idMed:"",nomPatient :"",prenomPatient :"",tel:"",date:"",time:""};
  medecins : Medecin[]=[];
  dataSource=new MatTableDataSource<Medecin>([]);
  displayColumns=["matricule","nom","prenom","email","tel"];
  @ViewChild(MatPaginator) paginator! : MatPaginator;
  @ViewChild(MatSort) sort! : MatSort;

  constructor(
    private httpClient : HttpClient,
    private dialog : MatDialog,
    private tokenService : JwtServiceService,
    private cd : ChangeDetectorRef,
    private dateFormatter : DatePipe,
    private userService : UserService
  ){}

  ngAfterViewInit(): void 
  {
    this.paginateAndSortTable();
  }

  ngOnInit(): void 
  {
    this.getAllMedecin();
  }

  paginateAndSortTable()
  {
    this.dataSource.paginator=this.paginator;
    this.dataSource.sort=this.sort;
    this.dataSource.sort.sortChange.emit({
      active : 'matricule',
      direction : 'asc'
    });
  }

  getAllMedecin()
  {
    let url="http://localhost:8080/med/getMedecins";
    let token=this.tokenService.getToken();
    const options={
      headers : new HttpHeaders({
      'Authorization' : 'Bearer '+token
      })
    };
    this.httpClient.get<Medecin[]>(url,options).subscribe({
      next : (data) => {
        this.medecins=data;
        this.dataSource.data=data;
        console.log("this.medecins = "+this.medecins);
        console.log("this.dataSource = "+this.dataSource.data);
        this.cd.detectChanges();
      }
    })
  }

  fixeRv(row : any)
  {
    const conf={
      width : "5500px",
      height : "590px",
      disableClose : true,
      data:{
        idSec:this.userService.getMatricule(),
        idMed:row.matricule
      }
    };

    const dialogRef=this.dialog.open(AddRvDialogComponent,conf);
    dialogRef.afterClosed().subscribe(res => {
      if(res!=null)
      {
        const config={
          data:{
            content:res.msg
          }
        };
        this.dialog.open(SecDialogComponent,config);
      }
    });
    this.cd.detectChanges();
  }

  parseDate(isoDate :string) : string | null
  {
    return this.dateFormatter.transform(isoDate,"dd-MM-yyyy");
  }

}
