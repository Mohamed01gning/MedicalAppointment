import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AfterViewInit, ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { JwtServiceService } from '../../services/jwt-service.service';
import { UserService } from '../../services/user.service';
import { CommonModule } from '@angular/common';

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
  selector: 'app-med-all-rv',
  imports: [MatTableModule,MatPaginator,CommonModule,MatSortModule],
  templateUrl: './med-all-rv.component.html',
  styleUrl: './med-all-rv.component.css'
})
export class MedAllRvComponent implements OnInit , AfterViewInit
{
  displayedColumns=["idSec","nomPatient","prenomPatient","tel","date","time","done"];
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
    this.getRvByMed();
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

  getRvByMed()
  {
    let token=this.tokenService.getToken();
    let idMed=this.userService.getMatricule();
    let url="http://localhost:8080/rv/getRvByIdMed/"+idMed;
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
