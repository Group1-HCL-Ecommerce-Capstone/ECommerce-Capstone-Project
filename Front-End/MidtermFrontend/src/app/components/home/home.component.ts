import { Component, OnInit, Input, Inject } from '@angular/core';
import { LocalService } from 'src/app/services/local.service';
import { Injectable } from '@angular/core';
import { filter, map, Observable } from 'rxjs';
import { OktaAuthStateService, OKTA_AUTH } from '@okta/okta-angular';
import { OktaAuth, AuthState } from '@okta/okta-auth-js';
import { AppComponent } from 'src/app/app.component';
//import { OktaAuthService } from '@okta/okta-angular';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
@Injectable({
  providedIn: 'root'
})
export class HomeComponent implements OnInit {

  user: any;
  public name$!: Observable<string>;
  isLoggedIn!: boolean;

  constructor(
    private _oktaAuthStateService: OktaAuthStateService,
    @Inject(OKTA_AUTH) private _auth: OktaAuth,
    private appComp: AppComponent) {
    appComp.isAuthenticated$.forEach((x) => this.isLoggedIn = x)
  }

  async ngOnInit() {
    console.log("logged in: " + this.isLoggedIn);
    this.name$ = this._oktaAuthStateService.authState$.pipe(
      filter((authState: AuthState) => !!authState && !!authState.isAuthenticated),
      map((authState: AuthState) => authState.idToken?.claims.name ?? '')
    );
    const testG = this._oktaAuthStateService.authState$.pipe(
      filter((authState: AuthState) => !!authState && !!authState.isAuthenticated),
      map((authState: AuthState) => authState.idToken?.claims.groups ?? '')
    );

    testG.forEach((x) => console.log(x));
    //console.log("BREAK");
    //const userClaims = await this._auth.getUser();
    //console.log(userClaims.groups);
    //console.log(userClaims.groups.toString().search("User"));
    //console.log(userClaims.groups.toString().search("Admin"));
  }
  /*
  currentUser:any;
  
  constructor(private localStore: LocalService) {
    this.currentUser =this.localStore.getData();
   }

  ngOnInit(): void {
  }
*/
}
