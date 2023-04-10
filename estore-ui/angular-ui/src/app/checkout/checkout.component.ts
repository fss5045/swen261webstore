import { Component, OnInit } from '@angular/core';

import { Product } from '../product';
import { ProductService } from '../product.service'
import { User } from '../user';
import { Discount } from '../discount';
import { DiscountService } from '../discount.service';
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
  discountCode: string = "";
  amount: number = 0;
  applied: boolean = false;

  constructor(
    private productService: ProductService,
    private cartService: CartService,
    private loginService: LoginService,
    private discountService: DiscountService,
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
    this.total = this.subtotal;
  });
  }

  applyDiscount(): void{
    this.discountCode = this.discountCode.toUpperCase();
    this.log(`code: ${this.discountCode}`)
    this.discountService.getDiscount(this.discountCode).subscribe(discount => {
      this.log(`amount: ${discount.amount}`);
      this.amount = discount.amount;
      this.total = this.subtotal - ((this.subtotal/100) * this.amount);
      this.applied = true;
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
    var nums: any = {};
    for(var item of this.cart){
      this.log(`${item.id}, ${item.name}, ${item.number}`);
      nums[item.id] = (nums[item.id] || 0) + 1;
      this.log(`n:${nums[item.id]}`)
      item.number = item.number - nums[item.id];
      this.log(`${item.name}, ${item.number}`);
      this.productService.updateProduct(item).subscribe(p => this.log(` new ${p.number}`));
      this.cartService.remove(item.id).subscribe();
    }
   
    // window.location.reload();
    this.router.navigate(["/done"]);
  }

  private log(message: string) {
    this.messageService.add(`CheckoutComponent: ${message}`);
  }
}
