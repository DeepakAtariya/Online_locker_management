import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
declare var $: any;
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})



export class AppComponent {
  title = 'mini-project';

  kajal_data : any;

  constructor(private http: HttpClient) { }

  ngOnInit(){
    this.http.get('http://localhost:8080/api/miniproject/AuthorService/test')
    .subscribe((data)=>{
      console.log(data);

      this.kajal_data = data;
    });
  
  }

  public static onShowLoader(loader){
    if(loader == 1){
      $('#divLoading').css("display", "block");
    }else{
      $('#divLoading').css("display", "none");
    }
  }
}
