import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { UserHomeComponent } from './components/user-home/user-home.component';
import { OrderDetailComponent  } from './components/order-detail/order-detail.component';
import { ProductDetailComponent } from './components/product-detail/product-detail.component';
import { ProductsComponent } from './components/products/products.component';
import { ShoppingCartComponent } from './components/shopping-cart/shopping-cart.component';
import { WatchlistComponent } from './components/watchlist/watchlist.component';
import { ProductDetailPageComponent } from './components/product-detail-page/product-detail-page.component';
import { AdminHomeComponent } from './components/admin-home/admin-home.component';
import { AdminProductsComponent } from './components/admin-products/admin-products.component';
import { EditProductComponent } from './components/admin/edit-product/edit-product.component';
import { AddProductComponent } from './components/admin/add-product/add-product.component';
import { AdminOrderManagementComponent } from './components/admin-order-management/admin-order-management.component';
import { OrderSearchComponent } from './components/order-search/order-search.component';
import { AuthGuard } from './auth.guard';
import { AdminGuard } from './admin.guard';

const routes: Routes = [
  { path: 'user-home', component: UserHomeComponent, canActivate: [AuthGuard] },
  { path: 'admin-home', component: AdminHomeComponent,  canActivate: [AuthGuard, AdminGuard] },
  { path: 'admin-order-management', component: AdminOrderManagementComponent,  canActivate: [AuthGuard, AdminGuard]},
  { path: 'order/:id', component: OrderDetailComponent, canActivate: [AuthGuard] },
  { path: 'products', component: ProductsComponent, canActivate: [AuthGuard] },
  { path: 'admin-products', component: AdminProductsComponent,  canActivate: [AuthGuard, AdminGuard] },
  { path: 'cart', component: ShoppingCartComponent,canActivate: [AuthGuard] },
  { path: 'watchlist', component: WatchlistComponent,canActivate: [AuthGuard] },
  { path: 'product-detail/:id', component: ProductDetailPageComponent,canActivate: [AuthGuard] },
  { path: 'product/:id', component: ProductDetailComponent,canActivate: [AuthGuard] },
  { path: 'product-detail/:id', component: ProductDetailComponent,canActivate: [AuthGuard] },
  { path: 'admin/edit-product/:id', component: EditProductComponent, canActivate: [AuthGuard, AdminGuard] },
  { path: 'admin/add-product', component: AddProductComponent,  canActivate: [AuthGuard, AdminGuard] },
  { path: 'search-order', component: OrderSearchComponent,canActivate: [AuthGuard, AdminGuard] },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: '**', redirectTo: 'login' } // fallback route
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
