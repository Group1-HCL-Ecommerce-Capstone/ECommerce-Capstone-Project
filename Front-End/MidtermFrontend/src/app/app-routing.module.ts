import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CatalogComponent } from './components/catalog/catalog.component';
import { HomeComponent } from './components/home/home.component';
import { ProfileComponent } from './components/profile/profile.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { ProductCRUDComponent } from './components/product-crud/product-crud.component';
import { UserCRUDComponent } from './components/user-crud/user-crud.component';
import { AddProductComponent } from './components/add-product/add-product.component';
import { EditProductComponent } from './components/edit-product/edit-product.component';
import { AddUserComponent } from './components/add-user/add-user.component';
import { EditUserComponent } from './components/edit-user/edit-user.component';
import { AddAddressComponent } from './components/add-address/add-address.component';
import { EditAddressComponent } from './components/edit-address/edit-address.component';
import { AddressCrudComponent } from './components/address-crud/address-crud.component';

const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full'},
  { path: 'home', component: HomeComponent},
  { path: 'catalog', component: CatalogComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'profile/list', component: AddressCrudComponent },
  { path: 'profile/add', component: AddAddressComponent },
  { path: 'profile/edit', component: EditAddressComponent },
  { path: 'login', component: LoginComponent},
  { path: 'register', component: RegisterComponent},
  { path: 'manage/users', component: UserCRUDComponent},
  { path: 'manage/users/add', component: AddUserComponent},
  { path: 'manage/users/edit', component: EditUserComponent},
  { path: 'manage/products', component: ProductCRUDComponent},
  { path: 'manage/products/add', component: AddProductComponent},
  { path: 'manage/products/edit', component: EditProductComponent},
  { path: '**', component: PageNotFoundComponent},
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
