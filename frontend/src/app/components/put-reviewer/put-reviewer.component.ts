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
        var recieved = JSON.parse(data as string);
        for(let paper of recieved){
          this.science_papers.push(paper.value.publicationId + "-" + paper.value.basicInformations.title.value)
        }
        }
    )
    userService.getReviewers().subscribe(
      data => {
        this.reviewers = JSON.parse(data as string);
      }
    )
  }

  assignReviewer(){
    console.log(this.selected_reviewer);
    var selected_paper_cut = this.selected_paper.split("-")[0];
    console.log(selected_paper_cut);
    this.reviewService.createReviewRequest(selected_paper_cut,this.selected_reviewer).subscribe(
      data=>{
        alert(data);
      }
    )
  }

  ngOnInit() {
  }

}
