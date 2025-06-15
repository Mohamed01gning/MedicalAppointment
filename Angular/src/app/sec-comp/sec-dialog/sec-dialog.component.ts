import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogActions, MatDialogContent, MatDialogModule, MatDialogRef, MatDialogTitle } from '@angular/material/dialog';

@Component({
  selector: 'app-sec-dialog',
  imports: [MatDialogModule,MatDialogActions,MatDialogContent],
  templateUrl: './sec-dialog.component.html',
  styleUrl: './sec-dialog.component.css'
})
export class SecDialogComponent {
  

  constructor(
    private dialogRef : MatDialogRef<SecDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data : {content:string},
  ){}

  onClick() 
  {
    this.dialogRef.close();
  }

}
