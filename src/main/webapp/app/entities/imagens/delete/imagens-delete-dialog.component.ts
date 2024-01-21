import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IImagens } from '../imagens.model';
import { ImagensService } from '../service/imagens.service';

@Component({
  standalone: true,
  templateUrl: './imagens-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ImagensDeleteDialogComponent {
  imagens?: IImagens;

  constructor(
    protected imagensService: ImagensService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.imagensService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
