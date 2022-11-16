import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from 'src/app/models/product';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-add-edit-product',
  templateUrl: './add-edit-product.component.html',
  styleUrls: ['./add-edit-product.component.css']
})
export class AddEditProductComponent implements OnInit {
  
  product: Product;

  constructor(
    private productsService: ProductService
  ){
    this.product = new Product();
  }

  ngOnInit(): void {
  }

  onSubmit(form: NgForm){
    this.productsService.addProduct(this.product);
    form.resetForm();
  }
  
  /*
  id: any;
  form: FormGroup ;
  errorMessage: string='';
  constructor(
    private productService: ProductService,
    private router: Router,
    private formBuilder: FormBuilder,
    private route: ActivatedRoute) { 
      this.form = this.formBuilder.group({
        name:['', Validators.required],
        description: ['', Validators.required],
        image: ['', Validators.required],
        price: ['', Validators.required],
        stock: ['', Validators.required],
        categories: ['']
      });
    }

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');
    console.log(this.id);
    if (this.id){
      this.productService.findProduct(this.id).subscribe(x=>this.form.patchValue(x));
    }
  }

  onSubmit(){
    var response = this.id ? this.productService.editProduct(this.form.value, this.id) : this.productService.addProduct(this.form.value);
    response.subscribe(
      data => {
        console.log("Product created / updated successfully");
        this.router.navigateByUrl('manage/products');
      },
      err =>{
        this.errorMessage = err.errorMessage;
      }
    );
  }
  */
}
