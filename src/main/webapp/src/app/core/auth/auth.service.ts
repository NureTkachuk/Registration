import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {tap} from "rxjs/operators";
import {Router} from "@angular/router";
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private httpClient: HttpClient, private router: Router) {}

  login(credentials): Observable<any> {
    return this.httpClient.post(`${environment.apiUrl}/auth`, credentials)
      .pipe(
        tap(authResponse => localStorage.setItem('accessToken', authResponse.accessToken))
      );
  }

  logout() {
    localStorage.removeItem('accessToken');
    this.router.navigate(['login']);
  }

  getAccessToken(): string {
    return localStorage.getItem('accessToken');
  }

  isAuthenticated(): boolean {
    const accessToken = this.getAccessToken();
    return !!accessToken;
  }
}
