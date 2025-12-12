import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  

  onSubmit(): void {

 if (this.loginForm.invalid) return;
       
  this.authService.login(this.loginForm.value).subscribe({
    next: (res) => {
      this.authService.saveToken(res.token);
      const role = this.authService.getUserRole();
     
       if (role === 1) {
        this.router.navigate(['/admin-home']);
       } else {
        this.router.navigate(['/user-home']);
       }
    },
    error: () => alert('Bad credentials')
  });
}





}


