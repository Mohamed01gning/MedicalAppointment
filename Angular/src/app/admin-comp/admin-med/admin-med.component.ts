import { HttpClient, HttpClientModule, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { AfterViewInit, ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { JwtServiceService } from '../../services/jwt-service.service';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatDialog } from '@angular/material/dialog';
import { AdminDialogComponent } from '../admin-dialog/admin-dialog.component';
import { AdminDialogTwoComponent } from '../admin-dialog-two/admin-dialog-two.component';

interface Medecin{
  matricule:string,
  nom:string,
  prenom:string,
  email:string,
  tel:string
}

@Component({
  selector: 'app-admin-med',
  imports: [MatTableModule,MatPaginator,MatSortModule,HttpClientModule],
  templateUrl: './admin-med.component.html',
  styleUrl: './admin-med.component.css'
})
export class AdminMedComponent implements OnInit ,AfterViewInit {

  medecins : Medecin[]=[];
  dataSource=new MatTableDataSource<Medecin>([]);
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

  deleteMedecin(med: any) 
  {
    let msg="Voullez vous vraiment supprimer l'utilisateur \""+med.matricule+"\"";
    const dialogRef=this.dialog.open(AdminDialogTwoComponent,{
      data : {content : msg},
      disableClose:true
    });
    this.cd.detectChanges();

    dialogRef.afterClosed().subscribe(value => {
      if(value == true)
      {
        let url="http://localhost:8080/med/remove/"+med.matricule;
        let token=this.tokenService.getToken();
        const options={
          headers : new HttpHeaders({
          'Authorization' : 'Bearer '+token
          })
        };
        this.httpClient.delete<{msg : string}>(url,options).subscribe({
          next : (res) => {
            this.dataSource.data=this.medecins.filter(user => user.matricule!=med.matricule);
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
