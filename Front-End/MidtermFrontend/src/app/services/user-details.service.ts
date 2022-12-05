import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { OktaAuthStateService } from '@okta/okta-angular';
import { AuthState } from '@okta/okta-auth-js';
import { filter, map, Observable } from 'rxjs';
import { UserDetails } from '../models/user-details';
import { LocalService } from './local.service';

@Injectable({
  providedIn: 'root'
})
export class UserDetailsService {
  public email$: Observable<string>;
  email:string='';
  currentUser: any;
  isAdded: boolean = false;
  isEdited: boolean = false;
  errMessage: string = '';
  oneAddress: any;
  currentAddressId: number = 0;
  private addressUrl: string;
  constructor(private http: HttpClient,
    private localStore: LocalService,
    private _oktaAuthStateService: OktaAuthStateService) {
    this.addressUrl = 'http://localhost:8181/address';
    //this.currentUser = this.localStore.getData();
    this.email$ = this._oktaAuthStateService.authState$.pipe(
      filter((authState: AuthState) => !!authState&& !!authState.isAuthenticated),
      map((authState: AuthState) => authState.idToken?.claims.email ?? '')
    );
    this.email$.forEach((x)=>this.email=x);
  }

  public addAddress(userDetails: UserDetails) {
    this.isAdded = false;
    console.log(this.email);
    this.http.post<any>(this.addressUrl + '/add/' +  this.email,userDetails).subscribe((response) => {
      console.log(response);
      this.isAdded = true;
    },
      error => {
        this.isAdded = false;
        this.errMessage = error.error.message;
      });
  }

  public updateAddress(adrId: number, userDetails: UserDetails) {
    //this.http.get<any>(this.addressUrl + '/all/' + this.currentUser.userId).subscribe(response => this.currentAddressId = response[0].id);
    this.http.patch<any>(this.addressUrl + '/update/' + adrId, userDetails).subscribe((response) => {
      console.log(response);
      this.isEdited = true;
    },
      error => {
        this.isEdited = false;
        this.errMessage = error.error.message;
      });
  }

  public findCurrentUserAddresses(): Observable<UserDetails[]>{
    return this.http.get<UserDetails[]>(this.addressUrl+'/all/'+this.email);
  }

  public findAddress(adrId: number){
    return this.http.get<any>(this.addressUrl+'/find/'+adrId);
  }

  public deleteCurrentAddress(adrId: number){
    return this.http.delete(this.addressUrl+'/delete/'+adrId);
  }

  public select(adrId: number){
    this.currentAddressId = adrId;
    this.findAddress(this.currentAddressId).subscribe(data=>{
      this.oneAddress = data;
    })
  }
}
