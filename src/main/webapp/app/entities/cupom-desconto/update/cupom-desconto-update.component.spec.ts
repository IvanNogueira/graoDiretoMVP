import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IEstabelecimento } from 'app/entities/estabelecimento/estabelecimento.model';
import { EstabelecimentoService } from 'app/entities/estabelecimento/service/estabelecimento.service';
import { CupomDescontoService } from '../service/cupom-desconto.service';
import { ICupomDesconto } from '../cupom-desconto.model';
import { CupomDescontoFormService } from './cupom-desconto-form.service';

import { CupomDescontoUpdateComponent } from './cupom-desconto-update.component';

describe('CupomDesconto Management Update Component', () => {
  let comp: CupomDescontoUpdateComponent;
  let fixture: ComponentFixture<CupomDescontoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let cupomDescontoFormService: CupomDescontoFormService;
  let cupomDescontoService: CupomDescontoService;
  let estabelecimentoService: EstabelecimentoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), CupomDescontoUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(CupomDescontoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CupomDescontoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    cupomDescontoFormService = TestBed.inject(CupomDescontoFormService);
    cupomDescontoService = TestBed.inject(CupomDescontoService);
    estabelecimentoService = TestBed.inject(EstabelecimentoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Estabelecimento query and add missing value', () => {
      const cupomDesconto: ICupomDesconto = { id: 456 };
      const estabelecimento: IEstabelecimento = { id: 11139 };
      cupomDesconto.estabelecimento = estabelecimento;

      const estabelecimentoCollection: IEstabelecimento[] = [{ id: 11692 }];
      jest.spyOn(estabelecimentoService, 'query').mockReturnValue(of(new HttpResponse({ body: estabelecimentoCollection })));
      const additionalEstabelecimentos = [estabelecimento];
      const expectedCollection: IEstabelecimento[] = [...additionalEstabelecimentos, ...estabelecimentoCollection];
      jest.spyOn(estabelecimentoService, 'addEstabelecimentoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ cupomDesconto });
      comp.ngOnInit();

      expect(estabelecimentoService.query).toHaveBeenCalled();
      expect(estabelecimentoService.addEstabelecimentoToCollectionIfMissing).toHaveBeenCalledWith(
        estabelecimentoCollection,
        ...additionalEstabelecimentos.map(expect.objectContaining),
      );
      expect(comp.estabelecimentosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const cupomDesconto: ICupomDesconto = { id: 456 };
      const estabelecimento: IEstabelecimento = { id: 15474 };
      cupomDesconto.estabelecimento = estabelecimento;

      activatedRoute.data = of({ cupomDesconto });
      comp.ngOnInit();

      expect(comp.estabelecimentosSharedCollection).toContain(estabelecimento);
      expect(comp.cupomDesconto).toEqual(cupomDesconto);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICupomDesconto>>();
      const cupomDesconto = { id: 123 };
      jest.spyOn(cupomDescontoFormService, 'getCupomDesconto').mockReturnValue(cupomDesconto);
      jest.spyOn(cupomDescontoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cupomDesconto });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cupomDesconto }));
      saveSubject.complete();

      // THEN
      expect(cupomDescontoFormService.getCupomDesconto).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(cupomDescontoService.update).toHaveBeenCalledWith(expect.objectContaining(cupomDesconto));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICupomDesconto>>();
      const cupomDesconto = { id: 123 };
      jest.spyOn(cupomDescontoFormService, 'getCupomDesconto').mockReturnValue({ id: null });
      jest.spyOn(cupomDescontoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cupomDesconto: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cupomDesconto }));
      saveSubject.complete();

      // THEN
      expect(cupomDescontoFormService.getCupomDesconto).toHaveBeenCalled();
      expect(cupomDescontoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICupomDesconto>>();
      const cupomDesconto = { id: 123 };
      jest.spyOn(cupomDescontoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cupomDesconto });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(cupomDescontoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareEstabelecimento', () => {
      it('Should forward to estabelecimentoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(estabelecimentoService, 'compareEstabelecimento');
        comp.compareEstabelecimento(entity, entity2);
        expect(estabelecimentoService.compareEstabelecimento).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
