import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { OrderService } from '../../services/order.service';
import { OrderDetail } from './order-detail.model';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-order-detail',
  templateUrl: './order-detail.component.html',
  styleUrls: ['./order-detail.component.css']
})
export class OrderDetailComponent implements OnInit {
  order!: OrderDetail;
  orderId!: number;

  constructor(
    private route: ActivatedRoute,
    private orderService: OrderService,
    private router: Router,
    private authService: AuthService
  ) {}

  routeFrom: string | null = null;
  ngOnInit(): void {
     this.routeFrom = this.route.snapshot.queryParamMap.get('from');
    this.orderId = Number(this.route.snapshot.paramMap.get('id'));
    this.orderService.getOrderById(this.orderId).subscribe({
      next: (res) => this.order = res,
      error: () => alert('Failed to fetch order details')
    });
  }

   
  getOrderTotal(): number {
  return this.order.items.reduce((sum, item) => sum + (item.quantity * item.purchasedPrice), 0);
  }

  //  goBack(): void {
  //   const role = this.authService.getUserRole();
  //   if (role === 1) {
  //     this.router.navigate(['/admin-home']);
  //   } else {
  //     this.router.navigate(['/user-home']);
  //   }
  // }

  goBack(): void {
  const from = this.route.snapshot.queryParamMap.get('from');

  switch (from) {
    case 'admin-orders':
      this.router.navigate(['/admin-order-management']);
      break;
    case 'admin-home':
      this.router.navigate(['/admin-home']);
      break;
    default:
      this.router.navigate(['/user-home']);
      break;
  }
 }

    cancelOrder(): void {
    this.orderService.cancelOrder(this.orderId).subscribe({
      next: () => {
        alert('Order canceled');
        this.ngOnInit(); // refresh data
      },
      error: () => alert('Cannot cancel this order')
    });
  }


}
