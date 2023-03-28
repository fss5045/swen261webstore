import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { Product } from '../product';
import { ProductService } from '../product.service';

import {User} from '../user';
import { LoginService } from '../login.service';
import { UserType } from '../user.type';

import { MessageService } from '../message.service'

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrls: [ './product-detail.component.css' ]
})
export class ProductDetailComponent implements OnInit {
  product: Product | undefined;
  currentUser: User | undefined;
  isAdmin: boolean = false;
  isCustomer: boolean = false;
  currentUserType: UserType = UserType.Guest;

  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    private location: Location,
    private loginService: LoginService,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {
    this.getProduct();
    this.getCurrentUser();
  }

  getProduct(): void {
    const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
    this.productService.getProduct(id)
      .subscribe(products => this.product = products);
  }

  getCurrentUser(): void {
    this.loginService.getCurrentUser()
    .subscribe(current => {this.currentUser = current
    // this.log(`currentDETAIL=${this.currentUser.userType}`)
    // this.loginService.isAdmin(this.currentUser)
    // .subscribe(status => {this.isAdmin = status});
    this.currentUserType = this.currentUser.userType;
    // this.log(`currentType=${this.currentUserType}`);
    this.isAdmin = (this.currentUserType.toString() === UserType[UserType.Admin]);
    this.isCustomer = (this.currentUserType.toString() === UserType[UserType.Customer]);
    // this.log(`isadmin:${this.isAdmin}`)
    // this.log(`iscust:${this.isCustomer}`)
  });
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    if (this.product) {
      this.productService.updateProduct(this.product)
        .subscribe(() => this.goBack());
    }
  }

  cart(): void {

  }

  private log(message: string) {
    this.messageService.add(`LoginService: ${message}`);
  }
}