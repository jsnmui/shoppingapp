import { Component, OnInit, ViewChild } from '@angular/core';
import { OrderService } from '../../services/order.service';
import { Order } from './order.model';
import { Router } from '@angular/router';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-user-home',
  templateUrl: './user-home.component.html',
  styleUrls: ['./user-home.component.css']
})
export class UserHomeComponent implements OnInit {
  displayedColumns: string[] = ['orderId', 'datePlaced', 'status', 'actions'];
  orders: Order[] = [];
  dataSource = new MatTableDataSource<Order>();
    
    @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private orderService: OrderService, private router: Router) {}

  ngOnInit(): void {
     this.loadOrders();
  }
cancelOrder(orderId: number): void {
  this.orderService.cancelOrder(orderId).subscribe({
    next: () => {
      alert('Order canceled');
      this.loadOrders();
    },
    error: (err) => {
      console.error('Cancel error:', err);
      alert('Cannot cancel this order.');
    }
  });
}
   
  

// loadOrders(): void {
//   this.orderService.getUserOrders().subscribe({
//     next: (res: Order[]) => this.orders = res,
//     error: (err) => {
//       console.error('Fetch orders error:', err);
//       alert('Failed to fetch orders');
//     }
//   });
// }
loadOrders(): void {
    this.orderService.getAdminOrders().subscribe({
      next: (orders) => {
        this.dataSource.data = orders;
        this.dataSource.paginator = this.paginator;
      },
      error: () => alert('Failed to load orders')
    });
  }

  viewOrder(orderId: number): void {
    this.router.navigate(['/order', orderId]); // needs a route like /order/:id
  }
}
