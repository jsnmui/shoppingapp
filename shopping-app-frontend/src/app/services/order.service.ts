import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Order } from '../components/user-home/order.model';
import { OrderDetail } from '../components/order-detail/order-detail.model';
import {ProductSummary} from '../components/product-summary/product-summary.model'

@Injectable({ providedIn: 'root' })
export class OrderService {
  private baseUrl = 'https://shoppingapp-content.onrender.com';

  constructor(private http: HttpClient) {}

  
  getUserOrders(): Observable<Order[]> {
    const token = localStorage.getItem('token');
    const headers = {
    Authorization: `Bearer ${token}`
  };
  return this.http.get<Order[]>(`${this.baseUrl}/orders/all`, { headers });
  }

  cancelOrder(orderId: number) {
    return this.http.patch(`${this.baseUrl}/orders/${orderId}/cancel`, {});
  }

 getFrequentProducts(): Observable<ProductSummary[]> {
  return this.http.get<ProductSummary[]>(`${this.baseUrl}/products/frequent/3`);
}

 getRecentProducts(): Observable<ProductSummary[]> {
  return this.http.get<ProductSummary[]>(`${this.baseUrl}/products/recent/3`);
}

   getOrderById(orderId: number): Observable<OrderDetail> {
    return this.http.get<OrderDetail>(`${this.baseUrl}/orders/${orderId}`);
  }

   
  getAdminOrders(): Observable<Order[]> {
    const token = localStorage.getItem('token');
    const headers = {
    Authorization: `Bearer ${token}`
  };
  return this.http.get<Order[]>(`${this.baseUrl}/orders/all`, { headers });
  }


completeOrder(orderId: number) {
  return this.http.patch(`${this.baseUrl}/orders/${orderId}/complete`, {});
}


}
