import { HttpClient, HttpClientModule, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { AfterViewInit, ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { JwtServiceService } from '../../services/jwt-service.service';
import { AdminDialogTwoComponent } from '../admin-dialog-two/admin-dialog-two.component';
import { AdminDialogComponent } from '../admin-dialog/admin-dialog.component';

interface Secretaire{
  matricule:string,
  nom:string,
  prenom:string,
  email:string,
  tel:string
}

@Component({
  selector: 'app-admin-sec',
  imports: [MatTableModule,MatPaginator,MatSortModule,HttpClientModule],
  templateUrl: './admin-sec.component.html',
  styleUrl: './admin-sec.component.css'
})
export class AdminSecComponent implements OnInit , AfterViewInit
{
  secretaires : Secretaire[]=[];
  dataSource=new MatTableDataSource<Secretaire>([]);
  displayColumns=["matricule","nom","prenom","email","tel","action"];
  @ViewChild(MatPaginator) paginator! : MatPaginator;
  @ViewChild(MatSort) sort! : MatSort;

  constructor(
    private httpClient : HttpClient,
    private dialog : MatDialog,
    private tokenService : JwtServiceService,
    private cd : ChangeDetectorRef
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
    this.httpClient.get<Secretaire[]>(url,options).subscribe({
      next : (data) => {
        this.secretaires=data;
        this.dataSource.data=data;
        this.cd.detectChanges();
      }
    })
  }

  deleteMedecin(sec: any) 
  {
    let msg="Voullez vous vraiment supprimer l'utilisateur \""+sec.matricule+"\"";
    const dialogRef=this.dialog.open(AdminDialogTwoComponent,{
      data : {content : msg},
      disableClose:true
    });
    this.cd.detectChanges();

    dialogRef.afterClosed().subscribe(value => {
      if(value == true)
      {
        let url="http://localhost:8080/med/remove/"+sec.matricule;
        let token=this.tokenService.getToken();
        const options={
          headers : new HttpHeaders({
          'Authorization' : 'Bearer '+token
          })
        };
        this.httpClient.delete<{msg : string}>(url,options).subscribe({
          next : (res) => {
            this.dataSource.data=this.secretaires.filter(user => user.matricule!=sec.matricule);
            this.openDialog(res.msg);
            this.cd.detectChanges();
          },
          error : (err : HttpErrorResponse) => {
            if(err.status == 403)
            {
              this.openDialog(err.error.error);
            }
            this.cd.detectChanges();
          }
        });
      }
    });
  }

  openDialog(msg : string)
  {
    this.dialog.open(AdminDialogComponent,{
      data : {content : msg},
      disableClose:true
    });
  }

}
