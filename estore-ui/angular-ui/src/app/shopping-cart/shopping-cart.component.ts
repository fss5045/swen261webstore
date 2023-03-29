import { Component } from '@angular/core';
import { CartService } from '../cart.service';
import { Product } from '../product';
import { ProductService } from '../product.service'
import { User } from '../user';
import { UserType } from '../user.type';
import { LoginService } from '../login.service';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent {
  products: Product[] = [];
  cart: Product[] = [];
  currentUser: User | undefined;
  isAdmin: boolean = false;
  isCustomer: boolean = false;

  constructor(
    private productService: ProductService,
    private cartService: CartService,
    private loginService: LoginService
    ) {}

  ngOnInit() {
    this.getProducts();
    this.getCurrentUser();
  }

  getProducts(): void {
    this.productService.getProducts().subscribe((products) => {
      this.products = products;
    });
  }

  getCurrentUser(): void {
    this.loginService.getCurrentUser()
    .subscribe(current => {this.currentUser = current
    this.isAdmin = (this.currentUser.userType.toString() === UserType[UserType.Admin]);
    this.isCustomer = (this.currentUser.userType.toString() === UserType[UserType.Customer]);
  });
  }

  removeFromCart(item: Product) {
    const index = this.cart.indexOf(item);
    if (index > -1) {
      this.cart.splice(index, 1);
    }
  }
}
