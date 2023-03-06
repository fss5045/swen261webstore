import { Component, OnInit } from '@angular/core';
import { Products } from '../product';
import { ProductsService } from '../product.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: [ './dashboard.component.css' ]
})
export class DashboardComponent implements OnInit {
  products: Products[] = [];

  constructor(private productsService: ProductsService) { }

  ngOnInit(): void {
    this.getHeroes();
  }

  getHeroes(): void {
    this.productsService.getProducts()
      .subscribe(products => this.products = products.slice(1, 5));
  }
}