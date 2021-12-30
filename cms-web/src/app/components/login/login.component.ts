import { Component, OnInit } from '@angular/core';
import { ConfigService } from 'src/app/services/config/config.service';
import { RestService } from 'src/app/services/rest/rest.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username: string;
  password: string;

  constructor(private configService: ConfigService, private restService: RestService) {
    this.username = '';
    this.password = '';
  }
  
  ngOnInit(): void {}

  login() {
    this.restService.login(
      this.username, this.password
    ).subscribe(response => {
      this.configService.setToken(response.jwt);
      this.restService.getLoggedInUser().subscribe(response => {
        this.configService.setUser(response);
        console.log(this.configService.getUser());
      })
    });
  }
}
