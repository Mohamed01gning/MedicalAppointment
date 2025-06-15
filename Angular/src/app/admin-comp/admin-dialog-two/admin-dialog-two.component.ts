import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogActions, MatDialogContent, MatDialogModule, MatDialogRef } from '@angular/material/dialog';

@Component({
  standalone:true,
  selector: 'app-admin-dialog-two',
  imports: [MatDialogActions,MatDialogModule,MatDialogContent],
  templateUrl: './admin-dialog-two.component.html',
  styleUrl: './admin-dialog-two.component.css'
})
export class AdminDialogTwoComponent 
{

  constructor(
    private dialogRef : MatDialogRef<AdminDialogTwoComponent>,
    @Inject(MAT_DIALOG_DATA) public data : {content : string}
  ){}

  close(event : Event) 
  {
    
    const buttonElt=event.target as HTMLButtonElement;
    if(buttonElt.textContent=="OUI")
    {
      this.dialogRef.close(true);
    }
    else{
      this.dialogRef.close(false);
    }
    
  }

}
