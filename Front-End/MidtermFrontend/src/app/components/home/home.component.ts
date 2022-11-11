import { Component, OnInit, Input} from '@angular/core';
import { LocalService } from 'src/app/services/local.service';
import { Injectable } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
@Injectable({
  providedIn: 'root'
})
export class HomeComponent implements OnInit {

  currentUser:any;
  
  constructor(private localStore: LocalService) {
    this.currentUser =this.localStore.getData();
   }

  ngOnInit(): void {
  }

}
