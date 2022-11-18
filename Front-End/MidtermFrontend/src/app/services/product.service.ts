import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Product } from '../models/product';
import { Observable } from 'rxjs';
import { LocalService } from './local.service';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private productsUrl: string;
  currentUser: any;
  isAdded: boolean = false;
  isEdited: boolean = false;
  errMessage: string = '';
  id: any;
  oneProduct:any;

  constructor(
    private http: HttpClient,
    private localStorage: LocalService) { 
    this.productsUrl = 'http://localhost:8181/products';
    this.currentUser = localStorage.getData();
  }
  
  public findAll(): Observable<Product[]>{
    return this.http.get<Product[]>(this.productsUrl + '/all');
  }

  public addProduct(product: Product){
    let jwt = this.currentUser.token;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${jwt}`
      })
    }
    this.http.post<any>(this.productsUrl + "/add", product, httpOptions).subscribe((response) => {
      console.log(response);
      this.isAdded = true;
    },
      error => {
        this.isAdded = false;
        this.errMessage = error.error.message;
      });
  }

  public editProduct(prdId: number, product: Product){
    let jwt = this.currentUser.token;
    console.log(jwt);
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${jwt}`
      })
    }
    this.http.patch<any>(this.productsUrl+'/update/'+prdId, product, httpOptions).subscribe((response) => {
      console.log(response);
      this.isEdited = true;
    },
      error => {
        this.isEdited = false;
        this.errMessage = error.error.message;
      });
  }

  public findProduct(id: number){
    return this.http.get<any>(this.productsUrl+'/find/'+id);
  }

  public deleteProduct(id: number){
    let jwt = this.currentUser.token;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${jwt}`
      })
    }
    return this.http.delete(this.productsUrl+'/delete/'+id, httpOptions);
  }

  public select(prdId: number){
    this.id = prdId;
    this.findProduct(this.id).subscribe(data=>{
      this.oneProduct = data;
    })
  }
}
