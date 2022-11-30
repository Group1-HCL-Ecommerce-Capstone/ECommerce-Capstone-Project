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
import { OktaAuthGuard, OktaCallbackComponent } from '@okta/okta-angular';
import { OktaConfigComponent } from './config/okta-config/okta-config.component';

const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full'},
  { path: 'home', component: HomeComponent},
  { path: 'catalog', component: CatalogComponent },
  { path: 'profile', component: ProfileComponent, canActivate: [OktaAuthGuard]},
  { path: 'profile/list', component: AddressCrudComponent, canActivate: [OktaAuthGuard] },
  { path: 'profile/add', component: AddAddressComponent, canActivate: [OktaAuthGuard] },
  { path: 'profile/edit', component: EditAddressComponent, canActivate: [OktaAuthGuard] },
  { path: 'login', component: LoginComponent},
  { path: 'login/callback', component: OktaCallbackComponent},
  { path: 'okta', component: OktaConfigComponent},
  { path: 'register', component: RegisterComponent},
  { path: 'manage/users', component: UserCRUDComponent, canActivate: [OktaAuthGuard]},
  { path: 'manage/users/add', component: AddUserComponent, canActivate: [OktaAuthGuard]},
  { path: 'manage/users/edit', component: EditUserComponent, canActivate: [OktaAuthGuard]},
  { path: 'manage/products', component: ProductCRUDComponent, canActivate: [OktaAuthGuard]},
  { path: 'manage/products/add', component: AddProductComponent, canActivate: [OktaAuthGuard]},
  { path: 'manage/products/edit', component: EditProductComponent, canActivate: [OktaAuthGuard]},
  { path: '**', component: PageNotFoundComponent},
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
