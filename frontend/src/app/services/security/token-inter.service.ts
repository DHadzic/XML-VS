import { Injectable, Injector } from '@angular/core';
import { AuthService } from './auth.service';
import { HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TokenInterService {
  constructor(private inj: Injector) { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let authenticationService:AuthService = this.inj.get(AuthService); 
    request = request.clone({
      setHeaders: {
        'Authentication-Token': `${authenticationService.getToken()}`
      }
    });

    return next.handle(request);
  }
}
