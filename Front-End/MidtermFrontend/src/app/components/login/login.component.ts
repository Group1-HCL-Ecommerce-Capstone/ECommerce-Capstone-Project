import { Component, OnInit } from '@angular/core';
import { UserRegService } from 'src/app/services/userReg.service';
import { User } from 'src/app/models/user';
import { Router } from '@angular/router';
import { FormBuilder, FormControl, FormGroup, NgForm } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  user: User;
  constructor(
    private userRegService: UserRegService,
    private router: Router
  ) {
    this.user = new User();
  }

  ngOnInit(): void {

  }


  onSubmit(form: NgForm) {
    this.userRegService.login(this.user);
    form.resetForm();
  }

}
