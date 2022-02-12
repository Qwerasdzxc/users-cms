import { Component, OnInit } from '@angular/core';
import { RestService } from 'src/app/services/rest/rest.service';

@Component({
  selector: 'app-add-machine',
  templateUrl: './add-machine.component.html',
  styleUrls: ['./add-machine.component.css']
})
export class AddMachineComponent implements OnInit {

  name: string;

  constructor(private restService: RestService) {
    this.name = "";
  }

  ngOnInit(): void {}

  submitData(): void {
    this.restService.createMachine(
      this.name
    ).subscribe(result => {
      console.log(result);
    });
  }

}
