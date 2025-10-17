import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BookService } from '../../../core/services/book.service';
import { Book, BookSaveDto } from '../../../core/models/book.interface';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';

// PrimeNG
import { TableModule } from 'primeng/table';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ToastModule } from 'primeng/toast';
import { ConfirmationService, MessageService } from 'primeng/api';

@Component({
  selector: 'app-books',
  imports: [
    TableModule, CardModule, ButtonModule, DialogModule,
    ReactiveFormsModule, CommonModule, ConfirmDialogModule, ToastModule
  ],
  providers: [ConfirmationService, MessageService],
  templateUrl: './books.html',
  styleUrl: './books.scss'
})
export class Books implements OnInit {

  showDialog = false;
  isEditMode = false;

  books: Book[] = [];
  selectedBookId?: number;
  bookForm!: FormGroup;

  constructor(
    private confirmationService: ConfirmationService,
    private messageService: MessageService,
    private bookService: BookService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.loadBooks();

    this.bookForm = this.fb.group({
      title: ['', [Validators.required, Validators.minLength(2)]],
      author: ['', [Validators.required]],
      genre: ['', [Validators.required]],
      copiesAvailable: [null, [Validators.required, Validators.min(1)]],
    });
  }

  loadBooks(): void {
    this.bookService.getBooks().subscribe({
      next: (books) => (this.books = books),
    });
  }

  openAddDialog(): void {
    this.isEditMode = false;
    this.bookForm.reset();
    this.showDialog = true;
  }

  openEditDialog(book: Book): void {
    this.isEditMode = true;
    this.selectedBookId = book.id;
    this.bookForm.patchValue(book);
    this.showDialog = true;
  }

  saveBook(): void {
    if (this.bookForm.invalid) {
      this.bookForm.markAllAsTouched();
      return;
    }

    const bookData: BookSaveDto = this.bookForm.value;

    if (this.isEditMode && this.selectedBookId) {
      this.bookService.updateBook(this.selectedBookId, bookData).subscribe({
        next: () => {
          this.messageService.add({ severity: 'success', summary: 'Actualizado', detail: 'Libro actualizado correctamente' });
          this.loadBooks();
          this.closeDialog();
        },
      });
    } else {
      this.bookService.addBook(bookData).subscribe({
        next: () => {
          this.messageService.add({ severity: 'success', summary: 'Agregado', detail: 'Libro agregado correctamente' });
          this.loadBooks();
          this.closeDialog();
        },
      });
    }
  }

  confirmDelete(book: Book): void {
    this.confirmationService.confirm({
      message: '¿Estás seguro de eliminar este libro?',
      header: 'Eliminar libro',
      icon: 'pi pi-info-circle',
      acceptButtonProps: { label: 'Eliminar', severity: 'danger' },
      rejectButtonProps: { label: 'Cancelar', severity: 'secondary', outlined: true },
      accept: () => {
        this.bookService.deleteBook(book.id).subscribe({
          next: () => {
            this.messageService.add({ severity: 'info', summary: 'Eliminado', detail: 'Libro eliminado correctamente' });
            this.loadBooks();
          },
        });
      },
    });
  }

  closeDialog(): void {
    this.showDialog = false;
    this.bookForm.reset();
  }
}
