import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/user';
import { UserCrudService } from 'src/app/services/user-crud.service';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css']
})
export class EditUserComponent implements OnInit {
  user: User;
  oneUser: User = new User;
  id: number=0;
  isEdited: boolean | undefined;

  constructor(
    private usersService: UserCrudService){
    this.user = new User();
  }

  ngOnInit(): void {
    this.id = this.usersService.id;
    this.oneUser = this.usersService.oneUser;
    setTimeout(()=>{
      console.log(this.oneUser)
    },200)
    
  }

  onSubmit(value: any){
    this.user = value;
    this.usersService.editUser(this.id, this.user);
    setTimeout(()=>{
      this.isEdited = this.usersService.isEdited;
    }, 200);
  }
  
}
