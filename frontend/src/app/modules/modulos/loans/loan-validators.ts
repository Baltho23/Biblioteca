import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export const noFutureDate: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
  const value = control.value;
  if (!value) return null;
  const inputDate = new Date(value);
  const today = new Date();

  return inputDate > today ? { futureDate: true } : null;
};

export const dueAfterLoanDate = (loanDateField: string): ValidatorFn => {
  return (control: AbstractControl): ValidationErrors | null => {
    const parent = control.parent;
    if (!parent) return null;

    const loanDate = parent.get(loanDateField)?.value;
    const dueDate = control.value;

    if (!loanDate || !dueDate) return null;

    const loan = new Date(loanDate);
    const due = new Date(dueDate);

    return due < loan ? { dueBeforeLoan: true } : null;
  };
};
