import { Book } from "./book.interface";
import { Member } from "./member.interface";

export interface Loan {
  id: number;
  book: Book;
  member: Member;
  loanDate: string;
  dueDate: string;
  returnDate: string;
}

export interface LoanSaveDto {
  bookId: number;
  memberId: number;
  loanDate: string;
  dueDate: string;
  returnDate: string;
}
