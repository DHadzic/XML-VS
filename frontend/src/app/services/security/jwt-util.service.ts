import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class JwtUtilService {

  constructor() { }

  getRoles(token: string) {
    if(token == ""){
      return [];
    }
    let jwtData = token.split('.')[1];
    let decodedJwtJsonData = window.atob(jwtData);
    let decodedJwtData = JSON.parse(decodedJwtJsonData);
    return decodedJwtData.roles.split(',');
  }
}
