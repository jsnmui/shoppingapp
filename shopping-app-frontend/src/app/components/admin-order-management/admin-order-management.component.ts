import { Component, OnInit, ViewChild } from '@angular/core';
import { OrderService } from '../../services/order.service';
import { Router } from '@angular/router';
import { Order } from '../user-home/order.model';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-admin-order-management',
  templateUrl: './admin-order-management.component.html',
  styleUrls: ['./admin-order-management.component.css']
})
export class AdminOrderManagementComponent implements OnInit {
  orders: Order[] = [];
  displayedColumns = ['orderId', 'datePlaced', 'orderStatus', 'actions'];

   dataSource = new MatTableDataSource<Order>();
    
    @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private orderService: OrderService, private router: Router) {}

  ngOnInit(): void {
    this.loadOrders();
  }

  loadOrders(): void {
    this.orderService.getAdminOrders().subscribe({
      next: (orders) => {
        this.dataSource.data = orders;
        this.dataSource.paginator = this.paginator;
      },
      error: () => alert('Failed to load orders')
    });
  }

  cancelOrder(orderId: number): void {
  this.orderService.cancelOrder(orderId).subscribe({
    next: (res: any) => {
      alert(res.message || 'Order canceled');
      this.loadOrders();
    },
    error: (err) => {
      const message = err?.error?.message || 'Unable to cancel order';
      alert(message);
    }
  });
}

  
    completeOrder(orderId: number): void {
  this.orderService.completeOrder(orderId).subscribe({
    next: (res: any) => {
      alert(res.message || 'Order marked as completed');
      this.loadOrders();
    },
    error: (err) => {
      const message = err?.error?.message || 'Unable to complete order';
      alert(message);
    }
  });
}


  viewOrder(orderId: number): void {
  this.router.navigate(['/order', orderId], { queryParams: { from: 'admin-orders' } });
}

}

