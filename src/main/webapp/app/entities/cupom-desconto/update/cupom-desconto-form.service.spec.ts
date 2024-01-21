import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../cupom-desconto.test-samples';

import { CupomDescontoFormService } from './cupom-desconto-form.service';

describe('CupomDesconto Form Service', () => {
  let service: CupomDescontoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CupomDescontoFormService);
  });

  describe('Service methods', () => {
    describe('createCupomDescontoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCupomDescontoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            valorDesconto: expect.any(Object),
            valorMinimo: expect.any(Object),
            valorMinimoRegra: expect.any(Object),
            descricaoRegras: expect.any(Object),
            valido: expect.any(Object),
            estabelecimento: expect.any(Object),
          }),
        );
      });

      it('passing ICupomDesconto should create a new form with FormGroup', () => {
        const formGroup = service.createCupomDescontoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            valorDesconto: expect.any(Object),
            valorMinimo: expect.any(Object),
            valorMinimoRegra: expect.any(Object),
            descricaoRegras: expect.any(Object),
            valido: expect.any(Object),
            estabelecimento: expect.any(Object),
          }),
        );
      });
    });

    describe('getCupomDesconto', () => {
      it('should return NewCupomDesconto for default CupomDesconto initial value', () => {
        const formGroup = service.createCupomDescontoFormGroup(sampleWithNewData);

        const cupomDesconto = service.getCupomDesconto(formGroup) as any;

        expect(cupomDesconto).toMatchObject(sampleWithNewData);
      });

      it('should return NewCupomDesconto for empty CupomDesconto initial value', () => {
        const formGroup = service.createCupomDescontoFormGroup();

        const cupomDesconto = service.getCupomDesconto(formGroup) as any;

        expect(cupomDesconto).toMatchObject({});
      });

      it('should return ICupomDesconto', () => {
        const formGroup = service.createCupomDescontoFormGroup(sampleWithRequiredData);

        const cupomDesconto = service.getCupomDesconto(formGroup) as any;

        expect(cupomDesconto).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICupomDesconto should not enable id FormControl', () => {
        const formGroup = service.createCupomDescontoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCupomDesconto should disable id FormControl', () => {
        const formGroup = service.createCupomDescontoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
