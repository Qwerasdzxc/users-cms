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

  searchMachines: boolean;
  startMachines: boolean;
  stopMachines: boolean;
  restartMachines: boolean;
  createMachines: boolean;
  destroyMachines: boolean;

  constructor(private restService: RestService) {
    this.email = "";
    this.name = "";
    this.surname = "";
    this.password = "";

    this.readPermission = false;
    this.createPermission = false;
    this.updatePermission = false;
    this.deletePermission = false;
    this.searchMachines = false;
    this.startMachines = false;
    this.stopMachines = false;
    this.restartMachines = false;
    this.createMachines = false;
    this.destroyMachines = false;
  }

  ngOnInit(): void {

  }

  submitData(): void {
    this.restService.createUser(
      this.email, this.password, this.name, this.surname, this.readPermission, this.createPermission, this.updatePermission, this.deletePermission,
      this.searchMachines, this.startMachines, this.stopMachines, this.restartMachines, this.createMachines, this.destroyMachines
    ).subscribe(result => {
      console.log(result);
    });
  }
}
