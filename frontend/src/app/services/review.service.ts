import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  private readonly path : String = "http://localhost:8888/api/review/";

  constructor(private http: HttpClient) { }

  createReviewRequest(paper,reviewer){
    var headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });
    
    var request = {
      "paper" : paper,
      "reviewer" : reviewer
    }

    var request_json = JSON.stringify(request);

    return this.http.put(this.path + "addRequest", request_json, {headers,responseType: 'text'});
  }

}
