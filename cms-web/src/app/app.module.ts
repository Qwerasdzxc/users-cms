import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';

import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './components/app/app.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { UsersTableComponent } from './components/users-table/users-table.component';
import { AddUserComponent } from './components/add-user/add-user.component';
import { EditUserComponent } from './components/edit-user/edit-user.component';
import { AddMachineComponent } from './components/add-machine/add-machine.component';
import { MachinesTableComponent } from './components/machines-table/machines-table.component';
import { MachineOperationErrorsComponent } from './components/machine-operation-errors/machine-operation-errors.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    UsersTableComponent,
    AddUserComponent,
    EditUserComponent,
    AddMachineComponent,
    MachinesTableComponent,
    MachineOperationErrorsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
