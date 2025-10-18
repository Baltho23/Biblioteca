import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BookStats } from '../models/book.interface';
import { Endpoints } from '../constants/endpoints';

@Injectable({
  providedIn: 'root'
})
export class StatisticsService {

  constructor(private http: HttpClient) {}

  getBookLoanStats(): Observable<BookStats[]> {
    return this.http.get<BookStats[]>(Endpoints.statistics.getStatistics());
  }
}
