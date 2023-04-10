import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { CartService } from '../cart.service';

import { Product } from '../product';
import { ProductService } from '../product.service'

import { User } from '../user';
import { UserType } from '../user.type';
import { LoginService } from '../login.service';

import { MessageService } from '../message.service'

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent implements OnInit{
  products: Product[] = [];
  cart: Product[] = [];
  currentUser: User | undefined;
  isAdmin: boolean = false;
  isCustomer: boolean = false;

  constructor(
    private location: Location,
    private productService: ProductService,
    private cartService: CartService,
    private loginService: LoginService,
    private messageService: MessageService
    ) {}

  ngOnInit() {
    this.getProducts();
    this.getCurrentUser();
  }

  getProducts(): void {
    this.productService.getProducts().subscribe((products) => {
      this.products = products;
    });
  }

  getCurrentUser(): void {
    this.loginService.getCurrentUser()
    .subscribe(current => {this.currentUser = current
    this.isAdmin = (this.currentUser.userType.toString() === UserType[UserType.Admin]);
    this.isCustomer = (this.currentUser.userType.toString() === UserType[UserType.Customer]);
    this.cart = this.currentUser.cart;
    for (var item of this.cart){
      this.log(item.name);
    }
  });
  }

  removeFromCart(item: Product) {
    this.cartService.remove(item.id)
    .subscribe(() => window.location.reload());
  }

  private log(message: string) {
    this.messageService.add(`CartComponent: ${message}`);
  }
}
