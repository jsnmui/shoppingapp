import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductService } from 'src/app/services/product.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Product } from '../../product-detail/product.model';

@Component({
  selector: 'app-edit-product',
  templateUrl: './edit-product.component.html',
  styleUrls: ['./edit-product.component.css']
})
export class EditProductComponent implements OnInit {
  productForm!: FormGroup;
  productId!: number;

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private productService: ProductService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.productId = +this.route.snapshot.paramMap.get('id')!;
    this.productService.getProductById(this.productId).subscribe({
      next: (product) => {
        this.productForm = this.fb.group({
          name: [product.name, Validators.required],
          description: [product.description],
          retailPrice: [product.retailPrice, [Validators.required, Validators.min(0)]],
          wholesalePrice: [product.wholesalePrice, [Validators.required, Validators.min(0)]],
          quantity: [product.quantity, [Validators.required, Validators.min(0)]]
        });
      },
      error: () => alert('Failed to load product')
    });
  }

  onSubmit(): void {
    if (this.productForm.valid) {
      this.productService.updateProduct(this.productId, this.productForm.value).subscribe({
        next: () => {
          alert('Product updated successfully');
          this.router.navigate(['/admin-products']);
        },
        error: () => alert('Failed to update product')
      });
    }
  }
}
