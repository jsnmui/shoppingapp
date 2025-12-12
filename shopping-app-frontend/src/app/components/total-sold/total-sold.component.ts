import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../services/product.service';

@Component({
  selector: 'app-total-sold',
  templateUrl: './total-sold.component.html'
})
export class TotalSoldComponent implements OnInit {
  totalSold: number = 0;

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.productService.getTotalSoldItems().subscribe({
      next: (res) => { this.totalSold = res
     
      },
      error: () => alert('Failed to fetch total sold items')
    });
  }
}
