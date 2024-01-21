import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEstabelecimento } from 'app/entities/estabelecimento/estabelecimento.model';
import { EstabelecimentoService } from 'app/entities/estabelecimento/service/estabelecimento.service';
import { ICardapio } from '../cardapio.model';
import { CardapioService } from '../service/cardapio.service';
import { CardapioFormService, CardapioFormGroup } from './cardapio-form.service';

@Component({
  standalone: true,
  selector: 'jhi-cardapio-update',
  templateUrl: './cardapio-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CardapioUpdateComponent implements OnInit {
  isSaving = false;
  cardapio: ICardapio | null = null;

  estabelecimentosSharedCollection: IEstabelecimento[] = [];

  editForm: CardapioFormGroup = this.cardapioFormService.createCardapioFormGroup();

  constructor(
    protected cardapioService: CardapioService,
    protected cardapioFormService: CardapioFormService,
    protected estabelecimentoService: EstabelecimentoService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareEstabelecimento = (o1: IEstabelecimento | null, o2: IEstabelecimento | null): boolean =>
    this.estabelecimentoService.compareEstabelecimento(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cardapio }) => {
      this.cardapio = cardapio;
      if (cardapio) {
        this.updateForm(cardapio);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cardapio = this.cardapioFormService.getCardapio(this.editForm);
    if (cardapio.id !== null) {
      this.subscribeToSaveResponse(this.cardapioService.update(cardapio));
    } else {
      this.subscribeToSaveResponse(this.cardapioService.create(cardapio));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICardapio>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(cardapio: ICardapio): void {
    this.cardapio = cardapio;
    this.cardapioFormService.resetForm(this.editForm, cardapio);

    this.estabelecimentosSharedCollection = this.estabelecimentoService.addEstabelecimentoToCollectionIfMissing<IEstabelecimento>(
      this.estabelecimentosSharedCollection,
      cardapio.estabelecimento,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.estabelecimentoService
      .query()
      .pipe(map((res: HttpResponse<IEstabelecimento[]>) => res.body ?? []))
      .pipe(
        map((estabelecimentos: IEstabelecimento[]) =>
          this.estabelecimentoService.addEstabelecimentoToCollectionIfMissing<IEstabelecimento>(
            estabelecimentos,
            this.cardapio?.estabelecimento,
          ),
        ),
      )
      .subscribe((estabelecimentos: IEstabelecimento[]) => (this.estabelecimentosSharedCollection = estabelecimentos));
  }

  toggleCheckbox(controlName: string) {
    const control = this.editForm.get(controlName);
    if (control) {
      control.setValue(!control.value);
    }
  }
}
