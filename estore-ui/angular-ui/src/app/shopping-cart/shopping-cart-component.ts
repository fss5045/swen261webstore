import { Component } from '@angular/core';
import { ProductService } from './product.service';

@Component({
  selector: 'app-shopping-cart',
  template: `
    <h1>Shopping Cart</h1>
    <div *ngFor="let product of products">
      <h2>{{ product.name }}</h2>
      <p>{{ product.description }}</p>
      <p>{{ product.price }}</p>
      <button (click)="addToCart(product)">Add to Cart</button>
    </div>
    <h2>Cart</h2>
    <div *ngFor="let item of cart">
      <h3>{{ item.product.name }}</h3>
      <p>{{ item.product.description }}</p>
      <p>{{ item.product.price }}</p>
      <button (click)="removeFromCart(item)">Remove</button>
    </div>
  `,
})
export class ShoppingCartComponent {
  products: any[];
  cart: any[] = [];

  constructor(private productService: ProductService) {}

  ngOnInit() {
    this.productService.getProducts().subscribe((products) => {
      this.products = products;
    });
  }

  addToCart(product: any) {
    this.cart.push({ product, quantity: 1 });
  }

  removeFromCart(item: any) {
    const index = this.cart.indexOf(item);
    if (index > -1) {
      this.cart.splice(index, 1);
    }
  }
}