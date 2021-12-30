import { Component, OnInit } from '@angular/core';
import { RestService } from 'src/app/services/rest/rest.service';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css']
})
export class AddUserComponent implements OnInit {

  email: string;
  name: string;
  surname: string;
  password: string;

  readPermission: boolean;
  createPermission: boolean;
  updatePermission: boolean;
  deletePermission: boolean;


  constructor(private restService: RestService) {
    this.email = "";
    this.name = "";
    this.surname = "";
    this.password = "";
    this.readPermission = false;
    this.createPermission = false;
    this.updatePermission = false;
    this.deletePermission = false;
  }

  ngOnInit(): void {

  }

  submitData(): void {
    this.restService.createUser(
      this.email, this.password, this.name, this.surname, this.readPermission, this.createPermission, this.updatePermission, this.deletePermission
    ).subscribe(result => {
      console.log(result);
    });
  }
}
