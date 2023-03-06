import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { Products } from '../product';
import { ProductsService } from '../product.service';

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrls: [ './product-detail.component.css' ]
})
export class ProductDetailComponent implements OnInit {
  products: Products | undefined;

  constructor(
    private route: ActivatedRoute,
    private productsService: ProductsService,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.getHero();
  }

  getHero(): void {
    const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
    this.productsService.getProduct(id)
      .subscribe(products => this.products = products);
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    if (this.products) {
      this.productsService.updateProduct(this.products)
        .subscribe(() => this.goBack());
    }
  }
}