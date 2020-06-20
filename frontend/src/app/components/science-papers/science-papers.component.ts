import { Component, OnInit } from '@angular/core';
import { SciencePapersService } from 'src/app/services/science-papers.service';
@Component({
  selector: 'app-science-papers',
  templateUrl: './science-papers.component.html',
  styleUrls: ['./science-papers.component.scss']
})
export class SciencePapersComponent implements OnInit {

  constructor(private sciencePapersService : SciencePapersService) { }

  private sciencePapers : any;
  ngOnInit() {
    this.sciencePapersService.getAllSciencePapers()
    .subscribe(res => {this.sciencePapers = res; console.log(res)})
  }

}
