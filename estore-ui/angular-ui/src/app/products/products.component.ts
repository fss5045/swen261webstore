import { Component, OnInit } from '@angular/core';

import { Product } from '../product';
import { ProductService } from '../product.service';

import {User} from '../user';
import { LoginService } from '../login.service';
import { UserType } from '../user.type';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {
    products: Product[] = [];
    currentUser: User | undefined;
    isAdmin: boolean = false;
    isCustomer: boolean = false;
    currentUserType: UserType = UserType.Guest;

  constructor(private productService: ProductService, private loginService: LoginService) { }

  ngOnInit(): void {
    this.getProducts();
    this.getCurrentUser();
    }

  getProducts(): void {
    this.productService.getProducts()
    .subscribe(products => this.products = products);
  }

  getCurrentUser(): void{
    this.loginService.getCurrentUser()
    .subscribe(current => {this.currentUser = current
    this.currentUserType = this.currentUser.userType;
    this.isAdmin = (this.currentUserType.toString() === UserType[UserType.Admin]);
    this.isCustomer = (this.currentUserType.toString() === UserType[UserType.Customer]);
  });
  }

  add(name: string): void {
    name = name.trim();
    if (!name) { return; }
    this.productService.addProduct({ name } as Product)
      .subscribe(product => {
        this.products.push(product);
      });
  }

  delete(product: Product): void {
    this.products = this.products.filter(h => h !== product);
    this.productService.deleteProduct(product.id).subscribe();
  }

}