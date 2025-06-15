import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-admin-dialog',
  imports: [MatDialogModule],
  templateUrl: './admin-dialog.component.html',
  styleUrl: './admin-dialog.component.css'
})
export class AdminDialogComponent {


  constructor(
    private dialogRef : MatDialogRef<AdminDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data : {content : string}
  ){}


  close() 
  {
    this.dialogRef.close();
  }


}
