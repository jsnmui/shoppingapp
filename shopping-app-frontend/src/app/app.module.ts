import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthInterceptor } from './auth.interceptor';


import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { UserHomeComponent } from './components/user-home/user-home.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { OrderDetailComponent } from './components/order-detail/order-detail.component';
import { ProductSummaryComponent } from './components/product-summary/product-summary.component';
import { ProductDetailComponent } from './components/product-detail/product-detail.component';
import { ProductsComponent } from './components/products/products.component';

import { WatchlistComponent } from './components/watchlist/watchlist.component';
import { ShoppingCartComponent } from './components/shopping-cart/shopping-cart.component';
import { ProductDetailPageComponent } from './components/product-detail-page/product-detail-page.component';
import { AdminHomeComponent } from './components/admin-home/admin-home.component';
import { PopularProductsComponent } from './components/popular-products/popular-products.component';
import { TotalSoldComponent } from './components/total-sold/total-sold.component';
import { AdminProductsComponent } from './components/admin-products/admin-products.component';
import { EditProductComponent } from './components/admin/edit-product/edit-product.component';
import { AddProductComponent } from './components/admin/add-product/add-product.component';
import { AdminOrderManagementComponent } from './components/admin-order-management/admin-order-management.component';
import { MostProfitableProductsComponent } from './components/admin/most-profitable-products/most-profitable-products.component';
import { OrderSearchComponent } from './components/order-search/order-search.component';
import { MatPaginatorModule } from '@angular/material/paginator';
@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    NavbarComponent,
    UserHomeComponent,
    OrderDetailComponent,
    ProductSummaryComponent,
    ProductDetailComponent,
    ProductsComponent,
    WatchlistComponent,
    ShoppingCartComponent,
    ProductDetailPageComponent,
    AdminHomeComponent,
    PopularProductsComponent,
    TotalSoldComponent,
    AdminProductsComponent,
    EditProductComponent,
    AddProductComponent,
    AdminOrderManagementComponent,
    MostProfitableProductsComponent,
    OrderSearchComponent
  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    ReactiveFormsModule,
    FormsModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    MatPaginatorModule,
  ],
  providers: [{ provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }],
  bootstrap: [AppComponent]
})
export class AppModule { }
