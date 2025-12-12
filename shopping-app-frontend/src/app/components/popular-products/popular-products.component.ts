import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../services/product.service';
import { PopularProduct } from './popular-product.model';

@Component({
  selector: 'app-popular-products',
  templateUrl: './popular-products.component.html',
  styleUrls: ['./popular-products.component.css']
})
export class PopularProductsComponent implements OnInit {
  popularProducts: PopularProduct[] = [];

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.productService.getPopularProducts(3).subscribe({
      next: (res) =>{ this.popularProducts = res

      },
      error: () => alert('Failed to fetch popular products')
    });
  }
}
