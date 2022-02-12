import { Component, OnInit } from '@angular/core';
import { MachineOperationError } from 'src/app/model';
import { RestService } from 'src/app/services/rest/rest.service';

@Component({
  selector: 'app-machine-operation-errors',
  templateUrl: './machine-operation-errors.component.html',
  styleUrls: ['./machine-operation-errors.component.css']
})
export class MachineOperationErrorsComponent implements OnInit {

  errors: Array<MachineOperationError>;

  constructor(private restService: RestService) { 
    this.errors = [];
  }

  ngOnInit(): void {
    this.getOperationErrors();
  }

  getOperationErrors() {
    this.restService.getOperationErrors().subscribe(response => {
      this.errors = response;
    });
  }

}
