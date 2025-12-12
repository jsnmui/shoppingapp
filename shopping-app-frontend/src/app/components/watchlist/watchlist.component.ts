import { Component, OnInit } from '@angular/core';
import { WatchlistService } from '../../services/watchlist.service';

@Component({
  selector: 'app-watchlist',
  templateUrl: './watchlist.component.html',
  styleUrls: ['./watchlist.component.css']
})
export class WatchlistComponent implements OnInit {
  watchlist: any[] = [];

  constructor(private watchlistService: WatchlistService) {}

  ngOnInit(): void {
    this.loadWatchlist();
  }

  loadWatchlist(): void {
    this.watchlistService.getWatchlist().subscribe({
      next: (res) => this.watchlist = res as any[],
      error: () => alert('Failed to load watchlist')
    });
  }

  removeFromWatchlist(productId: number): void {
    this.watchlistService.removeFromWatchlist(productId).subscribe({
      next: () => {
        alert('Removed from watchlist');
        this.loadWatchlist();
      },
      error: () => alert('Failed to remove from watchlist')
    });
  }
}
