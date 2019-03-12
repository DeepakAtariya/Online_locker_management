import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  username : any ;
  hideButton: string;
  messageVisibility: string = "none";
  constructor(private route:Router) { }

  ngOnInit() {
    this.username = localStorage.getItem("user");

    if(this.username==null){
      this.route.navigate(['']);
    }else{

    }
  }

  logout(){
    localStorage.clear();
    history.back();
  }

  request_locker(){
    console.log("requested");
    this.hideButton = "none";
    this.messageVisibility = "block";
  }

}
