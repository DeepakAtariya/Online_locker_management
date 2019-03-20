import { Component, OnInit, ViewChild } from '@angular/core';
import { AppComponent } from '../app.component';
import { HttpClient } from '@angular/common/http'
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-banklogin',
  templateUrl: './banklogin.component.html',
  styleUrls: ['./banklogin.component.css']
})
export class BankloginComponent implements OnInit {
  
  @ViewChild('login') loginData : NgForm;
  warning : string ;


  constructor(private http: HttpClient, private route:Router) { }


  ngOnInit() {
  }

  onLogin(){
    console.log("login hit");

    let loginData=this.loginData.value.loginFormData;

    console.log(loginData);

    AppComponent.onShowLoader(1);
    this.http.post("http://localhost:8080/api/miniproject/login/bankauth",loginData)
    .subscribe((data)=>{
      AppComponent.onShowLoader(0);
        // console.log(data);
        if(data['role']=="banker"){
          localStorage.setItem("user",loginData.username);
          localStorage.setItem("key",loginData.password);
          localStorage.setItem("banker",data['role']);
          this.route.navigate(['/bankdashboard']);
        }else{
          this.warning = "Invalid credentials"; 
          console.log("Invalid credentials");
        }
    },
    error=>{
      console.log(error['error']['message']);
    });


  }

}
