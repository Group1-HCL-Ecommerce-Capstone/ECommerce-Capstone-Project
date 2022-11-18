import { Component, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { Category } from 'src/app/models/category';
import { Product } from 'src/app/models/product';
import { ProductService } from 'src/app/services/product.service';
import { EditProductComponent } from '../edit-product/edit-product.component';

@Component({
  selector: 'app-product-crud',
  templateUrl: './product-crud.component.html',
  styleUrls: ['./product-crud.component.css']
})
export class ProductCRUDComponent implements OnInit {

  products: Product[] = [];
  oneProduct: Product = new Product;
  id: number = 0;
  //categories: Category[] = []
  displayedColumns: string[] = ["prdId", "name", "desc", "image", "price", "stock", "acts"]; //"categ",
  constructor(
    private productsService: ProductService,
    private router: Router) { }

  ngOnInit() {
    this.productsService.findAll().subscribe(data => {
      this.products = data;
    });
  }

  public addProduct() {

  }
  public editProduct(prdId: number) {
    this.productsService.select(prdId);
  
    setTimeout(()=>{
      this.id = this.productsService.id;
      this.oneProduct = this.productsService.oneProduct;
      console.log(this.id);
      console.log(this.oneProduct);
      this.router.navigate(['manage/products/edit']);
    },200)
  }

  public deleteProduct(id: number, name: string) {
    if(confirm(`Are you sure you want to delete Product ${id}: ${name}?`)){
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
  
}
