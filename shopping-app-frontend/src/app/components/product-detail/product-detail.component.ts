import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductService } from '../../services/product.service';
import { Product } from './product.model';
import { AuthService } from '../../services/auth.service';


@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.css']
})
export class ProductDetailComponent implements OnInit {
  product?: Product;
  orderId?: number;
  from: string | null = null;
  

  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    private router: Router,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.from = this.route.snapshot.queryParamMap.get('from');
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.route.queryParams.subscribe(params => {
    const orderIdParam = params['orderId'];
    if (orderIdParam) {
      this.orderId = Number(orderIdParam);
    }
  });

    if (id) {
      this.productService.getProductById(id).subscribe({
        next: (data) => this.product = data,
        error: () => alert('Failed to load product')
      });
    }
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
}
