import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {
  constructor(private authService: AuthService, private router: Router) {}

  logout(): void {
    this.authService.logout(); // remove token
    this.router.navigate(['/login']);
  }

  isLoggedIn(): boolean {
    return !!this.authService.getToken();
  }

  isAdmin(): number | null {
    return this.authService.getUserRole();
  }

  
  goHome(): void {
    const role = this.authService.getUserRole();
    if (role === 1) {
      this.router.navigate(['/admin-home']);
    } else {
      this.router.navigate(['/user-home']);
    }
  }

  goToProducts(): void {
    const role = this.authService.getUserRole();
    if (role === 1) {
      this.router.navigate(['/admin-products']);
    } else {
      this.router.navigate(['/products']);
    }
  }

}
