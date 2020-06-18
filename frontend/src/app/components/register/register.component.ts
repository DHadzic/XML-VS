import { Component, OnInit, EventEmitter, Output } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  public myBool : boolean;
  public user;
  public acc_types;
  public error_messages;

  @Output()
  changeDisplay:EventEmitter<any> = new EventEmitter();

  constructor(private userService:UserService,private router: Router) { 
    this.user = {};
    this.acc_types = ["ROLE_AUTHOR","ROLE_REVIEWER","ROLE_EDITOR"];
    this.user.role = this.acc_types[0];
    this.myBool = true;
    this.error_messages = {};
    this.error_messages.username_taken = false;
    this.error_messages.name = false;
  }

  
  ngOnInit() {
  }

  openRegistration(){
    this.changeDisplay.emit();
  }

  register(){

    console.log(this.user);
    var _this = this;

    this.userService.register(this.user).subscribe(
      data=>{
        alert(data);
      }
    )
  }

  public setLogin(){
    this.changeDisplay.emit();
  }

}
