import { Component, OnInit } from '@angular/core';

import { Product } from '../product';
import { ProductService } from '../product.service';

import {User} from '../user';
import { LoginService } from '../login.service';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {
    products: Product[] = [];
    currentUser: User | undefined;
    isAdmin: boolean = false;

  constructor(private productService: ProductService, private loginService: LoginService) { }

  ngOnInit(): void {
    this.getProducts();
    // this.loginService.getCurrentUser().subscribe(user => this.currentUser = user);
    // this.loginService.isAdmin().subscribe(isAdmin => this.isAdmin = isAdmin);
    }

  getProducts(): void {
    this.productService.getProducts()
    .subscribe(products => this.products = products);
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