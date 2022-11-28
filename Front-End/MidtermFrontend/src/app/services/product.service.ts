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
  
  public addHeaders(){
    console.log(this.currentUser);
    let jwt = this.currentUser.token;
    console.log(jwt);
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${jwt}`
      })
    }
    return httpOptions;
  }

  public findAll(): Observable<any[]>{
    return this.http.get<any[]>(this.productsUrl + '/all');
  }

  public addProduct(product: Product){
    this.http.post<any>(this.productsUrl + "/add", product, this.addHeaders()).subscribe((response) => {
      console.log(response);
      this.isAdded = true;
    },
      error => {
        this.isAdded = false;
        this.errMessage = error.error.message;
      });
  }

  public editProduct(prdId: number, product: Product){
    this.http.patch<any>(this.productsUrl+'/update/'+prdId, product, this.addHeaders()).subscribe((response) => {
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
    return this.http.delete(this.productsUrl+'/delete/'+id, this.addHeaders());
  }

  public select(prdId: number){
    this.id = prdId;
    this.findProduct(this.id).subscribe(data=>{
      this.oneProduct = data;
    })
  }
}
