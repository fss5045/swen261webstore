import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';


@Component({
  selector: 'app-dashboard',
  templateUrl: './login-page.component.html',
  styleUrls: [ './login-page.component.css' ]
})
export class LoginPageComponent implements OnInit{

    constructor(
        private route: ActivatedRoute,
        private location: Location
      ) {}
    
    ngOnInit(): void {
    }

    login(): void {
        this.goBack
    }

    goBack(): void {
       this.location.back();
    }
}