import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddMachineComponent } from './components/add-machine/add-machine.component';
import { AddUserComponent } from './components/add-user/add-user.component';
import { EditUserComponent } from './components/edit-user/edit-user.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { MachineOperationErrorsComponent } from './components/machine-operation-errors/machine-operation-errors.component';
import { MachinesTableComponent } from './components/machines-table/machines-table.component';
import { UsersTableComponent } from './components/users-table/users-table.component';
import { EditUserGuard } from './guards/edit-user.guard';
import { NewMachineGuard } from './guards/new-machine.guard';
import { NewUserGuard } from './guards/new-user.guard';
import { SearchMachinesGuard } from './guards/search-machines.guard';

const routes: Routes = [
  {
    path: "",
    component: HomeComponent
  },
  {
    path: "login",
    component: LoginComponent
  },
  {
    path: "users",
    component: UsersTableComponent
  },
  {
    path: "new-user",
    component: AddUserComponent,
    canActivate: [NewUserGuard],
  },
  {
    path: "users/:id",
    component: EditUserComponent,
    canActivate: [EditUserGuard]
  },
  {
    path: "machines",
    component: MachinesTableComponent,
    canActivate: [SearchMachinesGuard],
  },
  {
    path: "new-machine",
    component: AddMachineComponent,
    canActivate: [NewMachineGuard],
  },
  {
    path: "operation-errors",
    component: MachineOperationErrorsComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
