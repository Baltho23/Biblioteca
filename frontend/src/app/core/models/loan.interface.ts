export interface Loan {
  id: number;
  bookId: string;
  memberId: string;
  loanDate: Date;
  dueDate: Date;
  returnDate: Date;
}

export interface LoanSaveDto {
  bookId: string;
  memberId: string;
  loanDate: Date;
  dueDate: Date;
  returnDate: Date;
}
