import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { ConfigService } from '../services/config/config.service';

@Injectable({
  providedIn: 'root'
})
export class EditUserGuard implements CanActivate {

  constructor(private configService: ConfigService) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      return this.configService.getUser() != null ? this.configService.getUser().permissions.canUpdateUsers : false;
  }
  
}
