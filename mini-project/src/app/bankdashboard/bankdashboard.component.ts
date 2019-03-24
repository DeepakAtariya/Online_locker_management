import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AppComponent } from '../app.component';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-bankdashboard',
  templateUrl: './bankdashboard.component.html',
  styleUrls: ['./bankdashboard.component.css']
})
export class BankdashboardComponent implements OnInit {
  records: any;
  test: string;
  availableLocker: any;
  appointments: any;
  accounts : any;

  constructor(private route:Router, private http:HttpClient) { }

  user : any = {
    "username" : "",
    "password" : ""
  }
  
  ngOnInit() {
    
    this.test = "hey hey ngOnInit"; 
    this.user.username = localStorage.getItem("user");
    this.user.password = localStorage.getItem("key");
    
    if(this.user.username==null){
      this.route.navigate(['']);
    }else{
      
      //locker appointments
      AppComponent.onShowLoader(1);
      this.http.post("http://localhost:8080/api/miniproject/bank/appointments",this.user)
      .subscribe(response=>{
        AppComponent.onShowLoader(0);
        console.log(response);
        this.appointments = response['appointments'];
      },
      error=>{
        AppComponent.onShowLoader(0);
        console.log(error);
      });
      
      
      // locker application queue
      AppComponent.onShowLoader(1);
      this.http.post("http://localhost:8080/api/miniproject/bank/queue",this.user)
      .subscribe(response=>{
        AppComponent.onShowLoader(0);
        console.log(response);
        this.records = response['approvals'];
      },
      error=>{
        AppComponent.onShowLoader(0);
        console.log(error);
      });
  
  
    //Locker Availability 
    AppComponent.onShowLoader(1);
    this.http.post("http://localhost:8080/api/miniproject/bank/overalllocker",{
      "username" : this.user.username,
      "password" : this.user.password,
    })
    .subscribe(response=>{
      AppComponent.onShowLoader(0);
      console.log(response);
      this.availableLocker = response['locker_info'];      
    },
    error=>{
      AppComponent.onShowLoader(0);
      console.log(error);
    });
    
    //currently active accounts
    AppComponent.onShowLoader(1);
    this.http.get("http://localhost:8080/api/miniproject/bank/active_accounts")
    .subscribe(response=>{
      AppComponent.onShowLoader(0);
      console.log(response);
      this.accounts = response['accounts'];      
    },
    error=>{
      AppComponent.onShowLoader(0);
      console.log(error);
    });
  
  }
}

logout(){
  localStorage.clear();
  this.route.navigate(['bank']);
}


  approve(data:any){
    console.log(data);
    // this.route.navigateByUrl('/bankdashboard', {skipLocationChange: true}).then(()=>this.route.navigate(["/bankdashboard"])); 
    this.test="I am approved";

    AppComponent.onShowLoader(1);
    this.http.post("http://localhost:8080/api/miniproject/bank/approve_locker_application",{
      "username" : this.user.username,
      "password" : this.user.password,
      "customer_id" : data
    })
    .subscribe(response=>{
    AppComponent.onShowLoader(0);
      console.log(response);
      if(response['locker']=="NA"){
        alert("Lockers are not available");
      }else{
        this.records = response['approvals'];  
      }
      
      
    },
    error=>{
      AppComponent.onShowLoader(0);
      console.log(error);
    });


  }

  cancel(data:any){
    console.log(data);

    AppComponent.onShowLoader(1);
    this.http.post("http://localhost:8080/api/miniproject/bank/cancel_locker_application",{
      "username" : this.user.username,
      "password" : this.user.password,
      "customer_id" : data
    })
    .subscribe(response=>{
    AppComponent.onShowLoader(0);
      console.log(response);
      this.availableLocker = response['locker_info'];  
    },
    error=>{
      AppComponent.onShowLoader(0);
      console.log(error);
    });
  }

  refreshQueue(){
    AppComponent.onShowLoader(1);
    this.http.post("http://localhost:8080/api/miniproject/bank/queue",this.user)
    .subscribe(response=>{
    AppComponent.onShowLoader(0);
      console.log(response);
      this.records = response['approvals'];
    },
    error=>{
      AppComponent.onShowLoader(0);
      console.log(error);
    });
  }

  refreshLocker(){
    //Locker Availability 
    AppComponent.onShowLoader(1);
    this.http.post("http://localhost:8080/api/miniproject/bank/overalllocker",{
      "username" : this.user.username,
      "password" : this.user.password,
    })
    .subscribe(response=>{
    AppComponent.onShowLoader(0);
      console.log(response);
      this.availableLocker = response['locker_info'];  
      
    },
    error=>{
      AppComponent.onShowLoader(0);
      console.log(error);
    });
  }

  refreshAppointment(){
    console.log("appointment section is refreshed");
      //locker appointments
      AppComponent.onShowLoader(1);
      this.http.post("http://localhost:8080/api/miniproject/bank/appointments",this.user)
      .subscribe(response=>{
        AppComponent.onShowLoader(0);
        console.log(response);
        this.appointments = response['appointments'];
      },
      error=>{
        AppComponent.onShowLoader(0);
        console.log(error);
      });

  }

  approveAppointment(access_id : any){


    //approve locker appointments
    AppComponent.onShowLoader(1);
    this.http.post("http://localhost:8080/api/miniproject/bank/approveAppointment",{
      "username":this.user.username,
      "password":this.user.password,
      "access_id":access_id
    })
    .subscribe(response=>{
      AppComponent.onShowLoader(0);
      console.log(response);
      this.appointments = response['appointments'];
    },
    error=>{
      AppComponent.onShowLoader(0);
      console.log(error);
    });
  }

  rejectAppointment(access_id : any ){

    //reject locker appointments
    AppComponent.onShowLoader(1);
    this.http.post("http://localhost:8080/api/miniproject/bank/rejectAppointment",{
      "username":this.user.username,
      "password":this.user.password,
      "access_id":access_id
    })
    .subscribe(response=>{
      AppComponent.onShowLoader(0);
      console.log(response);
      this.appointments = response['appointments'];
    },
    error=>{
      AppComponent.onShowLoader(0);
      console.log(error);
    });
  }

  refreshAccounts(){

    //reject locker appointments
    AppComponent.onShowLoader(1);
    this.http.get("http://localhost:8080/api/miniproject/bank/active_accounts")
    .subscribe(response=>{
      AppComponent.onShowLoader(0);
      console.log(response);
      this.accounts = response['accounts'];
    },
    error=>{
      AppComponent.onShowLoader(0);
      console.log(error);
    });
  }
}
