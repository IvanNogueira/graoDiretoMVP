import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IImagens, NewImagens } from '../imagens.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IImagens for edit and NewImagensFormGroupInput for create.
 */
type ImagensFormGroupInput = IImagens | PartialWithRequiredKeyOf<NewImagens>;

type ImagensFormDefaults = Pick<NewImagens, 'id'>;

type ImagensFormGroupContent = {
  id: FormControl<IImagens['id'] | NewImagens['id']>;
  imagemContent: FormControl<IImagens['imagemContent']>;
  imagemContentContentType: FormControl<IImagens['imagemContentContentType']>;
  imagemContentType: FormControl<IImagens['imagemContentType']>;
  localImagem: FormControl<IImagens['localImagem']>;
  estabelecimento: FormControl<IImagens['estabelecimento']>;
  produto: FormControl<IImagens['produto']>;
};

export type ImagensFormGroup = FormGroup<ImagensFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ImagensFormService {
  createImagensFormGroup(imagens: ImagensFormGroupInput = { id: null }): ImagensFormGroup {
    const imagensRawValue = {
      ...this.getFormDefaults(),
      ...imagens,
    };
    return new FormGroup<ImagensFormGroupContent>({
      id: new FormControl(
        { value: imagensRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      imagemContent: new FormControl(imagensRawValue.imagemContent),
      imagemContentContentType: new FormControl(imagensRawValue.imagemContentContentType),
      imagemContentType: new FormControl(imagensRawValue.imagemContentType),
      localImagem: new FormControl(imagensRawValue.localImagem),
      estabelecimento: new FormControl(imagensRawValue.estabelecimento),
      produto: new FormControl(imagensRawValue.produto),
    });
  }

  getImagens(form: ImagensFormGroup): IImagens | NewImagens {
    return form.getRawValue() as IImagens | NewImagens;
  }

  resetForm(form: ImagensFormGroup, imagens: ImagensFormGroupInput): void {
    const imagensRawValue = { ...this.getFormDefaults(), ...imagens };
    form.reset(
      {
        ...imagensRawValue,
        id: { value: imagensRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ImagensFormDefaults {
    return {
      id: null,
    };
  }
}
