import { Component } from '@angular/core';
import { AuthService } from './services/security/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'Paper publishing';

  constructor(private authService: AuthService,
    public router: Router){}

    ngOnInit() {
    }
    
    loggedIn():boolean{
      return this.authService.isLoggedIn();
    }

    login():void{
      this.router.navigate(['/login']);
    }

    logout(){
      this.authService.logout();
      this.router.navigate(['/login']);
    }

    navigateTo(url){
      this.router.navigate['/' + url];
    }
}
