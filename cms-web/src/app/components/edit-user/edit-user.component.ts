import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { RestService } from 'src/app/services/rest/rest.service';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css']
})
export class EditUserComponent implements OnInit {

  email: string;
  name: string;
  surname: string;

  readPermission: boolean;
  createPermission: boolean;
  updatePermission: boolean;
  deletePermission: boolean;


  constructor(private restService: RestService, private _Activatedroute:ActivatedRoute) {
    this.email = "";
    this.name = "";
    this.surname = "";
    this.readPermission = false;
    this.createPermission = false;
    this.updatePermission = false;
    this.deletePermission = false;

    let id = this._Activatedroute.snapshot.paramMap.get("id");
    this.loadData(id!);
  }

  ngOnInit(): void {}

  loadData(id: string): void {
    this.restService.getUserById(
      id
    ).subscribe(result => {
      this.email = result.username;
      this.name = result.name;
      this.surname = result.surname;
      this.readPermission = result.permissions.canReadUsers;
      this.createPermission = result.permissions.canCreateUsers;
      this.updatePermission = result.permissions.canUpdateUsers;
      this.deletePermission = result.permissions.canDeleteUsers;
    })
  }

  submitData(): void {
    this.restService.editUser(
      this.email, this.name, this.surname, this.readPermission, this.createPermission, this.updatePermission, this.deletePermission
    ).subscribe(result => {
      console.log(result);
    });
  }
}
