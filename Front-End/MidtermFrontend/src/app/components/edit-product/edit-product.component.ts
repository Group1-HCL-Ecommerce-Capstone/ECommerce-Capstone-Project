import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Product } from 'src/app/models/product';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-edit-product',
  templateUrl: './edit-product.component.html',
  styleUrls: ['./edit-product.component.css']
})
export class EditProductComponent implements OnInit {
  product: Product;
  oneProduct: Product = new Product;
  id: number=0;
  isEdited: boolean | undefined;

  constructor(
    private productsService: ProductService){
    this.product = new Product();
  }

  ngOnInit(): void {
    this.id = this.productsService.id;
    this.oneProduct = this.productsService.oneProduct;
    setTimeout(()=>{
      console.log(this.oneProduct)
    },200)
    
  }

  onSubmit(value: any){
    this.product = value;
    this.productsService.editProduct(this.id, this.product);
    setTimeout(()=>{
      this.isEdited = this.productsService.isEdited;
    }, 200);
  }
  
}
