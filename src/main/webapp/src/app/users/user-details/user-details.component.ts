import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { User } from '../user/user';
import { UserService } from '../user/user.service';
import {Observable, throwError} from "rxjs";
import {catchError} from "rxjs/operators";

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css']
})
export class UserDetailsComponent implements OnInit {

  user$: Observable<User>;

  constructor(private route: ActivatedRoute, private router: Router,
              private userService: UserService) { }

  ngOnInit(): void {
    const id = this.route.snapshot.params['id'];
    this.user$ = this.userService.getUser(id);
  }

  list(): void{
    this.router.navigate(['users']);
  }

  onSubmit() {

  }
}
