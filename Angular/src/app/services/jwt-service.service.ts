import { Inject, Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class JwtServiceService 
{
  private token! : string;

  constructor() {
  }

  getToken() : string
  {
    return this.token;
  }

  setToken(t:string) 
  {
    this.token=t;
  }
}
