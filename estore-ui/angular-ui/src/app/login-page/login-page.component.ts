import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { Router } from '@angular/router';

import { User } from '../user';
import { UserType } from '../user.type';
import { LoginService } from '../login.service';

import { MessageService } from '../message.service'

@Component({
  selector: 'app-dashboard',
  templateUrl: './login-page.component.html',
  styleUrls: [ './login-page.component.css' ]
})
export class LoginPageComponent implements OnInit{
  currentUser: User | undefined;
  isAdmin: boolean = false;
  isCustomer: boolean = false;
  name: string = "";

  constructor(
    private route: ActivatedRoute,
    private loginService: LoginService,
    private messageService: MessageService,
    private location: Location,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.getCurrentUser();
  }

  getCurrentUser(): void {
    this.loginService.getCurrentUser()
    .subscribe(current => {this.currentUser = current;
    this.isAdmin = (this.currentUser.userType.toString() === UserType[UserType.Admin]);
    this.isCustomer = (this.currentUser.userType.toString() === UserType[UserType.Customer]);
  });
  }

  login(username: string): void {
    // this.log(`username: ${username}`);
    this.loginService.login(username)
    .subscribe(user => this.log(`${user.username} logged in`));
    this.router.navigate(["/dashboard"])
  }

  logout(): void {
    // this.log(`logging out`);
    this.loginService.logout()
    .subscribe(status => this.log(`logged out: ${status}`));
    window.location.reload()
  }

  goBack(): void {
    this.location.back();
  }

  private log(message: string) {
    this.messageService.add(`loginPage: ${message}`);
  }
}