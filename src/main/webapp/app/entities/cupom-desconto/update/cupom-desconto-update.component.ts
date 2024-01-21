import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEstabelecimento } from 'app/entities/estabelecimento/estabelecimento.model';
import { EstabelecimentoService } from 'app/entities/estabelecimento/service/estabelecimento.service';
import { ICupomDesconto } from '../cupom-desconto.model';
import { CupomDescontoService } from '../service/cupom-desconto.service';
import { CupomDescontoFormService, CupomDescontoFormGroup } from './cupom-desconto-form.service';

@Component({
  standalone: true,
  selector: 'jhi-cupom-desconto-update',
  templateUrl: './cupom-desconto-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CupomDescontoUpdateComponent implements OnInit {
  isSaving = false;
  cupomDesconto: ICupomDesconto | null = null;

  estabelecimentosSharedCollection: IEstabelecimento[] = [];

  editForm: CupomDescontoFormGroup = this.cupomDescontoFormService.createCupomDescontoFormGroup();

  constructor(
    protected cupomDescontoService: CupomDescontoService,
    protected cupomDescontoFormService: CupomDescontoFormService,
    protected estabelecimentoService: EstabelecimentoService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareEstabelecimento = (o1: IEstabelecimento | null, o2: IEstabelecimento | null): boolean =>
    this.estabelecimentoService.compareEstabelecimento(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cupomDesconto }) => {
      this.cupomDesconto = cupomDesconto;
      if (cupomDesconto) {
        this.updateForm(cupomDesconto);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cupomDesconto = this.cupomDescontoFormService.getCupomDesconto(this.editForm);
    if (cupomDesconto.id !== null) {
      this.subscribeToSaveResponse(this.cupomDescontoService.update(cupomDesconto));
    } else {
      this.subscribeToSaveResponse(this.cupomDescontoService.create(cupomDesconto));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICupomDesconto>>): void {
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

  protected updateForm(cupomDesconto: ICupomDesconto): void {
    this.cupomDesconto = cupomDesconto;
    this.cupomDescontoFormService.resetForm(this.editForm, cupomDesconto);

    this.estabelecimentosSharedCollection = this.estabelecimentoService.addEstabelecimentoToCollectionIfMissing<IEstabelecimento>(
      this.estabelecimentosSharedCollection,
      cupomDesconto.estabelecimento,
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
            this.cupomDesconto?.estabelecimento,
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
