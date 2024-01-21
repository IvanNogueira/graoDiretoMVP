import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../imagens.test-samples';

import { ImagensFormService } from './imagens-form.service';

describe('Imagens Form Service', () => {
  let service: ImagensFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ImagensFormService);
  });

  describe('Service methods', () => {
    describe('createImagensFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createImagensFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            imagemContent: expect.any(Object),
            imagemContentType: expect.any(Object),
            localImagem: expect.any(Object),
            estabelecimento: expect.any(Object),
            produto: expect.any(Object),
          }),
        );
      });

      it('passing IImagens should create a new form with FormGroup', () => {
        const formGroup = service.createImagensFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            imagemContent: expect.any(Object),
            imagemContentType: expect.any(Object),
            localImagem: expect.any(Object),
            estabelecimento: expect.any(Object),
            produto: expect.any(Object),
          }),
        );
      });
    });

    describe('getImagens', () => {
      it('should return NewImagens for default Imagens initial value', () => {
        const formGroup = service.createImagensFormGroup(sampleWithNewData);

        const imagens = service.getImagens(formGroup) as any;

        expect(imagens).toMatchObject(sampleWithNewData);
      });

      it('should return NewImagens for empty Imagens initial value', () => {
        const formGroup = service.createImagensFormGroup();

        const imagens = service.getImagens(formGroup) as any;

        expect(imagens).toMatchObject({});
      });

      it('should return IImagens', () => {
        const formGroup = service.createImagensFormGroup(sampleWithRequiredData);

        const imagens = service.getImagens(formGroup) as any;

        expect(imagens).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IImagens should not enable id FormControl', () => {
        const formGroup = service.createImagensFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewImagens should disable id FormControl', () => {
        const formGroup = service.createImagensFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
