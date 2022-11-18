import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Product } from '../models/product';
import { LocalService } from './local.service';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  currentUser: any;
  totalItems: any;
  responseString: string ='';
  private cartUrl: string;

  constructor(private http: HttpClient,
    private localStore: LocalService) {
    this.cartUrl = 'http://localhost:8181/cart';
    this.currentUser = this.localStore.getData();
  }

  getCartInfo(): any{
    this.http.get<any>(this.cartUrl + '/list/' + this.currentUser.userId).subscribe((response) => {this.totalItems = response.totalQuantity});
    return this.totalItems;
  }
  public addToCart(product: Product, quant: any) {
    this.responseString = '{"productId": '+ product.id +',"quantity": '+quant+'}';
    this.http.post<any>(this.cartUrl + '/add/' + this.currentUser.userId, JSON.parse(this.responseString)).subscribe();
  }

}
