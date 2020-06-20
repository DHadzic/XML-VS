import { Component, OnInit } from '@angular/core';
import { Router  , ActivatedRoute } from '@angular/router';
import { SciencePapersService } from 'src/app/services/science-papers.service';

@Component({
  selector: 'app-science-paper',
  templateUrl: './science-paper.component.html',
  styleUrls: ['./science-paper.component.scss']
})
export class SciencePaperComponent implements OnInit {
  constructor(private sciencePapersService : SciencePapersService, private route: ActivatedRoute) { }

  private sciencePaper : any;
  ngOnInit() {
    this.sciencePapersService.getById(this.route.snapshot.params['id'])
    .subscribe(res => {this.sciencePaper = res; console.log(res)})
  }

}
