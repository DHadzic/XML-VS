import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private authenticationService: AuthService, private router: Router) { }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {

    if (this.authenticationService.isLoggedIn()) {
      var allowed_roles = next.data["roles"] as Array<string>;
      var my_roles = this.authenticationService.getRoles();
    
      for(var i in my_roles){
        if(allowed_roles.includes(my_roles[i])){
          return true;
        }
      }

      this.router.navigate(['/main']);
      return false;
    }
    else {
      this.router.navigate(['/login']);
      return false;
    }
  }
}
