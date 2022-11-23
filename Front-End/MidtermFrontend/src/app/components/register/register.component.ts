import { Component, OnInit } from '@angular/core';
import { UserRegService } from 'src/app/services/userReg.service';
import { User } from 'src/app/models/user';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';

import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  user: User;

  successMsg: boolean | undefined;
  isErr: boolean | undefined;
  errMessage: string = '';
  form!: FormGroup;
  formSubmitted = false;

  constructor(private userRegService: UserRegService,
    private router: Router,
    private formBuilder: FormBuilder) {
    this.user = new User();
    this.buildForm();

  }
  ngOnInit(): void {

    this.buildForm();
  }

  buildForm() {
    this.form = this.formBuilder.group({
      firstName: [null, [Validators.required]],
      lastName: [null, [Validators.required]],
      email: [null, [Validators.email, Validators.required]],
      password: [null, [Validators.required, Validators.minLength(8)]]
    });
  }

  onSubmit(form1: NgForm, event: any) {
    event.preventDefault();
    this.formSubmitted = true;

    if (this.form.valid) {
      console.log(this.form.value); // Process your form
    }
    this.userRegService.save(this.user);
    setTimeout(() => {
      this.successMsg = !this.userRegService.err;
      this.errMessage = this.userRegService.errMessage;
      this.isErr = this.userRegService.err;
    }, 200);

    form1.resetForm();
  }

}
