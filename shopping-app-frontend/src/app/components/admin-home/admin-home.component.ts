import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { OrderService } from '../../services/order.service';
import { Router } from '@angular/router';
import { ProfitProduct } from './profit-product.model';
import { ProductService } from 'src/app/services/product.service';
import { Order } from '../user-home/order.model';

@Component({
  selector: 'app-admin-home',
  templateUrl: './admin-home.component.html',
  styleUrls: ['./admin-home.component.css']
})
export class AdminHomeComponent implements OnInit {
  displayedColumns: string[] = ['orderId', 'datePlaced', 'status',  'actions'];
 
  dataSource = new MatTableDataSource<Order>();
  
  @ViewChild(MatPaginator) paginator!: MatPaginator;

 
   mostProfitableProducts: ProfitProduct[] = [];

  constructor(private orderService: OrderService,private productService: ProductService,private router: Router) {}

  ngOnInit(): void {
    this.loadOrders();
     this.loadTopProfitableProducts();
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


   loadTopProfitableProducts(): void {
    this.productService.getTopProfitableProducts(1).subscribe({
     next: (data) => {
   
    this.mostProfitableProducts = data;
     
  },
      error: () => alert('Failed to load profitable products')
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
  this.router.navigate(['/order', orderId], { queryParams: { from: 'admin-home' } });
}
}
