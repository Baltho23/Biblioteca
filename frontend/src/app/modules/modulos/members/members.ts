import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MemberService } from '../../../core/services/member.service';
import { Member, MemberSaveDto } from '../../../core/models/member.interface';
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
  selector: 'app-members',
  imports: [
    TableModule, CardModule, ButtonModule, DialogModule,
    ReactiveFormsModule, CommonModule, ConfirmDialogModule, ToastModule
  ],
  providers: [ConfirmationService, MessageService],
  templateUrl: './members.html',
  styleUrl: './members.scss'
})
export class Members implements OnInit {

  showDialog = false;
  isEditMode = false;

  members: Member[] = [];
  selectedMemberId?: number;
  memberForm!: FormGroup;

  constructor(
    private confirmationService: ConfirmationService,
    private messageService: MessageService,
    private memberService: MemberService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.loadMembers();

    this.memberForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(2)]],
      email: ['', [Validators.required, Validators.email]],
    });
  }

  loadMembers(): void {
    this.memberService.getMembers().subscribe({
      next: (members) => (this.members = members),
    });
  }

  openAddDialog(): void {
    this.isEditMode = false;
    this.memberForm.reset();
    this.showDialog = true;
  }

  openEditDialog(member: Member): void {
    this.isEditMode = true;
    this.selectedMemberId = member.id;
    this.memberForm.patchValue(member);
    this.showDialog = true;
  }

  saveMember(): void {
    if (this.memberForm.invalid) {
      this.memberForm.markAllAsTouched();
      return;
    }

    const memberData: MemberSaveDto = this.memberForm.value;

    if (this.isEditMode && this.selectedMemberId) {
      this.memberService.updateMember(this.selectedMemberId, memberData).subscribe({
        next: () => {
          this.messageService.add({ severity: 'success', summary: 'Actualizado', detail: 'Miembro actualizado correctamente' });
          this.loadMembers();
          this.closeDialog();
        },
        error: () => this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Actualizacion invalida' }),
      });
    } else {
      this.memberService.addMember(memberData).subscribe({
        next: () => {
          this.messageService.add({ severity: 'success', summary: 'Agregado', detail: 'Miembro agregado correctamente' });
          this.loadMembers();
          this.closeDialog();
        },
        error: () => this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Miembro invalido' }),
      });
    }
  }

  confirmDelete(member: Member): void {
    this.confirmationService.confirm({
      message: '¿Estás seguro de eliminar este miembro?',
      header: 'Eliminar miembro',
      icon: 'pi pi-info-circle',
      acceptButtonProps: { label: 'Eliminar', severity: 'danger' },
      rejectButtonProps: { label: 'Cancelar', severity: 'secondary', outlined: true },
      accept: () => {
        this.memberService.deleteMember(member.id).subscribe({
          next: () => {
            this.messageService.add({ severity: 'info', summary: 'Eliminado', detail: 'Miembro eliminado correctamente' });
            this.loadMembers();
          },
        });
      },
    });
  }

  closeDialog(): void {
    this.showDialog = false;
    this.memberForm.reset();
  }

}
