import { Component, OnInit } from '@angular/core';

import { User } from './user';
import { UserType } from './user.type';
import { LoginService } from './login.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'Ball Store';
  currentUser: User | undefined;
  currentUserType: UserType = UserType.Guest;
  isAdmin: boolean = false;
  isCustomer: boolean = false;
  ahhh: string = "";

  constructor(
    private loginService: LoginService
  ){}

  ngOnInit(): void {
    this.getCurrentUser();
  }

  getCurrentUser(): void {
    this.loginService.getCurrentUser()
    .subscribe(current => {this.currentUser = current;
    this.currentUserType = this.currentUser.userType;
    this.isAdmin = (this.currentUserType.toString() === UserType[UserType.Admin]);
    this.isCustomer = (this.currentUserType.toString() === UserType[UserType.Customer]);
    });
  }
} 