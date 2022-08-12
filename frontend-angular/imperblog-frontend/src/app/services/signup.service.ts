import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Author } from '../models/Author';

@Injectable({
  providedIn: 'root'
})
export class SignupService
{
  url = environment.baseUrl;

  constructor(private http: HttpClient){}

  signup(newAuthor: Author): Observable<Author>
  {
    return this.http.post<Author>(this.url + "/api/auth/signup", newAuthor);
  }
}
