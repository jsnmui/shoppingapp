import { Component } from '@angular/core';
import { OrderService } from '../../services/order.service';

@Component({
  selector: 'app-order-search',
  templateUrl: './order-search.component.html',
  styleUrls: ['./order-search.component.css']
})
export class OrderSearchComponent {
  orderId!: number;
  order: any;
  notFound = false;

  constructor(private orderService: OrderService) {}

  searchOrder(): void {
    this.order = null;
    this.notFound = false;

    this.orderService.getOrderById(this.orderId).subscribe({
      next: (res) => this.order = res,
      error: () => this.notFound = true
    });
  }
}
