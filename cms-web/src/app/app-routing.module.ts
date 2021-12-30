import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddUserComponent } from './components/add-user/add-user.component';
import { EditUserComponent } from './components/edit-user/edit-user.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { UsersTableComponent } from './components/users-table/users-table.component';
import { EditUserGuard } from './guards/edit-user.guard';
import { NewUserGuard } from './guards/new-user.guard';

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
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
