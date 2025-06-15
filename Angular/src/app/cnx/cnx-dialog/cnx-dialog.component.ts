import { CommonModule } from '@angular/common';
import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-cnx-dialog',
  imports: [MatDialogModule,CommonModule],
  templateUrl: './cnx-dialog.component.html',
  styleUrl: './cnx-dialog.component.css'
})
export class CnxDialogComponent 
{

  constructor(
    private dialogRef : MatDialogRef<CnxDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data : {message:string}
  ){}

  close()
  {
    this.dialogRef.close();
  }

}
