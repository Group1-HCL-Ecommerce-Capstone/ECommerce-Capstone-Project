import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from '../models/user';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserRegService {

  private userRegUrl: string;

  constructor(private http: HttpClient) { 
    this.userRegUrl = 'http://localhost:8181/register/reg';
  }

  public save(user: User){
    this.http.post<User>(this.userRegUrl, user).subscribe();

  }
}
