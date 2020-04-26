import { Component} from '@angular/core';
import { UserService } from '../user/user.service';
import { User } from '../user/user';
import { Router } from '@angular/router';
import {catchError} from "rxjs/operators";
import {throwError} from "rxjs";

@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.css']
})
export class CreateUserComponent {

  user: User = new User();
  submitted = false;
  error: string;

  constructor(private userService: UserService, private router: Router) { }

  save() {
    this.userService.createUser(this.user)
      .pipe(catchError(err => {
        this.submitted = false;
        this.error = 'Error creating user';
        return throwError(err);
      }))
      .subscribe(_ => this.gotoList());
  }

  onSubmit() {
    this.submitted = true;
    this.error = null;
    this.save();
  }

  gotoList() {
    this.router.navigate(['/users']);
  }

}
