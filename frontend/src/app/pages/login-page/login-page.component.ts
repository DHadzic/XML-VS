import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss']
})
export class LoginPageComponent implements OnInit {

  public isLoginDisplayed : boolean;

  constructor() { 
    this.isLoginDisplayed = true;
  }

  ngOnInit() {
  }

  changeDisplay(){
    this.isLoginDisplayed = !this.isLoginDisplayed;
  }
}
