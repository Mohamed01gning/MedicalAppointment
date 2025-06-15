import { AfterViewInit, ChangeDetectorRef, Component, OnInit, ViewChild, viewChild } from '@angular/core';
import { RouterLink } from '@angular/router';
import { MatTableDataSource, MatTableModule} from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatDialog } from '@angular/material/dialog';
import { AdminDialogComponent } from '../admin-dialog/admin-dialog.component';
import { HttpClient, HttpClientModule, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { JwtServiceService } from '../../services/jwt-service.service';
import { AdminDialogTwoComponent } from '../admin-dialog-two/admin-dialog-two.component';

interface Admin{
  matricule:string,
  nom:string,
  prenom:string,
  email:string,
  tel:string
}

const data: Admin[]=[
  {matricule : "ADMIN-4587-500",nom:"Gning",prenom:"Mohamed",email:"gningahmed01@gmail.com",tel:"774586532"},
  {matricule : "ADMIN-4587-501",nom:"Gning",prenom:"Mohamed",email:"gningahmed01@gmail.com",tel:"774586532"},
  {matricule : "ADMIN-4587-502",nom:"Gning",prenom:"Mohamed",email:"gningahmed01@gmail.com",tel:"774586532"},
  {matricule : "ADMIN-4587-503",nom:"Gning",prenom:"Mohamed",email:"gningahmed01@gmail.com",tel:"774586532"},
  {matricule : "ADMIN-4587-504",nom:"Gning",prenom:"Mohamed",email:"gningahmed01@gmail.com",tel:"774586532"},
  {matricule : "ADMIN-4587-512",nom:"Gning",prenom:"Mohamed",email:"gningahmed01@gmail.com",tel:"774586532"},
  {matricule : "ADMIN-4587-513",nom:"Gning",prenom:"Mohamed",email:"gningahmed01@gmail.com",tel:"774586532"},
  {matricule : "ADMIN-4587-514",nom:"Gning",prenom:"Mohamed",email:"gningahmed01@gmail.com",tel:"774586532"},
  {matricule : "ADMIN-4587-515",nom:"Gning",prenom:"Mohamed",email:"gningahmed01@gmail.com",tel:"774586532"},
  {matricule : "ADMIN-4587-516",nom:"Gning",prenom:"Mohamed",email:"gningahmed01@gmail.com",tel:"774586532"},
  {matricule : "ADMIN-4587-517",nom:"Gning",prenom:"Mohamed",email:"gningahmed01@gmail.com",tel:"774586532"},
  {matricule : "ADMIN-4587-518",nom:"Gning",prenom:"Mohamed",email:"gningahmed01@gmail.com",tel:"774586532"},
  {matricule : "ADMIN-4587-513",nom:"Gning",prenom:"Mohamed",email:"gningahmed01@gmail.com",tel:"774586532"},
  {matricule : "ADMIN-4587-505",nom:"Gning",prenom:"Mohamed",email:"gningahmed01@gmail.com",tel:"774586532"},
  {matricule : "ADMIN-4587-506",nom:"Gning",prenom:"Mohamed",email:"gningahmed01@gmail.com",tel:"774586532"},
  {matricule : "ADMIN-4587-507",nom:"Gning",prenom:"Mohamed",email:"gningahmed01@gmail.com",tel:"774586532"},
  {matricule : "ADMIN-4587-508",nom:"Gning",prenom:"Mohamed",email:"gningahmed01@gmail.com",tel:"774586532"},
  {matricule : "ADMIN-4587-509",nom:"Gning",prenom:"Mohamed",email:"gningahmed01@gmail.com",tel:"774586532"},
  {matricule : "ADMIN-4587-510",nom:"Gning",prenom:"Mohamed",email:"gningahmed01@gmail.com",tel:"774586532"},
  {matricule : "ADMIN-4587-511",nom:"Gning",prenom:"Mohamed",email:"gningahmed01@gmail.com",tel:"774586532"},
  {matricule : "ADMIN-4587-497",nom:"Gning",prenom:"Mohamed",email:"gningahmed01@gmail.com",tel:"774586532"},
  {matricule : "ADMIN-4510-498",nom:"Gning",prenom:"Mohamed",email:"gningahmed01@gmail.com",tel:"774586532"},
  {matricule : "ADMIN-4578-499",nom:"Gning",prenom:"Mohamed",email:"gningahmed01@gmail.com",tel:"774586532"},
 
]

@Component({
  selector: 'app-admin-menu',
  imports: [MatTableModule,MatPaginator,MatSortModule,HttpClientModule],
  templateUrl: './admin-menu.component.html',
  styleUrl: './admin-menu.component.css'
})
export class AdminMenuComponent implements OnInit,AfterViewInit
{
 
  displayColumns:string[]=["matricule","nom","prenom","email","tel","action"];
  admins : Admin[]=[];
  dataSource =new MatTableDataSource<Admin>([]);
  @ViewChild(MatPaginator) paginator! : MatPaginator;
  @ViewChild(MatSort) sort! : MatSort;

  constructor(
    private dialog : MatDialog,
    private cd : ChangeDetectorRef,
    private httpClient : HttpClient,
    private tokenService : JwtServiceService
  ){}

  ngAfterViewInit(): void 
  {
    this.paginateAndSortTable();
  }

  ngOnInit(): void
  {
     this.getAllAdmin();
     
  }  

  getAllAdmin()
  {
    let token=this.tokenService.getToken();
    let url="http://localhost:8080/admin/getAdmins";
    const options={
      headers : new HttpHeaders({
        'Authorization' : 'Bearer '+token
      })
    };
    this.httpClient.get<Admin[]>(url,options).subscribe({
      next : (data) =>
      {
        this.admins=data;
        console.log(this.admins);
        this.dataSource.data=data;
        console.log("DATASOURCE : "+this.dataSource.data);
        this.cd.detectChanges();
      }
    });
  }

  paginateAndSortTable()
  {
    this.dataSource.paginator=this.paginator;
    this.dataSource.sort=this.sort;
    this.sort.sortChange.emit({
      active : "matricule",
      direction : "asc"
    })
  }

  deleteAdmin(admin : any) 
  {
    let msg ="Etes Vous Sur de Vouloir Supprimer l'utilisateur \""+admin.matricule+"\"";
    let deleteAllowed : boolean=false;
    const dialogRef=this.dialog.open(AdminDialogTwoComponent,{
      data : {content : msg},
      disableClose:true
    });
    dialogRef.afterClosed().subscribe(value =>{
      console.log(value);
      deleteAllowed=value;
      console.log(deleteAllowed);
      if(deleteAllowed)
      {
        let url="http://localhost:8080/admin/delete/"+admin.matricule;
        let token=this.tokenService.getToken();
        const options={
          headers : new HttpHeaders({
            'Authorization' : 'Bearer '+token
          })
        };
        this.httpClient.delete<{msg:string}>(url,options).subscribe({
          next : (response) => {
           
            this.admins=this.admins.filter(user => user.matricule!=admin.matricule);
            this.dataSource.data=this.admins;
            this.openAdminDialog(response.msg);
            this.cd.detectChanges();
          },
          error : (err : HttpErrorResponse) => {
            if(err.status == 403)
            {
              this.openAdminDialog(err.error.error);
            }
          }
        });
      }
    });
  }

  seletRow(row: any) 
  {
    this.openAdminDialog(row.matricule);
    this.cd.detectChanges();
  }

  openAdminDialog(msg : string)
  {
    this.dialog.open(AdminDialogComponent,{
      data : {content : msg},
      disableClose:true
    });
  }


}
