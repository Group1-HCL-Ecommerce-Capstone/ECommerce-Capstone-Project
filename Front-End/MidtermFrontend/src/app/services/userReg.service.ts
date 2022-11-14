import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../models/user';
import { LocalService } from './local.service';

@Injectable({
  providedIn: 'root'
})
export class UserRegService {

  private userRegUrl: string;
  currentUser: any;

  constructor(private http: HttpClient,
    private localStore: LocalService) {
    this.userRegUrl = 'http://localhost:8181/auth';
    this.currentUser = this.localStore.getData();
  }

  save(user: User) {
    this.http.post<User>(this.userRegUrl + '/register', user).subscribe();
  }

  login(user: User) {
    this.http.post<any>(this.userRegUrl + '/login', user).subscribe(Response => {
      this.localStore.saveData(Response);
    });
  }

  isLoggedIn() {
    if (localStorage.getItem('storedUser')) {
      return true;
    }
    return false;
  }
}