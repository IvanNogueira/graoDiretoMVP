import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICategoriaProduto } from '../categoria-produto.model';
import { CategoriaProdutoService } from '../service/categoria-produto.service';

@Component({
  standalone: true,
  templateUrl: './categoria-produto-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CategoriaProdutoDeleteDialogComponent {
  categoriaProduto?: ICategoriaProduto;

  constructor(
    protected categoriaProdutoService: CategoriaProdutoService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.categoriaProdutoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
