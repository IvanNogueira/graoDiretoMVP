import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICategoriaProduto, NewCategoriaProduto } from '../categoria-produto.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICategoriaProduto for edit and NewCategoriaProdutoFormGroupInput for create.
 */
type CategoriaProdutoFormGroupInput = ICategoriaProduto | PartialWithRequiredKeyOf<NewCategoriaProduto>;

type CategoriaProdutoFormDefaults = Pick<NewCategoriaProduto, 'id'>;

type CategoriaProdutoFormGroupContent = {
  id: FormControl<ICategoriaProduto['id'] | NewCategoriaProduto['id']>;
  nome: FormControl<ICategoriaProduto['nome']>;
  descricao: FormControl<ICategoriaProduto['descricao']>;
};

export type CategoriaProdutoFormGroup = FormGroup<CategoriaProdutoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CategoriaProdutoFormService {
  createCategoriaProdutoFormGroup(categoriaProduto: CategoriaProdutoFormGroupInput = { id: null }): CategoriaProdutoFormGroup {
    const categoriaProdutoRawValue = {
      ...this.getFormDefaults(),
      ...categoriaProduto,
    };
    return new FormGroup<CategoriaProdutoFormGroupContent>({
      id: new FormControl(
        { value: categoriaProdutoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(categoriaProdutoRawValue.nome, {
        validators: [Validators.required],
      }),
      descricao: new FormControl(categoriaProdutoRawValue.descricao),
    });
  }

  getCategoriaProduto(form: CategoriaProdutoFormGroup): ICategoriaProduto | NewCategoriaProduto {
    return form.getRawValue() as ICategoriaProduto | NewCategoriaProduto;
  }

  resetForm(form: CategoriaProdutoFormGroup, categoriaProduto: CategoriaProdutoFormGroupInput): void {
    const categoriaProdutoRawValue = { ...this.getFormDefaults(), ...categoriaProduto };
    form.reset(
      {
        ...categoriaProdutoRawValue,
        id: { value: categoriaProdutoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CategoriaProdutoFormDefaults {
    return {
      id: null,
    };
  }
}
