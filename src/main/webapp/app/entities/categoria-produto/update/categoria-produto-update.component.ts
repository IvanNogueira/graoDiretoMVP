import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICategoriaProduto } from '../categoria-produto.model';
import { CategoriaProdutoService } from '../service/categoria-produto.service';
import { CategoriaProdutoFormService, CategoriaProdutoFormGroup } from './categoria-produto-form.service';

@Component({
  standalone: true,
  selector: 'jhi-categoria-produto-update',
  templateUrl: './categoria-produto-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CategoriaProdutoUpdateComponent implements OnInit {
  isSaving = false;
  categoriaProduto: ICategoriaProduto | null = null;

  editForm: CategoriaProdutoFormGroup = this.categoriaProdutoFormService.createCategoriaProdutoFormGroup();

  constructor(
    protected categoriaProdutoService: CategoriaProdutoService,
    protected categoriaProdutoFormService: CategoriaProdutoFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categoriaProduto }) => {
      this.categoriaProduto = categoriaProduto;
      if (categoriaProduto) {
        this.updateForm(categoriaProduto);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const categoriaProduto = this.categoriaProdutoFormService.getCategoriaProduto(this.editForm);
    if (categoriaProduto.id !== null) {
      this.subscribeToSaveResponse(this.categoriaProdutoService.update(categoriaProduto));
    } else {
      this.subscribeToSaveResponse(this.categoriaProdutoService.create(categoriaProduto));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategoriaProduto>>): void {
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

  protected updateForm(categoriaProduto: ICategoriaProduto): void {
    this.categoriaProduto = categoriaProduto;
    this.categoriaProdutoFormService.resetForm(this.editForm, categoriaProduto);
  }
}
