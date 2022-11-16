import { Component, OnInit } from '@angular/core';
import { Category } from 'src/app/models/category';
import { Product } from 'src/app/models/product';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-product-crud',
  templateUrl: './product-crud.component.html',
  styleUrls: ['./product-crud.component.css']
})
export class ProductCRUDComponent implements OnInit {

  products: Product[] = [];
  //categories: Category[] = []
  displayedColumns: string[] = ["prdId", "name", "desc", "image", "price", "stock", "acts"]; //"categ",
  constructor(private productsService: ProductService) { }

  ngOnInit() {
    this.productsService.findAll().subscribe(data => {
      this.products = data;
    });
  }

  public addProduct() {

  }
  public editProduct(product: Product) {

  }

  public deleteProduct(id: number) {
    this.productsService.deleteProduct(id)
      .subscribe(data => {
        this.products = this.products.filter(item => item.id !== id);
        console.log('Product deleted successfully!');
      }
        , error => {
          console.log(error.error.message);
        }
      );
  }
  
}
