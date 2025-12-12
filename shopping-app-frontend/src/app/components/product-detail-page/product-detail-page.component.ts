import { Component, OnInit } from '@angular/core';
import { ActivatedRoute,Router } from '@angular/router';
import { ProductService } from '../../services/product.service';
import { Product } from '../product-detail/product.model';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-product-detail-page',
  templateUrl: './product-detail-page.component.html',
  styleUrls: ['./product-detail-page.component.css']
})
export class ProductDetailPageComponent implements OnInit {
  product?: Product;
  isAdmin = false;

  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    private router: Router,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    const id = +this.route.snapshot.paramMap.get('id')!;
    this.productService.getProductById(id).subscribe({
      next: (data) => {
        this.product = data;

        // Infer role from the presence of fields or implement a proper role check
        this.isAdmin = data.hasOwnProperty('wholesalePrice');
      },
      error: () => alert('Failed to load product')
    });
  }
  goBack(): void {
    const role = this.authService.getUserRole();
    if (role === 1) {
      this.router.navigate(['/admin-products']);
    } else {
      this.router.navigate(['/products']);
    }
  }
}
