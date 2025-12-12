import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class WatchlistService {
  private baseUrl = 'https://shoppingapp-content.onrender.com/watchlist';

  constructor(private http: HttpClient) {}

  addToWatchlist(productId: number) {
  return this.http.post<{ message: string }>(`${this.baseUrl}/product/${productId}`, {});
}
  getWatchlist() {
    return this.http.get(`${this.baseUrl}/products/all`);
  }

  removeFromWatchlist(productId: number) {
  return this.http.delete<{ message: string }>(`${this.baseUrl}/product/${productId}`);
 }

}

