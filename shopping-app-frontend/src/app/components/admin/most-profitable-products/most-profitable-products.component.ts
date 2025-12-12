import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../../services/product.service';

@Component({
  selector: 'app-most-profitable-products',
  templateUrl: './most-profitable-products.component.html',
  styleUrls: ['./most-profitable-products.component.css']
})
export class MostProfitableProductsComponent implements OnInit {
  mostProfitableProducts: any[] = [];

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.productService.getMostProfitableProducts().subscribe({
      next: (data) => this.mostProfitableProducts = data,
      error: () => alert('Failed to load most profitable products')
    });
  }
}