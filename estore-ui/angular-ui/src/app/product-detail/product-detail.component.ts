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
    // this.loginService.getCurrentUser().subscribe(user => this.currentUser = user);
    this.loginService.isAdmin().subscribe(status => this.isAdmin = status);
  }

  getProduct(): void {
    const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
    this.productService.getProduct(id)
      .subscribe(products => this.product = products);
  }

  getCurrentUser(): void {
    this.loginService.getCurrentUser()
    .subscribe(current => this.currentUser = current);
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

  private log(message: string) {
    this.messageService.add(`LoginService: ${message}`);
  }
}