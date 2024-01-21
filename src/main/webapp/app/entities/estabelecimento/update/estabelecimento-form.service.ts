import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IEstabelecimento, NewEstabelecimento } from '../estabelecimento.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEstabelecimento for edit and NewEstabelecimentoFormGroupInput for create.
 */
type EstabelecimentoFormGroupInput = IEstabelecimento | PartialWithRequiredKeyOf<NewEstabelecimento>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IEstabelecimento | NewEstabelecimento> = Omit<T, 'criadoEm'> & {
  criadoEm?: string | null;
};

type EstabelecimentoFormRawValue = FormValueOf<IEstabelecimento>;

type NewEstabelecimentoFormRawValue = FormValueOf<NewEstabelecimento>;

type EstabelecimentoFormDefaults = Pick<NewEstabelecimento, 'id' | 'criadoEm'>;

type EstabelecimentoFormGroupContent = {
  id: FormControl<EstabelecimentoFormRawValue['id'] | NewEstabelecimento['id']>;
  nome: FormControl<EstabelecimentoFormRawValue['nome']>;
  telefone: FormControl<EstabelecimentoFormRawValue['telefone']>;
  email: FormControl<EstabelecimentoFormRawValue['email']>;
  tipoEstabelecimento: FormControl<EstabelecimentoFormRawValue['tipoEstabelecimento']>;
  capa: FormControl<EstabelecimentoFormRawValue['capa']>;
  capaContentType: FormControl<EstabelecimentoFormRawValue['capaContentType']>;
  logo: FormControl<EstabelecimentoFormRawValue['logo']>;
  logoContentType: FormControl<EstabelecimentoFormRawValue['logoContentType']>;
  criadoEm: FormControl<EstabelecimentoFormRawValue['criadoEm']>;
  logradouro: FormControl<EstabelecimentoFormRawValue['logradouro']>;
  numero: FormControl<EstabelecimentoFormRawValue['numero']>;
  complemento: FormControl<EstabelecimentoFormRawValue['complemento']>;
  bairro: FormControl<EstabelecimentoFormRawValue['bairro']>;
  cep: FormControl<EstabelecimentoFormRawValue['cep']>;
  cidade: FormControl<EstabelecimentoFormRawValue['cidade']>;
};

export type EstabelecimentoFormGroup = FormGroup<EstabelecimentoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EstabelecimentoFormService {
  createEstabelecimentoFormGroup(estabelecimento: EstabelecimentoFormGroupInput = { id: null }): EstabelecimentoFormGroup {
    const estabelecimentoRawValue = this.convertEstabelecimentoToEstabelecimentoRawValue({
      ...this.getFormDefaults(),
      ...estabelecimento,
    });
    return new FormGroup<EstabelecimentoFormGroupContent>({
      id: new FormControl(
        { value: estabelecimentoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(estabelecimentoRawValue.nome, {
        validators: [Validators.required],
      }),
      telefone: new FormControl(estabelecimentoRawValue.telefone),
      email: new FormControl(estabelecimentoRawValue.email),
      tipoEstabelecimento: new FormControl(estabelecimentoRawValue.tipoEstabelecimento),
      capa: new FormControl(estabelecimentoRawValue.capa),
      capaContentType: new FormControl(estabelecimentoRawValue.capaContentType),
      logo: new FormControl(estabelecimentoRawValue.logo),
      logoContentType: new FormControl(estabelecimentoRawValue.logoContentType),
      criadoEm: new FormControl(estabelecimentoRawValue.criadoEm),
      logradouro: new FormControl(estabelecimentoRawValue.logradouro, {
        validators: [Validators.required],
      }),
      numero: new FormControl(estabelecimentoRawValue.numero),
      complemento: new FormControl(estabelecimentoRawValue.complemento),
      bairro: new FormControl(estabelecimentoRawValue.bairro, {
        validators: [Validators.required],
      }),
      cep: new FormControl(estabelecimentoRawValue.cep, {
        validators: [Validators.required],
      }),
      cidade: new FormControl(estabelecimentoRawValue.cidade),
    });
  }

  getEstabelecimento(form: EstabelecimentoFormGroup): IEstabelecimento | NewEstabelecimento {
    return this.convertEstabelecimentoRawValueToEstabelecimento(
      form.getRawValue() as EstabelecimentoFormRawValue | NewEstabelecimentoFormRawValue,
    );
  }

  resetForm(form: EstabelecimentoFormGroup, estabelecimento: EstabelecimentoFormGroupInput): void {
    const estabelecimentoRawValue = this.convertEstabelecimentoToEstabelecimentoRawValue({ ...this.getFormDefaults(), ...estabelecimento });
    form.reset(
      {
        ...estabelecimentoRawValue,
        id: { value: estabelecimentoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): EstabelecimentoFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      criadoEm: currentTime,
    };
  }

  private convertEstabelecimentoRawValueToEstabelecimento(
    rawEstabelecimento: EstabelecimentoFormRawValue | NewEstabelecimentoFormRawValue,
  ): IEstabelecimento | NewEstabelecimento {
    return {
      ...rawEstabelecimento,
      criadoEm: dayjs(rawEstabelecimento.criadoEm, DATE_TIME_FORMAT),
    };
  }

  private convertEstabelecimentoToEstabelecimentoRawValue(
    estabelecimento: IEstabelecimento | (Partial<NewEstabelecimento> & EstabelecimentoFormDefaults),
  ): EstabelecimentoFormRawValue | PartialWithRequiredKeyOf<NewEstabelecimentoFormRawValue> {
    return {
      ...estabelecimento,
      criadoEm: estabelecimento.criadoEm ? estabelecimento.criadoEm.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
