import { Component, OnInit } from '@angular/core';

import { Product } from '../product';
import { ProductService } from '../product.service'
import { User } from '../user';
import { LoginService } from '../login.service';
import { CartService } from '../cart.service';

import { MessageService } from '../message.service'
import { Router } from '@angular/router';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit{
  currentUser: User | undefined;
  cart: Product[] = [];
  subtotal: number = 0;
  total: number = 0;

  constructor(
    private productService: ProductService,
    private cartService: CartService,
    private loginService: LoginService,
    private messageService: MessageService,
    private router: Router
  ){}

  ngOnInit(): void {
    this.getCurrentUser();
  }

  getCurrentUser(): void {
    this.loginService.getCurrentUser()
    .subscribe(current => {this.currentUser = current;
    this.cart = this.currentUser.cart;
    for (var item of this.cart){
      this.subtotal += item.price;
    }
  });
  }

  getTotal(): number {
    this.total = this.subtotal;
    return this.total;
  }

  checkout(): void {
    this.emptyCart();
  }

  emptyCart(): void {
    for(var item of this.cart){
      item.number = item.number - 1;
      this.log(`${item.name}, ${item.number}`);
      this.productService.updateProduct(item).subscribe();
      this.cartService.remove(item.id).subscribe();
    }
    // window.location.reload();
    this.router.navigate(["/done"]);
  }

  private log(message: string) {
    this.messageService.add(`CheckoutComponent: ${message}`);
  }
}
