import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LocalService {

  constructor() {}

    public saveData(value: any){
      localStorage.setItem('storedUser', JSON.stringify(value));
    }

    public getData(){
      return JSON.parse(localStorage.getItem('storedUser') || '{}');
    }

    public clearData(){
      localStorage.clear();
    }

    isLoggedIn() {
      if (localStorage.getItem('storedUser')) {
        return true;
      }
      return false;
    }
}
