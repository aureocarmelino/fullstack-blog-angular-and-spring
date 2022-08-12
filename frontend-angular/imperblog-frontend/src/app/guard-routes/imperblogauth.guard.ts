import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class ImperblogauthGuard implements CanActivate
{
  constructor(
    private auth: AuthService,
    private router: Router
  ) {}

  canActivate( route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree
    {

      if (this.auth.isAccessTokenInvalid())
      {
        //console.log('Browsing with invalid access token. Getting new token...');

        return this.auth.getNewAccessToken()
          .then(() =>
          {
            if (this.auth.isAccessTokenInvalid())
            {
              this.router.navigate(['/login']);
              return false;
            }

            return true;
          });
      }
      return true;
  }
}
