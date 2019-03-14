import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { AppComponent } from '../app.component';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

  user : any = {
    "username" : "",
    "password" : ""
  }
  approved_date = "2019/03/14";
  expiry_date = "2020/03/14";
  balance = 150;
  serverError: string;

  constructor(private route:Router, private http:HttpClient) { }

  ngOnInit() {
    this.user.username = localStorage.getItem("user");
    this.user.password = localStorage.getItem("key");

    if(this.user.username==null){
      this.route.navigate(['']);
    }else{
      
    }

    //fetch bank balance and locker issued and expiry date
    AppComponent.onShowLoader(1);
    this.http.post("http://localhost:8080/api/miniproject/customer2bank/balance",this.user)
    .subscribe(response=>{
    AppComponent.onShowLoader(0);
      console.log(response);
      this.balance = response['balance'];
    },
    error=>{
      AppComponent.onShowLoader(0);
      console.log(error);
      this.serverError = "Server Unavailable";
    });

    AppComponent.onShowLoader(1);
    this.http.post("http://localhost:8080/api/miniproject/customer2bank/locker_issued_expiry_date",this.user)
    .subscribe(response=>{
    AppComponent.onShowLoader(0);
      console.log(response);
      this.approved_date = response['issued'];
      this.expiry_date = response['expiry'];

    },
    error=>{
      AppComponent.onShowLoader(0);
      console.log(error);
      this.serverError = "Server Unavailable";
    });

    /*
      compare and expiry date with current date if current date is exceeds the expiry then call api to deduct money from user account and refresh current page.
    */
  }

  logout(){
    localStorage.clear();
    history.go(-2);
  }

}
