import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoanService } from '../../../core/services/loan.service';
import { Loan, LoanSaveDto } from '../../../core/models/loan.interface';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';

// PrimeNG
import { TableModule } from 'primeng/table';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ToastModule } from 'primeng/toast';
import { ConfirmationService, MessageService } from 'primeng/api';
import { dueAfterLoanDate, noFutureDate } from './loan-validators';

@Component({
  selector: 'app-loans',
  imports: [
    TableModule, CardModule, ButtonModule, DialogModule,
    ReactiveFormsModule, CommonModule, ConfirmDialogModule, ToastModule
  ],
  providers: [ConfirmationService, MessageService],
  templateUrl: './loans.html',
  styleUrl: './loans.scss'
})
export class Loans implements OnInit {

  showDialog = false;
  isEditMode = false;

  loans: Loan[] = [];
  selectedLoanId?: number;
  loanForm!: FormGroup;

  constructor(
    private confirmationService: ConfirmationService,
    private messageService: MessageService,
    private loanService: LoanService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.loadLoans();

    this.loanForm = this.fb.group({
      bookId: ['null', [Validators.required]],
      memberId: ['null', [Validators.required]],
      loanDate: ['', [Validators.required, noFutureDate]],
      dueDate: ['', [Validators.required, dueAfterLoanDate('loanDate')]],
      returnDate: ['', [noFutureDate]],
    });
  }

  loadLoans(): void {
    this.loanService.getLoans().subscribe({
      next: (loans) => (this.loans = loans),
    });
  }

  openAddDialog(): void {
    this.isEditMode = false;
    this.loanForm.reset();
    this.showDialog = true;
  }

  openEditDialog(loan: Loan): void {
    this.isEditMode = true;
    this.selectedLoanId = loan.id;
    this.loanForm.patchValue({
      bookId: loan.book?.id,
      memberId: loan.member?.id,
      loanDate: loan.loanDate,
      dueDate: loan.dueDate,
      returnDate: loan.returnDate,
    });
    this.showDialog = true;
  }

  saveLoan(): void {
    if (this.loanForm.invalid) {
      this.loanForm.markAllAsTouched();
      return;
    }

    const loanData: LoanSaveDto = this.loanForm.value;

    if (this.isEditMode && this.selectedLoanId) {
      this.loanService.updateLoan(this.selectedLoanId, loanData).subscribe({
        next: () => {
          this.messageService.add({ severity: 'success', summary: 'Actualizado', detail: 'Préstamo actualizado correctamente' });
          this.loadLoans();
          this.closeDialog();
        },
      });
    } else {
      this.loanService.addLoan(loanData).subscribe({
        next: () => {
          this.messageService.add({ severity: 'success', summary: 'Agregado', detail: 'Préstamo agregado correctamente' });
          this.loadLoans();
          this.closeDialog();
        },
      });
    }
  }

  confirmDelete(loan: Loan): void {
    this.confirmationService.confirm({
      message: '¿Estás seguro de eliminar este préstamo?',
      header: 'Eliminar préstamo',
      icon: 'pi pi-info-circle',
      acceptButtonProps: { label: 'Eliminar', severity: 'danger' },
      rejectButtonProps: { label: 'Cancelar', severity: 'secondary', outlined: true },
      accept: () => {
        this.loanService.deleteLoan(loan.id).subscribe({
          next: () => {
            this.messageService.add({ severity: 'info', summary: 'Eliminado', detail: 'Préstamo eliminado correctamente' });
            this.loadLoans();
          },
        });
      },
    });
  }

  closeDialog(): void {
    this.showDialog = false;
    this.loanForm.reset();
  }
}
