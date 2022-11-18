import { Component, } from '@angular/core';
import { LocalService } from './services/local.service';
import { Router } from '@angular/router';
import { UserRegService } from './services/userReg.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent {
  title = 'MidtermFrontend'

  
  currentUser: any;
  isAdmin:boolean = this.userRegService.isAdmin;

  constructor(public localStore: LocalService,
    public userRegService: UserRegService,
    private router: Router) {
    this.currentUser = this.localStore.getData();

  }

  logout() {
    this.localStore.clearData();
    location.reload();
    
  }
  
        

}