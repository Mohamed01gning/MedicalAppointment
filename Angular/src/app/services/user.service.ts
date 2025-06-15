import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private matricule! : string;
  
  constructor() {}

  getMatricule() : string
  {
    return this.matricule;
  }

  setMatricule(matricule : string)
  {
    this.matricule=matricule;
  }
  
  
}
