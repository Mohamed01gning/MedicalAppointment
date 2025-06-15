import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AfterViewInit, ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTable, MatTableDataSource, MatTableModule } from '@angular/material/table';
import { JwtServiceService } from '../../services/jwt-service.service';
import { UserService } from '../../services/user.service';
import { MatSort } from '@angular/material/sort';
import { CommonModule } from '@angular/common';

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
  selector: 'app-added-rv',
  imports: [MatTableModule,MatPaginator,CommonModule],
  templateUrl: './added-rv.component.html',
  styleUrl: './added-rv.component.css'
})
export class AddedRvComponent implements OnInit , AfterViewInit
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
    this.getRvBySecretaire();
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

  getRvBySecretaire()
  {
    let token=this.tokenService.getToken();
    let idSec=this.userService.getMatricule();
    let url="http://localhost:8080/rv/getRvByIdSec/"+idSec;
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
