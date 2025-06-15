import { CommonModule } from '@angular/common';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AfterViewInit, ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { JwtServiceService } from '../../services/jwt-service.service';
import { UserService } from '../../services/user.service';
import { MatSort, MatSortModule } from '@angular/material/sort';

interface Rv{
  num:string,
  idSec:string,
  idMed:string,
  nomPatient:string,
  prenomPatient:string,
  tel:string,
  date:string,
  time:string,
  done:string
}

@Component({
  selector: 'app-admin-rv',
  imports: [MatTableModule,MatPaginator,CommonModule,MatSortModule],
  templateUrl: './admin-rv.component.html',
  styleUrl: './admin-rv.component.css'
})
export class AdminRvComponent implements OnInit , AfterViewInit
{
  displayedColumns=["idSec","idMed","nomPatient","prenomPatient","tel","date","time","done"];
  dataSource=new MatTableDataSource<Rv>([]);
  @ViewChild(MatPaginator) paginator! : MatPaginator;
  @ViewChild(MatSort) sort! : MatSort;

  constructor(
    private httpClient : HttpClient,
    private tokenService : JwtServiceService,
    private cd : ChangeDetectorRef,
  ){}

  ngOnInit(): void 
  {
    this.getAllRv();
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

  getAllRv()
  {
    let token=this.tokenService.getToken();
    let url="http://localhost:8080/rv/getAllRv";
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
