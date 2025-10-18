import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Loan, LoanSaveDto } from '../models/loan.interface';
import { Endpoints } from '../constants/endpoints';

@Injectable({
  providedIn: 'root'
})
export class LoanService {
  constructor(private http: HttpClient) {}

  getLoans(): Observable<Loan[]> {
    return this.http.get<Loan[]>(Endpoints.loan.getLoans());
  }

  addLoan(loanSave: LoanSaveDto): Observable<Loan> {
    return this.http.post<Loan>(Endpoints.loan.postLoans(), loanSave);
  }

  updateLoan(id: number, loanUpdate: LoanSaveDto): Observable<Loan> {
    return this.http.put<Loan>(Endpoints.loan.putLoan(id), loanUpdate);
  }

  deleteLoan(id: number): Observable<Boolean> {
    return this.http.delete<Boolean>(Endpoints.loan.deleteLoan(id));
  }

}
