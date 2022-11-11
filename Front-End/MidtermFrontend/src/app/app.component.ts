import { Component,  } from '@angular/core';
import { LocalService } from './services/local.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent {
  title = 'MidtermFrontend'

  currentUser:any;
  
  constructor(private localStore: LocalService) {
    this.currentUser =this.localStore.getData();
   }

  logout(){
    this.localStore.clearData();
    console.log("logout");
    location.reload();
  }
  
}
