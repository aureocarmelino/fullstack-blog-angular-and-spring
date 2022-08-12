import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { LoginDto } from '../models/LoginDto';

@Injectable({
  providedIn: 'root'
})
export class AuthService
{
  url = environment.baseUrl;
  jwtPayload: any;

  constructor(private http: HttpClient, private jwtHelper: JwtHelperService)
  {
    this.loadToken();
  }

  login(newLogin: LoginDto): Promise<void>
  {
    var header = new HttpHeaders();
    header = header.append('Content-Type', 'application/x-www-form-urlencoded');
    header = header.append('Authorization', 'Basic dGVzdGU6dGVzdGU=');

    var body = new HttpParams();
    body = body.append('username', newLogin.email as string);
    body = body.append('password', newLogin.password as string);
    body = body.append('grant_type', 'password');


    return this.http.post<any>(this.url + "/oauth/token", body, {headers: header, withCredentials: true}).toPromise()
    .then((response:any) =>
    {
      this.storeToken(response['access_token']);
    })
    .catch(response =>
    {
      if (response.status === 400)
      {
        if (response.error.error === 'invalid_grant')
        {
          return Promise.reject('Username or password is invalid!');
        }
      }

      if (response.status === 0)
      {
        if (response.statusText === 'Unknown Error')
        {
          return Promise.reject('Unavailable server');
        }
      }
      return Promise.reject(response);
    });

  }

  getNewAccessToken(): Promise<void>
  {
    var header = new HttpHeaders();
    header = header.append('Content-Type', 'application/x-www-form-urlencoded');
    header = header.append('Authorization', 'Basic dGVzdGU6dGVzdGU=');

    var body = new HttpParams();
    body = body.append('grant_type', 'refresh_token');

    return this.http.post<any>(this.url + "/oauth/token", body,{ headers: header, withCredentials: true}).toPromise()
    .then((response:any) =>
    {
      this.storeToken(response['access_token']);
      return Promise.resolve();

    }).catch(response =>
    {
      return Promise.resolve();
    });
  }

  isAccessTokenInvalid()
  {
    const token = localStorage.getItem('token');
    return !token || this.jwtHelper.isTokenExpired(token);
  }

  public storeToken(token: string)
  {
    this.jwtPayload = this.jwtHelper.decodeToken(token);
   // console.log(JSON.stringify(this.jwtPayload))
    localStorage.setItem('token', token);


  }

  public loadToken()
  {
    const token = localStorage.getItem('token');

    if ( token )
    {
      this.storeToken(token);
    }
  }

  clearAccessToken()
  {
    localStorage.removeItem('token');
    this.jwtPayload = null;

    //console.log("DELETING TOKEN")
  }

}
