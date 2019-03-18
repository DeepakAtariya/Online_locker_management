import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { AppComponent } from '../app.component';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  user : any = {
    "username" : "",
    "password" : ""
  }
  username : any;
  hideButton: string;
  messageVisibility: string = "none";
  serverError: string;
  constructor(private route:Router, private http:HttpClient) { }

  ngOnInit() {
   
    this.user.username = localStorage.getItem("user");
    this.user.password = localStorage.getItem("key");
    var role = localStorage.getItem("banker");

    if(this.user.username==null){
      this.route.navigate(['']);
    }else{
        if(role == 'banker'){
          this.route.navigate(['/bankdashboard']);
        }
    }


    AppComponent.onShowLoader(1);
    this.http.post("http://localhost:8080/api/miniproject/customer2bank/check_locker_request",this.user)
    .subscribe(response=>{
    AppComponent.onShowLoader(0);
      console.log(response['status']);
      if(response['status'] == 4){
        
        this.route.navigate(['\main']);
      }else if (response['status'] == 3){
        this.hideButton = "none";
        this.messageVisibility = "block";
      }
      
    },
    error=>{
      AppComponent.onShowLoader(0);
      console.log(error);
      this.serverError = "Server Unavailable";
    });
  }

  logout(){
    localStorage.clear();
    history.back();
  }

  request_locker(){
    console.log("requested");
    
    AppComponent.onShowLoader(1);
    this.http.post("http://localhost:8080/api/miniproject/customer2bank/locker_request",this.user)
    .subscribe(response=>{
    AppComponent.onShowLoader(0);
      console.log(response);
      this.hideButton = "none";
      this.messageVisibility = "block";
    },
    error=>{
      AppComponent.onShowLoader(0);
      console.log(error);
      this.serverError = "Server Unavailable";
    });
    



  }

}
