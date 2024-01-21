import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IEstabelecimento } from 'app/entities/estabelecimento/estabelecimento.model';
import { EstabelecimentoService } from 'app/entities/estabelecimento/service/estabelecimento.service';
import { IProduto } from 'app/entities/produto/produto.model';
import { ProdutoService } from 'app/entities/produto/service/produto.service';
import { LocalImagem } from 'app/entities/enumerations/local-imagem.model';
import { ImagensService } from '../service/imagens.service';
import { IImagens } from '../imagens.model';
import { ImagensFormService, ImagensFormGroup } from './imagens-form.service';

@Component({
  standalone: true,
  selector: 'jhi-imagens-update',
  templateUrl: './imagens-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ImagensUpdateComponent implements OnInit {
  isSaving = false;
  imagens: IImagens | null = null;
  localImagemValues = Object.keys(LocalImagem);

  estabelecimentosSharedCollection: IEstabelecimento[] = [];
  produtosSharedCollection: IProduto[] = [];

  editForm: ImagensFormGroup = this.imagensFormService.createImagensFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected imagensService: ImagensService,
    protected imagensFormService: ImagensFormService,
    protected estabelecimentoService: EstabelecimentoService,
    protected produtoService: ProdutoService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareEstabelecimento = (o1: IEstabelecimento | null, o2: IEstabelecimento | null): boolean =>
    this.estabelecimentoService.compareEstabelecimento(o1, o2);

  compareProduto = (o1: IProduto | null, o2: IProduto | null): boolean => this.produtoService.compareProduto(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ imagens }) => {
      this.imagens = imagens;
      if (imagens) {
        this.updateForm(imagens);
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

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const imagens = this.imagensFormService.getImagens(this.editForm);
    if (imagens.id !== null) {
      this.subscribeToSaveResponse(this.imagensService.update(imagens));
    } else {
      this.subscribeToSaveResponse(this.imagensService.create(imagens));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IImagens>>): void {
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

  protected updateForm(imagens: IImagens): void {
    this.imagens = imagens;
    this.imagensFormService.resetForm(this.editForm, imagens);

    this.estabelecimentosSharedCollection = this.estabelecimentoService.addEstabelecimentoToCollectionIfMissing<IEstabelecimento>(
      this.estabelecimentosSharedCollection,
      imagens.estabelecimento,
    );
    this.produtosSharedCollection = this.produtoService.addProdutoToCollectionIfMissing<IProduto>(
      this.produtosSharedCollection,
      imagens.produto,
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
            this.imagens?.estabelecimento,
          ),
        ),
      )
      .subscribe((estabelecimentos: IEstabelecimento[]) => (this.estabelecimentosSharedCollection = estabelecimentos));

    this.produtoService
      .query()
      .pipe(map((res: HttpResponse<IProduto[]>) => res.body ?? []))
      .pipe(map((produtos: IProduto[]) => this.produtoService.addProdutoToCollectionIfMissing<IProduto>(produtos, this.imagens?.produto)))
      .subscribe((produtos: IProduto[]) => (this.produtosSharedCollection = produtos));
  }
}
