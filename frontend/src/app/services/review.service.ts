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
      "paperId" : paper,
      "username" : reviewer
    }

    var request_json = JSON.stringify(request);

    return this.http.put(this.path + "addRequest", request_json, {headers,responseType: 'text'});
  }

  getMyRevRequests(){
    return this.http.get(this.path + "getRequests", { responseType: 'text'});
  }

  declineRequest(requestId){
    return this.http.put(this.path + "declineRequest/" + requestId, { responseType: 'text'});
  }

  addReview(review){
    var headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });

    var review_json = JSON.stringify(review);

    console.log(review_json);

    return this.http.put(this.path + "save", review_json, {headers,responseType: 'text'});
  }

  getAuthorReviews(){
    return this.http.get(this.path + "getAuthorReviews", { responseType: 'text'});
  }

  acceptReview(reviewId){
    return this.http.put(this.path + "approveReview/" + reviewId, { responseType: 'text'});
  }

}
