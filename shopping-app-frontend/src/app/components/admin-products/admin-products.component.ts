import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ProductService } from '../../services/product.service';
import { Product } from '../product-detail/product.model';

@Component({
  selector: 'app-admin-products',
  templateUrl: './admin-products.component.html',
  styleUrls: ['./admin-products.component.css']
})
export class AdminProductsComponent implements OnInit {
  products: Product[] = [];
  displayedColumns: string[] = ['productId', 'name', 'retailPrice',  'actions'];

  constructor(
    private productService: ProductService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.productService.getAllProducts().subscribe({
      next: (data) => this.products = data,
      error: () => alert('Failed to load products')
    });
  }

  viewProduct(id: number): void {
    this.router.navigate(['/product-detail', id]);
  }

  editProduct(id: number): void {
    this.router.navigate(['/edit-product', id]);
  }
}
