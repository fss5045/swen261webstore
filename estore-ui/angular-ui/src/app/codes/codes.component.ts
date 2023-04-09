import { Component, OnInit } from '@angular/core';

import { Discount } from '../discount';
import { DiscountService } from '../discount.service';
import { LoginService } from '../login.service';

import { MessageService } from '../message.service'
import { Router } from '@angular/router';


@Component({
  selector: 'app-codes',
  templateUrl: './codes.component.html',
  styleUrls: ['./codes.component.css']
})
export class CodesComponent implements OnInit{
  discounts: Discount[] = [];
  code: string = "";
  amount: number = 0;

  constructor(
    private loginService: LoginService,
    private discountService: DiscountService,
    private messageService: MessageService) { }

  ngOnInit(): void {
    this.getDiscounts();
  }

  getDiscounts(): void {
    this.discountService.getDiscounts()
    .subscribe(codes => {
    this.discounts = codes;
    });
  }

  add(): void {
    this.log(`${this.code}, ${this.amount}`)
    if ((this.code === "") || (this.amount === 0)) { return; }
    var name: string = this.code.toUpperCase();
    var discount = { name } as Discount;
    discount.amount = this.amount;
    this.discountService.addDiscount(discount)
      .subscribe(code => {
        this.discounts.push(code);
      });
  }

  delete(code: Discount): void {
    this.discounts = this.discounts.filter(h => h !== code);
    this.discountService.deleteDiscount(code.name).subscribe();
  }

  private log(message: string) {
    this.messageService.add(`CodesComponent: ${message}`);
  }
}
