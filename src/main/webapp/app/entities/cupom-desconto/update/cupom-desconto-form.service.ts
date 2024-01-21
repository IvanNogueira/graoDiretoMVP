import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICupomDesconto, NewCupomDesconto } from '../cupom-desconto.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICupomDesconto for edit and NewCupomDescontoFormGroupInput for create.
 */
type CupomDescontoFormGroupInput = ICupomDesconto | PartialWithRequiredKeyOf<NewCupomDesconto>;

type CupomDescontoFormDefaults = Pick<NewCupomDesconto, 'id' | 'valorMinimo' | 'valido'>;

type CupomDescontoFormGroupContent = {
  id: FormControl<ICupomDesconto['id'] | NewCupomDesconto['id']>;
  nome: FormControl<ICupomDesconto['nome']>;
  valorDesconto: FormControl<ICupomDesconto['valorDesconto']>;
  valorMinimo: FormControl<ICupomDesconto['valorMinimo']>;
  valorMinimoRegra: FormControl<ICupomDesconto['valorMinimoRegra']>;
  descricaoRegras: FormControl<ICupomDesconto['descricaoRegras']>;
  valido: FormControl<ICupomDesconto['valido']>;
  estabelecimento: FormControl<ICupomDesconto['estabelecimento']>;
};

export type CupomDescontoFormGroup = FormGroup<CupomDescontoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CupomDescontoFormService {
  createCupomDescontoFormGroup(cupomDesconto: CupomDescontoFormGroupInput = { id: null }): CupomDescontoFormGroup {
    const cupomDescontoRawValue = {
      ...this.getFormDefaults(),
      ...cupomDesconto,
    };
    return new FormGroup<CupomDescontoFormGroupContent>({
      id: new FormControl(
        { value: cupomDescontoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(cupomDescontoRawValue.nome),
      valorDesconto: new FormControl(cupomDescontoRawValue.valorDesconto),
      valorMinimo: new FormControl(cupomDescontoRawValue.valorMinimo),
      valorMinimoRegra: new FormControl(cupomDescontoRawValue.valorMinimoRegra),
      descricaoRegras: new FormControl(cupomDescontoRawValue.descricaoRegras),
      valido: new FormControl(cupomDescontoRawValue.valido),
      estabelecimento: new FormControl(cupomDescontoRawValue.estabelecimento),
    });
  }

  getCupomDesconto(form: CupomDescontoFormGroup): ICupomDesconto | NewCupomDesconto {
    return form.getRawValue() as ICupomDesconto | NewCupomDesconto;
  }

  resetForm(form: CupomDescontoFormGroup, cupomDesconto: CupomDescontoFormGroupInput): void {
    const cupomDescontoRawValue = { ...this.getFormDefaults(), ...cupomDesconto };
    form.reset(
      {
        ...cupomDescontoRawValue,
        id: { value: cupomDescontoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CupomDescontoFormDefaults {
    return {
      id: null,
      valorMinimo: false,
      valido: false,
    };
  }
}
