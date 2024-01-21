import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../categoria-produto.test-samples';

import { CategoriaProdutoFormService } from './categoria-produto-form.service';

describe('CategoriaProduto Form Service', () => {
  let service: CategoriaProdutoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CategoriaProdutoFormService);
  });

  describe('Service methods', () => {
    describe('createCategoriaProdutoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCategoriaProdutoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
          }),
        );
      });

      it('passing ICategoriaProduto should create a new form with FormGroup', () => {
        const formGroup = service.createCategoriaProdutoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
          }),
        );
      });
    });

    describe('getCategoriaProduto', () => {
      it('should return NewCategoriaProduto for default CategoriaProduto initial value', () => {
        const formGroup = service.createCategoriaProdutoFormGroup(sampleWithNewData);

        const categoriaProduto = service.getCategoriaProduto(formGroup) as any;

        expect(categoriaProduto).toMatchObject(sampleWithNewData);
      });

      it('should return NewCategoriaProduto for empty CategoriaProduto initial value', () => {
        const formGroup = service.createCategoriaProdutoFormGroup();

        const categoriaProduto = service.getCategoriaProduto(formGroup) as any;

        expect(categoriaProduto).toMatchObject({});
      });

      it('should return ICategoriaProduto', () => {
        const formGroup = service.createCategoriaProdutoFormGroup(sampleWithRequiredData);

        const categoriaProduto = service.getCategoriaProduto(formGroup) as any;

        expect(categoriaProduto).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICategoriaProduto should not enable id FormControl', () => {
        const formGroup = service.createCategoriaProdutoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCategoriaProduto should disable id FormControl', () => {
        const formGroup = service.createCategoriaProdutoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
