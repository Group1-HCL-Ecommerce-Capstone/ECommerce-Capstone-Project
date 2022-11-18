import { Component, } from '@angular/core';
import { LocalService } from './services/local.service';
import { Router } from '@angular/router';
import { UserRegService } from './services/userReg.service';
import { CartService } from './services/cart.service';
import { CatalogComponent } from './components/catalog/catalog.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent {
  title = 'MidtermFrontend'

  currentUser: any;
  itemsToPrint: any;
  isAdmin: boolean = this.userRegService.isAdmin;


  constructor(public localStore: LocalService,
    public userRegService: UserRegService,
    public cartService: CartService,
    public catComp: CatalogComponent,
    private router: Router) {
    this.currentUser = this.localStore.getData();
    this.itemsToPrint = this.catComp.itemsToPrint;
  }

  logout() {
    this.localStore.clearData();
    location.reload();
    
  }
  somethingElse() {
   console.log('Do Stuff');
  }



}

