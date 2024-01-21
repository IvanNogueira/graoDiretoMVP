import { Component, Input } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ICategoriaProduto } from '../categoria-produto.model';

@Component({
  standalone: true,
  selector: 'jhi-categoria-produto-detail',
  templateUrl: './categoria-produto-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class CategoriaProdutoDetailComponent {
  @Input() categoriaProduto: ICategoriaProduto | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  previousState(): void {
    window.history.back();
  }
}
