import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { CreatePostDto } from '../models/CreatePostDto';
import { Post } from '../models/Post';

@Injectable({
  providedIn: 'root'
})
export class AddPostService
{
  url = environment.baseUrl;

  constructor(private http: HttpClient){}

  addPost(newPost: CreatePostDto): Observable<Post>
  {
    return this.http.post<Post>(this.url + "/api/posts", newPost);
  }

  editPost(editPost: CreatePostDto): Observable<Post>
  {
    return this.http.put<Post>(this.url + "/api/posts", editPost);
  }
}

