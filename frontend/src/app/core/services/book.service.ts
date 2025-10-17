import { Injectable } from '@angular/core';
import { Book, BookSaveDto } from '../models/book.interface';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Endpoints } from '../constants/endpoints';


@Injectable({
  providedIn: 'root'
})
export class BookService {
    constructor( private http: HttpClient) {}

    getBooks(): Observable<Book[]>{
    return this.http.get<Book[]>(Endpoints.book.getBooks());
    }

    addBook( bookSave: BookSaveDto ): Observable<Book>{
      return this.http.post<Book>(Endpoints.book.postBooks(), bookSave);
    }

    deleteBook( id: number ): Observable<Boolean>{
      return this.http.delete<Boolean>(Endpoints.book.deleteBook(id));
    }

    updateBook(id: number, bookUpdate: BookSaveDto): Observable<Book> {
      return this.http.put<Book>(Endpoints.book.putBook(id), bookUpdate);
    }
}
