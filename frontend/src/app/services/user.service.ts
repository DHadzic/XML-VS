import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private readonly path : String = "http://localhost:8888/api/user/";

  constructor(private http: HttpClient) { }

  register(user){
    var headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });
    
    var user_json = JSON.stringify(user);
  
    return this.http.put(this.path + "register", user_json, {headers,responseType: 'text'});  
  }
}
