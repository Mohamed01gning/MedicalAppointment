import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA,MatDialogModule, MatDialogRef, MatDialogTitle } from '@angular/material/dialog';

@Component({
  selector: 'app-med-dialog',
  imports: [MatDialogTitle,MatDialogModule],
  templateUrl: './med-dialog.component.html',
  styleUrl: './med-dialog.component.css'
})
export class MedDialogComponent {


  constructor(
    private dialogRef : MatDialogRef<MedDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data:{content:string,title:string}
  ){}

  onClick(e: Event) 
  {
    let button=e.target as HTMLButtonElement;
    if(button.textContent=="OUI")
    {
      this.dialogRef.close(true);
    }
    else
    {
      this.dialogRef.close(false);
    }
  }

  

}
