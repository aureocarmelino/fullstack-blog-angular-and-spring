import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ChartDto } from '../models/ChartDto';
import { Post } from '../models/Post';

@Injectable({
  providedIn: 'root'
})
export class ListPostService {

  url = environment.baseUrl;

  constructor(private http: HttpClient){}

  findAllPost(): Observable<Post[]>
  {
    var header = new HttpHeaders();
    header = header.append('Content-Type', 'application/json');

    return this.http.get<Post[]>(this.url + "/api/posts", {headers: header});
  }



  findAllMyPost(): Observable<Post[]>
  {
    var header = new HttpHeaders();
    header = header.append('Content-Type', 'application/json');

    return this.http.get<Post[]>(this.url + "/api/posts/user", {headers: header});
  }

  findByPostId(id : number): Observable<Post>
  {
    var header = new HttpHeaders();
    header = header.append('Content-Type', 'application/json');

    return this.http.get<Post>(this.url + "/api/posts/"+id, {headers: header});
  }

  findTotalAuthorsByGender(): Observable<ChartDto[]>
  {
    var header = new HttpHeaders();
    header = header.append('Content-Type', 'application/json');

    return this.http.get<ChartDto[]>(this.url + "/api/chart", {headers: header});
  }

  deleteById(id: number): Observable<void>
  {
    return this.http.delete<void>(this.url + "/api/posts/"+id);
  }


}
