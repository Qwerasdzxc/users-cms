import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { ConfigService } from '../config/config.service';
import { LoginResponse, User } from 'src/app/model';
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

  getUsers(): Observable<Array<User>> {
    let token = this.configService.getToken();
    return this.httpClient.get<Array<User>>(
      `${this.apiUrl}/api/users/all`, {
        headers: {'Authorization': `Bearer ${token}`}
     }
    );
  }

  getUserById(id: string): Observable<User> {
    let token = this.configService.getToken();
    return this.httpClient.get<User>(
      `${this.apiUrl}/api/users/${id}`, {
        headers: {'Authorization': `Bearer ${token}`}
     }
    );
  }

  getLoggedInUser(): Observable<User> {
    let token = this.configService.getToken();
    return this.httpClient.get<User>(
      `${this.apiUrl}/api/users/me`, {
        headers: {'Authorization': `Bearer ${token}`}
     }
    );
  }

  deleteUserById(id: number): Observable<void> {
    let token = this.configService.getToken();
    return this.httpClient.delete<void>(
      `${this.apiUrl}/api/users/${id}`, {
        headers: {'Authorization': `Bearer ${token}`}
     }
    );
  }

  createUser(
    username: string,
    password: string,
    name: string,
    surname: string,
    readPermission: boolean,
    createPermission: boolean,
    updatePermission: boolean,
    deletePermission: boolean
  ): Observable<User> {
    let token = this.configService.getToken();
    return this.httpClient.post<User>(
      `${this.apiUrl}/api/users`,
      {
        username: username,
        password: password,
        name: name,
        surname: surname,
        readPermission: readPermission,
        createPermission: createPermission,
        updatePermission: updatePermission,
        deletePermission: deletePermission
      },
      {
        headers: {'Authorization': `Bearer ${token}`}
      }
    )
  }

  editUser(
    username: string,
    name: string,
    surname: string,
    readPermission: boolean,
    createPermission: boolean,
    updatePermission: boolean,
    deletePermission: boolean
  ): Observable<User> {
    let token = this.configService.getToken();
    return this.httpClient.put<User>(
      `${this.apiUrl}/api/users`,
      {
        username: username,
        name: name,
        surname: surname,
        readPermission: readPermission,
        createPermission: createPermission,
        updatePermission: updatePermission,
        deletePermission: deletePermission
      },
      {
        headers: {'Authorization': `Bearer ${token}`}
      }
    )
  }
}
