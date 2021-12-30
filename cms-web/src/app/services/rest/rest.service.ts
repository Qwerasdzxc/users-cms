import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { ConfigService } from '../config/config.service';
import { LoginResponse } from 'src/app/model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RestService {

  private readonly apiUrl = environment.apiUrl;

  constructor(
    private configService: ConfigService,
    private httpClient: HttpClient
  ) {}

  login(
    username: string,
    password: string
  ): Observable<LoginResponse> {
    return this.httpClient.post<LoginResponse>(
      `${this.apiUrl}/auth/login`,
      {
          username: username,
          password: password,
      }
    );
  }
}
