import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/models/product';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-catalog',
  templateUrl: './catalog.component.html',
  styleUrls: ['./catalog.component.css']
})
export class CatalogComponent implements OnInit {

  products: Product[] = [];
  constructor(private productsService: ProductService) { }

  ngOnInit() {
    this.productsService.findAll().subscribe(data => {
      this.products = data;
    });
  }

}
