import { Component, OnInit } from '@angular/core';
import { UserRegService } from 'src/app/services/userReg.service';
import { User } from 'src/app/models/user';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  user: User;
  
  successMsg: boolean = false;
  constructor(private userRegService: UserRegService,
    private router: Router) {
    this.user = new User();
  }

  onSubmit(form: NgForm) {
    this.userRegService.save(this.user);
    this.successMsg = true;
    form.resetForm();

  }

  ngOnInit(): void {
  }

}
