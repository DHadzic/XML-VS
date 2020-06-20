import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class SciencePapersService {
  private readonly path : string = "http://localhost:8888/api/publication";

  constructor(private http: HttpClient) { }

  getAllSciencePapers () {
    return this.http.get(this.path);
  }
  getById (id) {
    return this.http.get(this.path + "/" + id);
  }
}