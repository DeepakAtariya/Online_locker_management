import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { AppComponent } from '../app.component';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  login_d = {
    "username":'',
    "password":''
  };

  user : any = {
    "username" : "",
    "password" : ""
  }

  @ViewChild('login') loginData : NgForm;

  constructor(private http: HttpClient, private route:Router) { }

  ngOnInit() {

    this.user.username = localStorage.getItem("user");
    this.user.password = localStorage.getItem("key");
    var role = localStorage.getItem("banker");

    if(this.user.username==null){
      this.route.navigate(['']);
    }else{
        if(role == 'banker'){
          this.route.navigate(['/bankdashboard']);
        }else{
          console.log("not a banker but already logged in ");
          this.route.navigate(['dashboard']);
        }
    }

  }

  onLogin(){

    

    console.log("login hit");

    let loginData=this.loginData.value.loginFormData;

    console.log(loginData);

    AppComponent.onShowLoader(1);
    this.http.post("http://localhost:8080/api/miniproject/login/auth",loginData)
    .subscribe((data)=>{
      AppComponent.onShowLoader(0);
        console.log(data);
        if(data['auth']==true){
          localStorage.setItem("user",loginData.username);
          localStorage.setItem("key",loginData.password);
          this.route.navigate(['\dashboard']);
        }else{
          alert("Invalid credentials");
          console.log("Invalid credentials");
        }
    },
    error=>{
      console.log(error['error']['message']);
    });


  }

  createAccount(){
    this.route.navigate(['account']);
  }

}
