import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/models/product';
import { ProductService } from 'src/app/services/product.service';
import { CartService } from 'src/app/services/cart.service';
import { FormControl, FormGroup, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-catalog',
  templateUrl: './catalog.component.html',
  styleUrls: ['./catalog.component.css']
})
export class CatalogComponent implements OnInit {

  products: Product[] = [];

  itemsToPrint: any;
  quantControl = new FormControl(1);
  quantForm = new FormGroup({
    quant: this.quantControl
  });
  constructor(private productsService: ProductService,
    private cartService: CartService) { }

  ngOnInit() {
    this.productsService.findAll().subscribe(data => {
      this.products = data;
    });
   
  }

  public addToCart(product: Product) {
    this.cartService.addToCart(product, this.quantControl.value);
    this.cartService.getCartInfo();
    setTimeout(() => {
      this.itemsToPrint = this.cartService.totalItems;
      console.log(this.itemsToPrint);
    }, 150);

  }

}
