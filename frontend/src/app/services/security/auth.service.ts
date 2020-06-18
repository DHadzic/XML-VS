import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable,throwError } from 'rxjs';
import { JwtUtilService } from './jwt-util.service';
import {map, catchError} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly path : String = "http://localhost:8888/api/user/";
  constructor(private http: HttpClient, private jwtUtilsService: JwtUtilService) {
  }

  login(username: String, password: String, callback) {
    var headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });
    var credentials = {
      'username' : username,
      'password' : password
    };
    var _this = this;
    this.http.post(this.path + "login", credentials, { headers, responseType : 'text'}).subscribe(
      data =>{
        var value = data as string;
        if (value !== "Invalid login") {
          localStorage.setItem('currentUser', JSON.stringify({ 
            'username': username,
            'roles': _this.jwtUtilsService.getRoles(value), 
            'token': value
          }));
          callback.handleLogin(true);
        }
        else callback.handleLogin(false);
      }
    )
  }

  getToken(): String {
    var currentUser = JSON.parse(localStorage.getItem('currentUser'));
    var token = currentUser && currentUser.token;
    return token ? token : "";
  }

  logout(): void {
    localStorage.removeItem('currentUser');
  }

  isLoggedIn(): boolean {
    if (this.getToken() != '') return true;
    else return false;
  }

  getCurrentUser() {
    if (localStorage.currentUser) {
      return JSON.parse(localStorage.currentUser);
    }
    else {
      return undefined;
    }
  }

  getRoles(){
    var token = this.getToken().toString();
    return this.jwtUtilsService.getRoles(token);
  }
}
