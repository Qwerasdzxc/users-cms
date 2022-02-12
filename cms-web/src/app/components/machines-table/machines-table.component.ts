import { Component, OnInit } from '@angular/core';
import { Machine } from 'src/app/model';
import { RestService } from 'src/app/services/rest/rest.service';

@Component({
  selector: 'app-machines-table',
  templateUrl: './machines-table.component.html',
  styleUrls: ['./machines-table.component.css']
})
export class MachinesTableComponent implements OnInit {

  data: Array<Machine>;
  msDelay: number;

  searchFrom: string;
  searchTo: string;
  searchName: string;
  searchStatus: string;

  constructor(private restService: RestService) { 
    this.data = [];
    this.msDelay = 0;

    this.searchFrom = '';
    this.searchTo = '';
    this.searchName = '';
    this.searchStatus = '';
  }

  ngOnInit(): void {
    this.getMachines();
  }

  searchMachines() {
    this.restService.getMachines(
      this.searchFrom.length > 0 ? this.searchFrom : null, 
      this.searchTo.length > 0 ? this.searchTo : null, 
      this.searchName.length > 0 ? this.searchName : null, 
      this.searchStatus.length > 0 ? this.searchStatus : null
    ).subscribe(response => {
      this.data = response;
    });
  }

  getMachines() {
    this.restService.getMachines(null, null, null,  null).subscribe(response => {
      this.data = response;
    });
  }

  startMachine(id: number) {
    let timestamp = new Date();
    timestamp = new Date(timestamp.getTime() + this.msDelay);

    this.restService.startMachine(id, timestamp.getTime()).subscribe(async response => {
      this.getMachines();

      var busyMachines = true;
      while (busyMachines) {
        busyMachines = false;

        await new Promise(resolve => setTimeout(resolve, 6000));
        this.getMachines();

        this.data.forEach( (machine) => {
          if (machine.status == 'BUSY') {
            busyMachines = true;
          }
      });
      }
    });
  }

  stopMachine(id: number) {
    let timestamp = new Date();
    timestamp = new Date(timestamp.getTime() + this.msDelay);

    this.restService.stopMachine(id, timestamp.getTime()).subscribe(async response => {
      this.getMachines();

      var busyMachines = true;
      while (busyMachines) {
        busyMachines = false;

        await new Promise(resolve => setTimeout(resolve, 6000));
        this.getMachines();

        this.data.forEach( (machine) => {
          if (machine.status == 'BUSY') {
            busyMachines = true;
          }
      });
      }
    });
  }

  restartMachine(id: number) {
    let timestamp = new Date();
    timestamp = new Date(timestamp.getTime() + this.msDelay);

    this.restService.restartMachine(id, timestamp.getTime()).subscribe(async response => {
      this.getMachines();

      var busyMachines = true;
      while (busyMachines) {
        busyMachines = false;

        await new Promise(resolve => setTimeout(resolve, 6000));
        this.getMachines();

        this.data.forEach( (machine) => {
          if (machine.status == 'BUSY') {
            busyMachines = true;
          }
      });
      }
    });
  }

  destroyMachine(id: number) {
    this.restService.destroyMachine(id).subscribe(response => {
      this.getMachines();
    });
  }
}
