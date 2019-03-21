import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { AppComponent } from '../app.component';
import { NgForm } from '@angular/forms';
import { DatePipe } from '@angular/common';

declare var $: any;

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

  request_data = {
    "Date" : "",
    "Time" : "",
    "username" : "",
    "password" : ""
  }

  approved_date = "2019/03/14";
  expiry_date = "2020/03/14";
  balance = 150;
  serverError: string;
  warning: string;
  formHide: string;
  lockeraccesspending_message: string = "none";
  access_id : any;
  records : any;
  hide_blank : string;
  locker_id: any;
  free : any;

  constructor(private route:Router, private http:HttpClient) { }
  @ViewChild('request') requestdata : NgForm;

  ngOnInit() {

    this.user.username = localStorage.getItem("user");
    this.user.password = localStorage.getItem("key");

    if(this.user.username==null){
      this.route.navigate(['']);
    }else{
      
    }

    // AppComponent.onShowLoader(1);
    // this.http.post("http://localhost:8080/api/miniproject/customer2bank/check_locker_approval",this.user)
    // .subscribe(response=>{
    // AppComponent.onShowLoader(0);
    //   if(response['status']==5){
    //     alert("your locker account is cancelled, please contact your bank");
    //     this.route.navigate(['']);
    //   }
    // },
    // error=>{
    //   AppComponent.onShowLoader(0);
    //   console.log(error);
    //   this.serverError = "Server Unavailable";
    // });


    AppComponent.onShowLoader(1);
    this.http.post("http://localhost:8080/api/miniproject/customer2bank/check_locker_request",this.user)
    .subscribe(response=>{
    AppComponent.onShowLoader(0);
      console.log(response['status']);
      if(response['status'] == 4){
          this.locker_id = response['locker_id'];
          this.balance = response['balance'];
          this.approved_date = response['issued_on'];
          this.expiry_date = response['expired_on'];
        // this.route.navigate(['\main']);
      }else if (response['status'] == 3){
        history.back();        
      }
      
    },
    error=>{
      AppComponent.onShowLoader(0);
      console.log(error);
      this.serverError = "Server Unavailable";
    });
    
    
    //check the locker status and show old records of locker access request(if any) 
    AppComponent.onShowLoader(1);
      this.http.post("http://localhost:8080/api/miniproject/customer2bank/approvedlockerrequests",this.user)
      .subscribe(response=>{
        
        console.log(response);

        if(response['approvals']!=""){
          this.records = response['approvals'];
          this.hide_blank = "none";
        }else{
          this.hide_blank = "block";
        }
        AppComponent.onShowLoader(0);
      },
      error=>{
        AppComponent.onShowLoader(0);
        
      });

    //check the locker status and show the pending message (if any request is pending) and hide locker acceess request form
    AppComponent.onShowLoader(1);
      this.http.post("http://localhost:8080/api/miniproject/customer2bank/checkpendinglockeraccessrequest",this.user)
      .subscribe(
      response=>{
        AppComponent.onShowLoader(0);
        console.log(response);

        //hide the form and show message of locker access apointment if LockerAccessId is not a 0
        if(response['LockerAccessId']!=0){
          this.access_id = response['LockerAccessId'];
          this.formHide = "none";
          this.lockeraccesspending_message ="block";
        }else{
          if(response['is_rejected']==1){
            alert("Your last locker access request is rejected");
            this.formHide = "block";
            this.lockeraccesspending_message ="none";
          }else{
            this.formHide = "block";
            this.lockeraccesspending_message ="none";
          }
          
        }
      },
      error=>{
        AppComponent.onShowLoader(0);
      });


    //fetch bank balance and locker issued and expiry date
    AppComponent.onShowLoader(1);
    this.http.post("http://localhost:8080/api/miniproject/customer2bank/balance",this.user)
    .subscribe(response=>{
    AppComponent.onShowLoader(0);
      // console.log(response);
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
      // console.log(response);
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
  } //end ngInit



  logout(){
    localStorage.clear();
    // history.go(-2);
    this.route.navigate(['']);
  }


  // locker appointment
  accessrequest(){
    console.log(this.requestdata.value.requestData);
    this.request_data.Date = this.requestdata.value.requestData.date;
    this.request_data.Time = this.requestdata.value.requestData.time;
    this.request_data.username = this.user.username;
    this.request_data.password = this.user.password;

    this.warning = "";
    var datePipe = new DatePipe("en-US");
    var currentDate = datePipe.transform(new Date(),'dd/MM/yyyy');

    var givenDate = datePipe.transform(this.request_data.Date, "dd/MM/yyyy");
    // console.log(val2>val); 

    if(this.request_data.Date == "" || this.request_data.Time == ""){
      this.warning = "Schedule is invalid";
    }else if (currentDate > givenDate){
      this.warning = "Schedule is underflowed";
    }else{
      this.warning = "";

      AppComponent.onShowLoader(1);
      this.http.post("http://localhost:8080/api/miniproject/customer2bank/lockeraccessrequest",this.request_data)
      .subscribe(
      response=>{
        AppComponent.onShowLoader(0);
        console.log(response);

        //hide the form and show message of locker access apointment
        this.formHide = "none";
        this.lockeraccesspending_message ="block";

        
      },
      error=>{
        AppComponent.onShowLoader(0);
        
      });
    }

    history.go(0);

  }
}
