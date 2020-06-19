import { Component, OnInit } from '@angular/core';
import { ReviewService } from 'src/app/services/review.service';
import { Route } from '@angular/compiler/src/core';
import { Router } from '@angular/router';
import { catchError } from 'rxjs/operators';
import { AuthService } from 'src/app/services/security/auth.service';
import { routerNgProbeToken } from '@angular/router/src/router_module';

@Component({
  selector: 'app-my-request',
  templateUrl: './my-request.component.html',
  styleUrls: ['./my-request.component.scss']
})
export class MyRequestComponent implements OnInit {
  public my_requests;
  public inputOn : boolean;
  public rates = ["1","2","3","4","5"];
  public review;
  public selectedPaperId;

  constructor(private reviewService : ReviewService,private authService : AuthService,private router : Router) { 
    this.my_requests = [];
    this.inputOn = false;
    this.review = {};
    this.review.rateReadability = "1";
    this.review.rateOriginality = "1";
    this.review.rateSubject = "1";

    reviewService.getMyRevRequests().subscribe(
      data => {
        var recieved = JSON.parse(data as string);
        for(let request of recieved){
          console.log(request);
          this.my_requests.push(request);
        }
        }
    )
  }

  ngOnInit() {
  }

  acceptRequest(paperId){
    this.selectedPaperId = paperId;
    this.inputOn = true;
  }

  declineRequest(requestId){
    this.reviewService.declineRequest(requestId).subscribe(
      data => {
        if(data === "Successful"){
          alert("All good");
        }
      },
      err =>{
        alert(err.error.text);
      }
    )
  }

  isInputOn(){
    return this.inputOn;
  }

  addReview(){
    this.review.reviewedBy = "reviewer";
    this.review.paperId = this.selectedPaperId;
    this.review.authors = [];
    this.review.authors.push(this.authService.getCurrentUser().username);

    this.reviewService.addReview(this.review).subscribe(
      data => {
        alert(data);
        this.router.navigateByUrl("main");
      },
      err =>{
        alert(err);
        this.router.navigateByUrl("main");
      }
    )
  }




}
