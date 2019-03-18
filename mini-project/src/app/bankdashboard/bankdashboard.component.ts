import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-bankdashboard',
  templateUrl: './bankdashboard.component.html',
  styleUrls: ['./bankdashboard.component.css']
})
export class BankdashboardComponent implements OnInit {

  constructor(private route:Router) { }

  user : any = {
    "username" : "",
    "password" : ""
  }

  ngOnInit() {
    this.user.username = localStorage.getItem("user");
    this.user.password = localStorage.getItem("key");

    if(this.user.username==null){
      this.route.navigate(['']);
    }else{
      
    }
  }

  logout(){
    localStorage.clear();
    this.route.navigate(['/bank']);
  }


}
