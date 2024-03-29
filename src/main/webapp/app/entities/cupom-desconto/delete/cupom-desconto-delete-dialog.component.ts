import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICupomDesconto } from '../cupom-desconto.model';
import { CupomDescontoService } from '../service/cupom-desconto.service';

@Component({
  standalone: true,
  templateUrl: './cupom-desconto-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CupomDescontoDeleteDialogComponent {
  cupomDesconto?: ICupomDesconto;

  constructor(
    protected cupomDescontoService: CupomDescontoService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cupomDescontoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
