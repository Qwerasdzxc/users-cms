import { Injectable } from '@angular/core';
import { User } from 'src/app/model';

@Injectable({
  providedIn: 'root'
})
export class ConfigService {
  private token: string;
  private user: User | null;

  constructor() {
    this.user = null;
    var localToken = localStorage.getItem('token');
    if (localToken != null) this.token = localToken;
    else this.token = '';
  }

  setToken(token: string): void {
    this.token = token;
    localStorage.setItem('token', token);
  }

  getToken(): string {
    return this.token;
  }

  setUser(user: User): void {
    this.user = user;
  }

  getUser(): User {
    return this.user!;
  }
}
