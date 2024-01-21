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
import { ICategoriaProduto } from 'app/entities/categoria-produto/categoria-produto.model';
import { CategoriaProdutoService } from 'app/entities/categoria-produto/service/categoria-produto.service';
import { ICardapio } from 'app/entities/cardapio/cardapio.model';
import { CardapioService } from 'app/entities/cardapio/service/cardapio.service';
import { ProdutoService } from '../service/produto.service';
import { IProduto } from '../produto.model';
import { ProdutoFormService, ProdutoFormGroup } from './produto-form.service';

@Component({
  standalone: true,
  selector: 'jhi-produto-update',
  templateUrl: './produto-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ProdutoUpdateComponent implements OnInit {
  isSaving = false;
  produto: IProduto | null = null;

  categoriaProdutosSharedCollection: ICategoriaProduto[] = [];
  cardapiosSharedCollection: ICardapio[] = [];

  editForm: ProdutoFormGroup = this.produtoFormService.createProdutoFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected produtoService: ProdutoService,
    protected produtoFormService: ProdutoFormService,
    protected categoriaProdutoService: CategoriaProdutoService,
    protected cardapioService: CardapioService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareCategoriaProduto = (o1: ICategoriaProduto | null, o2: ICategoriaProduto | null): boolean =>
    this.categoriaProdutoService.compareCategoriaProduto(o1, o2);

  compareCardapio = (o1: ICardapio | null, o2: ICardapio | null): boolean => this.cardapioService.compareCardapio(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ produto }) => {
      this.produto = produto;
      if (produto) {
        this.updateForm(produto);
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
    const produto = this.produtoFormService.getProduto(this.editForm);
    if (produto.id !== null) {
      this.subscribeToSaveResponse(this.produtoService.update(produto));
    } else {
      this.subscribeToSaveResponse(this.produtoService.create(produto));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProduto>>): void {
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

  protected updateForm(produto: IProduto): void {
    this.produto = produto;
    this.produtoFormService.resetForm(this.editForm, produto);

    this.categoriaProdutosSharedCollection = this.categoriaProdutoService.addCategoriaProdutoToCollectionIfMissing<ICategoriaProduto>(
      this.categoriaProdutosSharedCollection,
      produto.categoriaProduto,
    );
    this.cardapiosSharedCollection = this.cardapioService.addCardapioToCollectionIfMissing<ICardapio>(
      this.cardapiosSharedCollection,
      produto.cardapio,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.categoriaProdutoService
      .query()
      .pipe(map((res: HttpResponse<ICategoriaProduto[]>) => res.body ?? []))
      .pipe(
        map((categoriaProdutos: ICategoriaProduto[]) =>
          this.categoriaProdutoService.addCategoriaProdutoToCollectionIfMissing<ICategoriaProduto>(
            categoriaProdutos,
            this.produto?.categoriaProduto,
          ),
        ),
      )
      .subscribe((categoriaProdutos: ICategoriaProduto[]) => (this.categoriaProdutosSharedCollection = categoriaProdutos));

    this.cardapioService
      .query()
      .pipe(map((res: HttpResponse<ICardapio[]>) => res.body ?? []))
      .pipe(
        map((cardapios: ICardapio[]) =>
          this.cardapioService.addCardapioToCollectionIfMissing<ICardapio>(cardapios, this.produto?.cardapio),
        ),
      )
      .subscribe((cardapios: ICardapio[]) => (this.cardapiosSharedCollection = cardapios));
  }
}
