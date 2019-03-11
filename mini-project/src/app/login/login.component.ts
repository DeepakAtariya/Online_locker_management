import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

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

  @ViewChild('login') loginData : NgForm;

  constructor(private http: HttpClient, private route:Router) { }

  ngOnInit() {
  }

  onLogin(){
    console.log("login hit");

    let loginData=this.loginData.value.loginFormData;

    console.log(loginData);

    this.http.post("http://localhost:8080/api/miniproject/login/auth",loginData)
    .subscribe((data)=>{
        console.log(data);
        if(data['auth']==true){
          localStorage.setItem("user",loginData.username);
          localStorage.setItem("key",loginData.password);
          this.route.navigate(['\dashboard']);
        }else{
          console.log("Invalid credentials");
        }
    },
    error=>{
      console.log(error['error']['message']);
    });


  }

}