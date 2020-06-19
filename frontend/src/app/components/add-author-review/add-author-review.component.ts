import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { ReviewService } from 'src/app/services/review.service';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/security/auth.service';

@Component({
  selector: 'app-add-author-review',
  templateUrl: './add-author-review.component.html',
  styleUrls: ['./add-author-review.component.scss']
})
export class AddAuthorReviewComponent implements OnInit {
  public science_papers;
  public selected_paper;
  public rates = ["1","2","3","4","5"];
  public review;

  constructor(private authService : AuthService,private userService : UserService,private reviewService : ReviewService,private router : Router) { 
    this.science_papers = [];
    this.review = {};
    this.review.rateReadability = "1";
    this.review.rateOriginality = "1";
    this.review.rateSubject = "1";

    userService.getReviewingPublications().subscribe(
      data => {
        var recieved = JSON.parse(data as string);
        for(let paper of recieved){
          this.science_papers.push(paper.value.publicationId + "-" + paper.value.basicInformations.title.value)
        }
        }
    )
  }

  addReview(){
    this.review.reviewedBy = "author";
    this.review.paperId = this.selected_paper.split("-")[0];
    this.review.authors = [];
    this.review.authors.push(this.authService.getCurrentUser().username);

    this.reviewService.addReview(this.review).subscribe(
      data => {
        alert(data);
        this.router.navigateByUrl("main");
      },
      err =>{
        alert(err);
      }
    )
  }

  ngOnInit() {
  }

}
