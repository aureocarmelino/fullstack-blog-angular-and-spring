import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable, from } from 'rxjs';
import { mergeMap } from 'rxjs/operators';
import { AuthService } from '../services/auth.service';

export class NotAuthenticatedError {}

@Injectable()
export class ImperBlogHttpInterceptor implements HttpInterceptor
{

  constructor(private auth: AuthService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>>
  {

    if(req.url.includes('/api/auth/signup'))
    {
      return next.handle(req);
    }

    if (!req.url.includes('/oauth/token') && this.auth.isAccessTokenInvalid())
    {
      return from(this.auth.getNewAccessToken())
        .pipe(
          mergeMap(() => {

            if (this.auth.isAccessTokenInvalid())
            {
              throw new NotAuthenticatedError();
            }

            alert(localStorage.getItem('token'))
            req = req.clone({
              setHeaders: {
                Authorization: `Bearer ${localStorage.getItem('token')}`
              }
            });
            return next.handle(req);
          })
        );
    }
    return next.handle(req);
  }
}
