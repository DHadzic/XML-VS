import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { Router } from '@angular/router';
import { ReviewService } from 'src/app/services/review.service';

@Component({
  selector: 'app-put-reviewer',
  templateUrl: './put-reviewer.component.html',
  styleUrls: ['./put-reviewer.component.scss']
})
export class PutReviewerComponent implements OnInit {

  public science_papers;
  public reviewers;
  public selected_paper;
  public selected_reviewer;

  constructor(private userService : UserService,private reviewService : ReviewService, router : Router) { 
    this.science_papers = [];
    this.reviewers = [];

    userService.getReviewingPublications().subscribe(
      data => {
        this.science_papers = JSON.parse(data as string);
        }
    )
    userService.getReviewers().subscribe(
      data => {
        this.reviewers = JSON.parse(data as string);
      }
    )
  }

  createRequest(){
    console.log(this.selected_paper);
    console.log(this.selected_reviewer);
    this.reviewService.createReviewRequest(this.selected_paper,this.selected_reviewer);
  }

  ngOnInit() {
  }

}
