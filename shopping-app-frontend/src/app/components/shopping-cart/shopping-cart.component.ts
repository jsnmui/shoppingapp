import { Component, OnInit } from '@angular/core';
import { CartService } from '../../services/cart.service';
import { Product } from '../product-detail/product.model';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { CartItem } from './cart-item.model';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent implements OnInit {
 cartItems: CartItem[] = [];

    constructor(
    private cartService: CartService,
    private http: HttpClient,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.cartItems = this.cartService.getCart();
  }

  clearCart() {
    this.cartService.clearCart();
    this.cartItems = [];
  }

  remove(productId: number): void {
    this.cartService.removeFromCart(productId);
    this.cartItems = this.cartService.getCart();
  }
   

  //  placeOrder(): void {
  //   const orderPayload = {
  //     order: this.cartItems.map(item => ({
  //       productId: item.productId,
  //       quantity: item.quantity
  //     }))
  //   };

  //   this.http.post('http://localhost:8080/orders', orderPayload).subscribe({
  //     next: () => {
  //       alert('Order placed successfully!');
  //       this.cartService.clearCart();
  //       this.router.navigate(['/user-home']); // or redirect anywhere
  //     },
  //     error: () => {
  //       alert('Failed to place order');
  //     }
  //   });
  // }

  placeOrder(): void {
  const orderPayload = {
    order: this.cartItems.map(item => ({
      productId: item.productId,
      quantity: item.quantity
    }))
  };

  this.http.post('http://localhost:8080/orders', orderPayload).subscribe({
    next: (response: any) => {
      alert(response.message || 'Order placed successfully!');
      this.cartService.clearCart();
      this.router.navigate(['/user-home']);
    },
    error: (err) => {
      const errorMsg =
        err.error?.error || err.error?.message || 'Failed to place order';
      alert(errorMsg);
    }
  });
}


}
