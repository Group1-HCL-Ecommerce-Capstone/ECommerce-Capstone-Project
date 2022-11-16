import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/models/product';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-product-crud',
  templateUrl: './product-crud.component.html',
  styleUrls: ['./product-crud.component.css']
})
export class ProductCRUDComponent implements OnInit {

  products: Product[] = [];
  
  constructor(
    private productsService: ProductService,
  ) { }

  ngOnInit() {
    this.productsService.findAll().subscribe(data => {
      this.products = data;
    });
  }

}
