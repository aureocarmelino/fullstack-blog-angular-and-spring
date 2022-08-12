import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class LogoutService {

  tokensRevokeUrl = environment.baseUrl;

  constructor( private http: HttpClient, private auth: AuthService) { }

  async logout()
  {
    await this.http.delete(this.tokensRevokeUrl+"/tokens/revoke", { withCredentials: true }).toPromise();
    this.auth.clearAccessToken();
  }
}
