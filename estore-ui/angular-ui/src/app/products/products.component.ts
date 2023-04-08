import { Component, OnInit } from '@angular/core';

import { Product } from '../product';
import { ProductService } from '../product.service';

import {User} from '../user';
import { LoginService } from '../login.service';
import { UserType } from '../user.type';

import { MessageService } from '../message.service'


@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {
    products: Product[] = [];
    hidden: Product[] = [];
    currentUser: User | undefined;
    isAdmin: boolean = false;
    isCustomer: boolean = false;
    currentUserType: UserType = UserType.Guest;

  constructor(
    private productService: ProductService, 
    private loginService: LoginService,
    private messageService: MessageService) { }

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

  // filter, by sport and color

  sortByPriceDesc():void{
    this.products = this.products.sort((p1, p2) => (p1.price > p2.price) ? -1 : 1);
    this.log("sorting by price (expensive to cheapest)")
  }
  sortByPriceAsc():void{
    this.products = this.products.sort((p1,p2) => (p1.price < p2.price) ? -1 :1);
    this.log("sorting by price (cheapest to expensive)");
  }

  sortByQuantityDesc(): void {
    this.products = this.products.sort((q1, q2) => (q1.number  > q2.number) ? -1 : 1 );
    this.log("sorting by remaning stock (highest to lowest)");
  }
  
  sortByQuantityAsc(): void {
    this.products = this.products.sort((q1, q2) => (q1.number  < q2.number) ? -1 : 1 );
    this.log("sorting by remaning stock (lowest to highest)");
  }

  filterByColor(color : String): void {
    this.hidden = this.products.filter(h => h.color !== color);
    for(var i of this.hidden){
      this.log(`${i.name}`)
    }
  }
  filterBySport(sport : String): void {
    this.hidden = this.products.filter(h => h.sport !== sport);
  }

  private log(message: string) {
    this.messageService.add(`ProductsComponent: ${message}`);
  }
}