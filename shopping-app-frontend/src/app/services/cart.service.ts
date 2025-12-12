import { Injectable } from '@angular/core';
import { CartItem } from '../components/shopping-cart/cart-item.model';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private cart: CartItem[] = [];

  constructor() {
    const stored = localStorage.getItem('cart');
    this.cart = stored ? JSON.parse(stored) : [];
  }

  getCart(): CartItem[] {
    return this.cart;
  }

  clearCart() {
    this.cart = [];
    localStorage.removeItem('cart');
  }

  addToCart(item: CartItem): void {
    const existing = this.cart.find(p => p.productId === item.productId);
    if (existing) {
      existing.quantity += item.quantity;
    } else {
      this.cart.push(item);
    }

    localStorage.setItem('cart', JSON.stringify(this.cart));
  }

  removeFromCart(productId: number) {
    this.cart = this.cart.filter(p => p.productId !== productId);
    localStorage.setItem('cart', JSON.stringify(this.cart));
  }
}

