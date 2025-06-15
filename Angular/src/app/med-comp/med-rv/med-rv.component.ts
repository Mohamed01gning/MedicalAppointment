import { CommonModule } from '@angular/common';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AfterViewInit, ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { JwtServiceService } from '../../services/jwt-service.service';
import { UserService } from '../../services/user.service';
import { MatDialog } from '@angular/material/dialog';
import { MedDialogComponent } from '../med-dialog/med-dialog.component';

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
  selector: 'app-med-rv',
  imports: [MatTableModule,MatPaginator,CommonModule,MatSortModule],
  templateUrl: './med-rv.component.html',
  styleUrl: './med-rv.component.css'
})
export class MedRvComponent implements OnInit , AfterViewInit
{
  displayedColumns=["idSec","nomPatient","prenomPatient","tel","date","time","done"];
  dataSource=new MatTableDataSource<Rv>([]);
  @ViewChild(MatPaginator) paginator! : MatPaginator;
  @ViewChild(MatSort) sort! : MatSort;

  constructor(
    private httpClient : HttpClient,
    private tokenService : JwtServiceService,
    private cd : ChangeDetectorRef,
    private userService : UserService,
    private dialog : MatDialog
  ){}

  ngOnInit(): void 
  {
    this.getTodayRvByMed();
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

  getTodayRvByMed()
  {
    let token=this.tokenService.getToken();
    let idMed=this.userService.getMatricule();
    let url="http://localhost:8080/rv/getTodayRv/"+idMed;
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

  doneRv(rv : any) 
  {
    console.log("RV : "+rv.num+" "+rv.idSec);
    const conf={
      data:{
        content : "Voulez Vous Terminer Cette Rendez-Vous Avec "+rv.prenomPatient+" "+rv.nomPatient+"?",
        title:"Rendez-Vous" 
      }
    };
    const dialogRef=this.dialog.open(MedDialogComponent,conf);
    dialogRef.afterClosed().subscribe(result => {
      if(result)
      {
        let rendezVous:Rv=rv;
        rendezVous.done="check_box";
        this.updateRv(rendezVous);
      }
    });
  }

  updateRv(rv:Rv)
  {
    let token=this.tokenService.getToken();
    let url="http://localhost:8080/rv/update";
    const options={
      headers : new HttpHeaders({
      'Authorization' : 'Bearer '+token
      })
    };

    this.httpClient.post<{msg:string}>(url,rv,options).subscribe({
      next : (res) => {
        console.log(res);
        this.cd.detectChanges();
      }
    });
  }

}
