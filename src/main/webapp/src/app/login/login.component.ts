import {Component} from '@angular/core';
import {AuthService} from "../core/auth/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  credentials: Credentials = new Credentials();

  error: string;
  usernameError: string;
  passwordError: string;

  constructor(private authService: AuthService, private router: Router) {
  }

  submit() {
    this.clearErrors();
    if (this.validateForm()) {
      this.authService.login(this.credentials)
        .subscribe(
          _ => this.router.navigate(['']),
          errorResponse => {
            console.error(errorResponse);
            this.error = errorResponse.error || 'Invalid username or password';
          });
    }
  }

  private clearErrors() {
    this.error = null;
    this.usernameError = null;
    this.passwordError = null;
  }

  private validateForm(): boolean {
    if (!this.credentials.username) {
      this.usernameError = 'Please enter a username';
    }
    if (!this.credentials.password) {
      this.passwordError = 'Please enter a password';
    }
    return !this.usernameError && !this.passwordError;
  }
}

class Credentials {
  username: string;
  password: string;
}

