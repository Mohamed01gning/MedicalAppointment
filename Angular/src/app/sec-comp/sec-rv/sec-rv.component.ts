import { CommonModule } from '@angular/common';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AfterViewInit, ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { JwtServiceService } from '../../services/jwt-service.service';
import { UserService } from '../../services/user.service';
import { MatSort } from '@angular/material/sort';

interface Rv{
  idMed:string,
  nomPatient:string,
  prenomPatient:string,
  tel:string,
  date:string,
  time:string,
  done:string
}

@Component({
  selector: 'app-sec-rv',
  imports: [MatTableModule,MatPaginator,CommonModule],
  templateUrl: './sec-rv.component.html',
  styleUrl: './sec-rv.component.css'
})
export class SecRvComponent implements OnInit,AfterViewInit
{

  displayedColumns=["idMed","nomPatient","prenomPatient","tel","date","time","done"];
  dataSource=new MatTableDataSource<Rv>([]);
  @ViewChild(MatPaginator) paginator! : MatPaginator;
  @ViewChild(MatSort) sort! : MatSort;

  constructor(
    private httpClient : HttpClient,
    private tokenService : JwtServiceService,
    private cd : ChangeDetectorRef,
    private userService : UserService
  ){}

  ngOnInit(): void 
  {
    this.getTodayRvBySecretaire();
  }

  ngAfterViewInit(): void 
  {
    this.paginateAndSortTable();
  }

  paginateAndSortTable()
  {
    this.dataSource.paginator=this.paginator;
    this.dataSource.sort=this.sort;
    this.dataSource.sort.sortChange.emit({
      active : 'date',
      direction : 'asc'
    });
  }

  getTodayRvBySecretaire()
  {
    let token=this.tokenService.getToken();
    let idSec=this.userService.getMatricule();
    let url="http://localhost:8080/rv/getTodayRvByIdSec/"+idSec;
    const options={
      headers : new HttpHeaders({
      'Authorization' : 'Bearer '+token
      })
    };

    this.httpClient.get<Rv[]>(url,options).subscribe({
      next : (rendezVous) => {
        this.dataSource.data=rendezVous;
        this.cd.detectChanges();
      }
    });



  }
}