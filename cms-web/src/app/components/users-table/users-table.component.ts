import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/model';
import { ConfigService } from 'src/app/services/config/config.service';
import { RestService } from 'src/app/services/rest/rest.service';

@Component({
  selector: 'app-users-table',
  templateUrl: './users-table.component.html',
  styleUrls: ['./users-table.component.css']
})
export class UsersTableComponent implements OnInit {

  data: Array<User>;
  canDelete: boolean;
  canUpdate: boolean;

  constructor(private configService: ConfigService, private restService: RestService) {
    this.data = [];
    this.canDelete = configService.getUser().permissions.canDeleteUsers;
    this.canUpdate = configService.getUser().permissions.canUpdateUsers;
  }

  ngOnInit(): void {
    this.getUsers();
  }

  getUsers() {
    this.restService.getUsers().subscribe(response => {
      this.data = response;
    });
  }

  onDelete(id: number) {
      this.restService.deleteUserById(id).subscribe(response => {
        this.getUsers();
      });
  }

}
