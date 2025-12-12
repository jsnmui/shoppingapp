import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../services/product.service';
import { CartService } from '../../services/cart.service';
import { WatchlistService } from '../../services/watchlist.service';
import { Product } from '../product-detail/product.model';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {
  products: Product[] = [];
  displayedColumns = ['productId','name', 'retailPrice', 'actions','view'];

  constructor(
    private productService: ProductService,
    private cartService: CartService,
    private watchlistService: WatchlistService
  ) {}

  ngOnInit(): void {
    this.productService.getAllProducts().subscribe({
      next: (data) => this.products = data,
      error: () => alert('Failed to load products')
    });
  }

 

  addToCart(product: Product): void {
  this.cartService.addToCart({
    productId: product.productId,
    name: product.name,
    retailPrice: product.retailPrice,
    quantity: 1
  });
  alert('Product added to cart');
}

  addToWatchlist(product: Product): void {
    this.watchlistService.addToWatchlist(product.productId).subscribe({
      next: () => alert('Added to watchlist'),
      error: () => alert('Failed to add to watchlist')
    });
  }
   
  removeFromWatchlist(productId: number): void {
  this.watchlistService.removeFromWatchlist(productId).subscribe({
    next: (res) => alert(res.message),
    error: () => alert('Failed to remove from watchlist')
  });
}

  
}
