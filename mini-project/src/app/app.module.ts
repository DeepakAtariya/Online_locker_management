import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { HttpClientModule } from '@angular/common/http';
import { LoginComponent } from './login/login.component';
import { RouterModule, Routes } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { DashboardComponent } from './dashboard/dashboard.component';
import { MainComponent } from './main/main.component';
import { BankloginComponent } from './banklogin/banklogin.component';
import { BankdashboardComponent } from './bankdashboard/bankdashboard.component';
import { OpenAccountComponent } from './open-account/open-account.component';


const appRoutes : Routes = [
  {
    path : '', component : LoginComponent
  },
  {
    path : '\dashboard', component : DashboardComponent
  },
  {
    path : '\main', component : MainComponent
  },
  {
    path : 'bank', component : BankloginComponent
  },
  {
    path : 'bankdashboard', component : BankdashboardComponent
  },
  {
    path : 'account', component : OpenAccountComponent
  }
];

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    DashboardComponent,
    MainComponent,
    BankloginComponent,
    BankdashboardComponent,
    OpenAccountComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot(appRoutes)
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
