import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Product } from '../models/product';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private productsUrl: string;

  constructor(private http: HttpClient) { 
    this.productsUrl = 'http://localhost:8181/products';
  }

  public findAll(): Observable<Product[]>{
    return this.http.get<Product[]>(this.productsUrl + '/all');
  }

  public addProduct(product: Product){
    return this.http.post<any>(this.productsUrl + "/add", product)
  }

  public editProduct(product: Product, prdId: number){
    return this.http.patch<any>(this.productsUrl+'/update/'+prdId, product);
  }

  public findProduct(id: number){
    return this.http.get<any>(this.productsUrl+'/find/'+id);
  }

  public deleteProduct(id: number){
    return this.http.delete(this.productsUrl+'/delete/'+id);
  }
}
