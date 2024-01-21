import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { ICidade } from 'app/entities/cidade/cidade.model';
import { CidadeService } from 'app/entities/cidade/service/cidade.service';
import { TipoEstabelicimento } from 'app/entities/enumerations/tipo-estabelicimento.model';
import { EstabelecimentoService } from '../service/estabelecimento.service';
import { IEstabelecimento } from '../estabelecimento.model';
import { EstabelecimentoFormService, EstabelecimentoFormGroup } from './estabelecimento-form.service';

@Component({
  standalone: true,
  selector: 'jhi-estabelecimento-update',
  templateUrl: './estabelecimento-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class EstabelecimentoUpdateComponent implements OnInit {
  isSaving = false;
  estabelecimento: IEstabelecimento | null = null;
  tipoEstabelicimentoValues = Object.keys(TipoEstabelicimento);

  cidadesSharedCollection: ICidade[] = [];

  editForm: EstabelecimentoFormGroup = this.estabelecimentoFormService.createEstabelecimentoFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected estabelecimentoService: EstabelecimentoService,
    protected estabelecimentoFormService: EstabelecimentoFormService,
    protected cidadeService: CidadeService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareCidade = (o1: ICidade | null, o2: ICidade | null): boolean => this.cidadeService.compareCidade(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ estabelecimento }) => {
      this.estabelecimento = estabelecimento;
      if (estabelecimento) {
        this.updateForm(estabelecimento);
      }

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('graoDiretoMvpApp.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const estabelecimento = this.estabelecimentoFormService.getEstabelecimento(this.editForm);
    if (estabelecimento.id !== null) {
      this.subscribeToSaveResponse(this.estabelecimentoService.update(estabelecimento));
    } else {
      this.subscribeToSaveResponse(this.estabelecimentoService.create(estabelecimento));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEstabelecimento>>): void {
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

  protected updateForm(estabelecimento: IEstabelecimento): void {
    this.estabelecimento = estabelecimento;
    this.estabelecimentoFormService.resetForm(this.editForm, estabelecimento);

    this.cidadesSharedCollection = this.cidadeService.addCidadeToCollectionIfMissing<ICidade>(
      this.cidadesSharedCollection,
      estabelecimento.cidade,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.cidadeService
      .query()
      .pipe(map((res: HttpResponse<ICidade[]>) => res.body ?? []))
      .pipe(map((cidades: ICidade[]) => this.cidadeService.addCidadeToCollectionIfMissing<ICidade>(cidades, this.estabelecimento?.cidade)))
      .subscribe((cidades: ICidade[]) => (this.cidadesSharedCollection = cidades));
  }
}
