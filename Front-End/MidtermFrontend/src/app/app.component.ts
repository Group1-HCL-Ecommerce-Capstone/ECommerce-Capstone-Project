import { Component, Inject, OnInit, } from '@angular/core';
import { LocalService } from './services/local.service';
import { NavigationEnd, Router, RouterEvent } from '@angular/router';
import { UserRegService } from './services/userReg.service';
import { CartService } from './services/cart.service';
import { CatalogComponent } from './components/catalog/catalog.component';

import { OktaAuthStateService, OKTA_AUTH } from '@okta/okta-angular';
import { AuthState, OktaAuth } from '@okta/okta-auth-js';
import { filter, map, Observable } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit{
  title = 'MidtermFrontend'
  public isAuthenticated$!: Observable<boolean>;

  
  currentUser: any;
  itemsToPrint: any;
  //isAdmin: boolean = this.userRegService.isAdmin;

  /*
  constructor(public localStore: LocalService,
    public userRegService: UserRegService,
    public cartService: CartService,
    public catComp: CatalogComponent,
    private router: Router) {
    this.currentUser = this.localStore.getData();
    this.itemsToPrint = this.catComp.itemsToPrint;
    router.events.subscribe((e)=>{
      this.isAdmin = this.localStore.admin;
      console.log(e instanceof NavigationEnd);
      console.log("app component admin check: "+this.isAdmin);
    });
  }
*/
constructor(
  private _router: Router,
  private _oktaStateService: OktaAuthStateService,
  @Inject(OKTA_AUTH) private _oktaAuth: OktaAuth
  ){}


  public ngOnInit(): void {
    this.isAuthenticated$ = this._oktaStateService.authState$.pipe(
      filter((s: AuthState)=>!!s),
      map((s: AuthState) => s.isAuthenticated ?? false)
    );
  }

  public async signIn() : Promise<void>{
    await this._oktaAuth.signInWithRedirect().then(
      _ => this._router.navigate(['/okta'])
    )
  }
  public async signOut() : Promise<void>{
    await this._oktaAuth.signOut();
  }

  /*
  logout() {
    this.localStore.clearData();
    location.reload();
  }
*/


  somethingElse() {
   console.log('Do Stuff');
  }


}