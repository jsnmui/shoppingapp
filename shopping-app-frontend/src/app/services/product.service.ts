import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Product } from '../components/product-detail/product.model'
import { Observable } from 'rxjs';
import { ProfitProduct } from '../components/admin-home/profit-product.model';
import { PopularProduct } from '../components/popular-products/popular-product.model';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private baseUrl = 'https://shoppingapp-content.onrender.com';   

  constructor(private http: HttpClient) { }

  getAllProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.baseUrl}/products/all`);
  }

  getProductById(id: number): Observable<Product> {
    return this.http.get<Product>(`${this.baseUrl}/products/${id}`);
  }

  getTopProfitableProducts(limit: number): Observable<ProfitProduct[]> {
  return this.http.get<ProfitProduct[]>(`${this.baseUrl}/products/profit/${limit}`);
}

getPopularProducts(limit: number) {
  return this.http.get<PopularProduct[]>(`${this.baseUrl}/products/popular/${limit}`);
}

getTotalSoldItems() {
  return this.http.get<number>(`${this.baseUrl}/products/sold/total`);
}

updateProduct(productId: number, productData: any) {
  return this.http.patch(`${this.baseUrl}/products/${productId}`, productData);
}

addProduct(product: any) {
  return this.http.post(`${this.baseUrl}/products`, product);
}
getMostProfitableProducts() {
  return this.http.get<any[]>(`${this.baseUrl}/products/profit/1`);
}

}
