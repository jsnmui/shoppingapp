import { Component, OnInit } from '@angular/core';
import { ProductSummary } from './product-summary.model';
import { OrderService } from 'src/app/services/order.service';

@Component({
  selector: 'app-product-summary',
  templateUrl: './product-summary.component.html',
  styleUrls: ['./product-summary.component.css']
})
export class ProductSummaryComponent implements OnInit {
  frequentProducts: ProductSummary[] = [];
  recentProducts: ProductSummary[] = [];

  constructor(private orderService: OrderService) {}

  ngOnInit(): void {
    this.loadFrequentProducts();
    this.loadRecentProducts();
  }

  loadFrequentProducts(): void {
    this.orderService.getFrequentProducts().subscribe({
      next: (res) => this.frequentProducts = res,
      error: () => alert('Failed to load frequent products')
    });
  }

  loadRecentProducts(): void {
    this.orderService.getRecentProducts().subscribe({
      next: (res) => this.recentProducts = res,
      error: () => alert('Failed to load recent products')
    });
  }
}
