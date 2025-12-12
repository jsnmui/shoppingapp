import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { CartService } from './cart.service';

interface LoginResponse {
  message: string;
  token: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {

   private baseUrl = 'https://shoppingapp-auth.onrender.com';

  constructor(private http: HttpClient, private router: Router, private cartService: CartService  ) {}

  
  register(data: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/signup`, data);
  }

  
  login(credentials: { username: string; password: string }): Observable<LoginResponse> {
    this.cartService.clearCart(); 
    return this.http.post<LoginResponse>(`${this.baseUrl}/login`, credentials);
  }

  
  saveToken(token: string): void {
    localStorage.setItem('token', token);
  }


  getToken(): string | null {
    return localStorage.getItem('token');
  }

  logout(): void {
    localStorage.removeItem('token');
    this.cartService.clearCart();  // clears memory + storage
    this.router.navigate(['/login']);
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  getUserRole(): number | null {
  const token = localStorage.getItem('token');
  if (!token) return null;

  const payload = JSON.parse(atob(token.split('.')[1]));
  return payload.role;
}

  
}
