import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserDetails } from '../models/user-details';
import { LocalService } from './local.service';

@Injectable({
  providedIn: 'root'
})
export class UserDetailsService {

  currentUser: any;
  currentAddressId: number = 0;
  private addressUrl: string;
  constructor(private http: HttpClient,
    private localStore: LocalService) {
    this.addressUrl = 'http://localhost:8181/address';
    this.currentUser = this.localStore.getData();
  }

  public addAddress(userDetails: UserDetails) {
    console.log(userDetails);
    this.http.post<any>(this.addressUrl + '/add/' + this.currentUser.userId, userDetails).subscribe(
      response => this.currentAddressId = response.id,
      error => {

      }
    );
  }

  public updateAddress(userDetails: UserDetails) {
    //this.http.get<any>(this.addressUrl + '/all/' + this.currentUser.userId).subscribe(response => this.currentAddressId = response.id);
    setTimeout(() => {
      this.http.patch<any>(this.addressUrl + '/update/' + 17, userDetails).subscribe();
    }, 200)
  }
}