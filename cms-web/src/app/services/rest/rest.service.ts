import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { ConfigService } from '../config/config.service';
import { LoginResponse, Machine, MachineOperationError, User } from 'src/app/model';
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

  createMachine(
    name: string,
  ): Observable<Machine> {
    let token = this.configService.getToken();
    return this.httpClient.post<Machine>(
      `${this.apiUrl}/api/machines`,
      {
        name: name,
      },
      {
        headers: {'Authorization': `Bearer ${token}`}
      }
    )
  }

  getMachines(from: string | null, to: string | null, name: string | null, status: string | null): Observable<Array<Machine>> {
    let token = this.configService.getToken();
    var params = new HttpParams();
    if (from != null) {
      params = params.set('from', from);
    }
    if (to != null) {
      params = params.set('to', to);
    }
    if (name != null) {
      params = params.set('name', name);
    }
    if (status != null) {
      params = params.set('status', status);
    }

    return this.httpClient.get<Array<Machine>>(
      `${this.apiUrl}/api/machines/all`, {
        params: params,
        headers: {'Authorization': `Bearer ${token}`}
     }
    );
  }

  startMachine(machineId: number, msDelay: number): Observable<void> {
    let token = this.configService.getToken();
    return this.httpClient.post<void>(
      `${this.apiUrl}/api/machines/start/${machineId}`,
      {},
      {
        params: {'date': msDelay},
        headers: {'Authorization': `Bearer ${token}`}
     }
    );
  }

  restartMachine(machineId: number, msDelay: number): Observable<void> {
    let token = this.configService.getToken();
    return this.httpClient.post<void>(
      `${this.apiUrl}/api/machines/restart/${machineId}`,
      {},
      {
        params: {'date': msDelay},
        headers: {'Authorization': `Bearer ${token}`}
     }
    );
  }

  stopMachine(machineId: number, msDelay: number): Observable<void> {
    let token = this.configService.getToken();
    return this.httpClient.post<void>(
      `${this.apiUrl}/api/machines/stop/${machineId}`,
      {},
      {
        params: {'date': msDelay},
        headers: {'Authorization': `Bearer ${token}`}
     }
    );
  }

  destroyMachine(id: number): Observable<void> {
    let token = this.configService.getToken();
    return this.httpClient.delete<void>(
      `${this.apiUrl}/api/machines/${id}`, {
        headers: {'Authorization': `Bearer ${token}`}
     }
    );
  }

  getOperationErrors(): Observable<Array<MachineOperationError>> {
    let token = this.configService.getToken();
    return this.httpClient.get<Array<MachineOperationError>>(
      `${this.apiUrl}/api/operation-errors/all`, {
        headers: {'Authorization': `Bearer ${token}`}
     }
    );
  }
}
