import { Component, OnInit, ViewChild } from '@angular/core';
import { AppComponent } from '../app.component';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-open-account',
  templateUrl: './open-account.component.html',
  styleUrls: ['./open-account.component.css']
})
export class OpenAccountComponent implements OnInit {

  @ViewChild('account_open_details') account_open_details : NgForm;
  warning: string;
  constructor(private http:HttpClient, private route:Router) { }

  ngOnInit() {
  }

  onSubmit(){
    // let account_open_details;
    let account_open_details=this.account_open_details.value.account_open_data;
    console.log(account_open_details);

    const name = account_open_details.name;
    const contact = account_open_details.contact;
    const address = account_open_details.address;
    const openingamount = account_open_details.openingamount;
    const accounttype = account_open_details.accounttype;
    const password = account_open_details.password;

    if(name=="" || contact=="" || address=="" || openingamount=="" || accounttype=="" || password==""){
      this.warning = "Do not leave blank any field";
    }else{
      AppComponent.onShowLoader(1);
      this.http.post("http://localhost:8080/api/miniproject/customer2bank/open_account",account_open_details)
      .subscribe((data)=>{
        AppComponent.onShowLoader(0);
          console.log(data);
          const account_number = data['account_number'];
          const info = "Your account number and login id is "+account_number+". You may login with same password you entered before";
          alert(info);
          this.route.navigate(['']);
      },
      error=>{
        AppComponent.onShowLoader(0);
        console.log(error['error']['message']);
      });
    }


    
  }
}
