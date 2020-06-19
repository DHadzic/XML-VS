import { Component, OnInit } from '@angular/core';
import { ReviewService } from 'src/app/services/review.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-manage-reviews',
  templateUrl: './manage-reviews.component.html',
  styleUrls: ['./manage-reviews.component.scss']
})
export class ManageReviewsComponent implements OnInit {
  public reviews;

  constructor(private reviewService : ReviewService, private router : Router) { 
      this.reviews = [];

      reviewService.getAuthorReviews().subscribe(
        data => {
          var recieved = JSON.parse(data as string);
          for(let review of recieved){
            this.reviews.push(review)
          }
          }
      )
  
  }

  ngOnInit() {
  }

  acceptReview(id){
    this.reviewService.acceptReview(id).subscribe(
      data => {
        alert(data);
        this.router.navigateByUrl("main");
      },
      err => {
        console.log(err);
        alert(err);
      }
    )
  }

}
