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
  isAdmin: boolean = this.userRegService.isAdmin;

  paymentHandler: any = null;


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
  @Inject(OKTA_AUTH) private _oktaAuth: OktaAuth,
  public localStore: LocalService,
    public userRegService: UserRegService,
    public cartService: CartService,
    public catComp: CatalogComponent,
    private router: Router
  ){
    this.currentUser = this.localStore.getData();
    this.itemsToPrint = this.catComp.itemsToPrint;
    router.events.subscribe((e)=>{
      this.isAdmin = this.localStore.admin;
      console.log(e instanceof NavigationEnd);
      console.log("app component admin check: "+this.isAdmin);
    });

  }


  public ngOnInit(): void {
    this.isAuthenticated$ = this._oktaStateService.authState$.pipe(
      filter((s: AuthState)=>!!s),
      map((s: AuthState) => s.isAuthenticated ?? false)),
      this.invokeStripe();
  }

  public async signIn() : Promise<void>{
    await this._oktaAuth.signInWithRedirect().then(
      _ => this._router.navigate(['/okta'])
    )
  }
  public async signOut() : Promise<void>{
    await this._oktaAuth.signOut();
  }

  
  logout() {
    this.localStore.clearData();
    location.reload();
  }



  somethingElse() {
   console.log('Do Stuff');
  }
  
  makePayment(amount: any) {
    const paymentHandler = (<any>window).StripeCheckout.configure({
      key: 'pk_test_51M9UYGAywS1S1N1AIpCijfjSSVmJsOkg17aXqpBJKwIY51I4lJfrkUkLA3KuIeMUjWv0Za8usbNQCq66rpMO3rES00RrhgPzTg',
      locale: 'auto',
      token: function (stripeToken: any) {
        console.log(stripeToken);
        alert('Stripe token generated!');
      },
    });
    paymentHandler.open({
      name: 'Positronx',
      description: '3 widgets',
      amount: amount * 100,
    });
  }
  invokeStripe() {
    if (!window.document.getElementById('stripe-script')) {
      const script = window.document.createElement('script');
      script.id = 'stripe-script';
      script.type = 'text/javascript';
      script.src = 'https://checkout.stripe.com/checkout.js';
      script.onload = () => {
        this.paymentHandler = (<any>window).StripeCheckout.configure({
          key: 'pk_test_51M9UYGAywS1S1N1AIpCijfjSSVmJsOkg17aXqpBJKwIY51I4lJfrkUkLA3KuIeMUjWv0Za8usbNQCq66rpMO3rES00RrhgPzTg',
          locale: 'auto',
          token: function (stripeToken: any) {
            console.log(stripeToken);
            alert('Payment has been successfull!');
          },
        });
      };
      window.document.body.appendChild(script);
    }
  }


}