import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IEstabelecimento } from 'app/entities/estabelecimento/estabelecimento.model';
import { EstabelecimentoService } from 'app/entities/estabelecimento/service/estabelecimento.service';
import { CardapioService } from '../service/cardapio.service';
import { ICardapio } from '../cardapio.model';
import { CardapioFormService } from './cardapio-form.service';

import { CardapioUpdateComponent } from './cardapio-update.component';

describe('Cardapio Management Update Component', () => {
  let comp: CardapioUpdateComponent;
  let fixture: ComponentFixture<CardapioUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let cardapioFormService: CardapioFormService;
  let cardapioService: CardapioService;
  let estabelecimentoService: EstabelecimentoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), CardapioUpdateComponent],
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
      .overrideTemplate(CardapioUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CardapioUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    cardapioFormService = TestBed.inject(CardapioFormService);
    cardapioService = TestBed.inject(CardapioService);
    estabelecimentoService = TestBed.inject(EstabelecimentoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Estabelecimento query and add missing value', () => {
      const cardapio: ICardapio = { id: 456 };
      const estabelecimento: IEstabelecimento = { id: 11620 };
      cardapio.estabelecimento = estabelecimento;

      const estabelecimentoCollection: IEstabelecimento[] = [{ id: 31058 }];
      jest.spyOn(estabelecimentoService, 'query').mockReturnValue(of(new HttpResponse({ body: estabelecimentoCollection })));
      const additionalEstabelecimentos = [estabelecimento];
      const expectedCollection: IEstabelecimento[] = [...additionalEstabelecimentos, ...estabelecimentoCollection];
      jest.spyOn(estabelecimentoService, 'addEstabelecimentoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ cardapio });
      comp.ngOnInit();

      expect(estabelecimentoService.query).toHaveBeenCalled();
      expect(estabelecimentoService.addEstabelecimentoToCollectionIfMissing).toHaveBeenCalledWith(
        estabelecimentoCollection,
        ...additionalEstabelecimentos.map(expect.objectContaining),
      );
      expect(comp.estabelecimentosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const cardapio: ICardapio = { id: 456 };
      const estabelecimento: IEstabelecimento = { id: 14944 };
      cardapio.estabelecimento = estabelecimento;

      activatedRoute.data = of({ cardapio });
      comp.ngOnInit();

      expect(comp.estabelecimentosSharedCollection).toContain(estabelecimento);
      expect(comp.cardapio).toEqual(cardapio);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICardapio>>();
      const cardapio = { id: 123 };
      jest.spyOn(cardapioFormService, 'getCardapio').mockReturnValue(cardapio);
      jest.spyOn(cardapioService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cardapio });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cardapio }));
      saveSubject.complete();

      // THEN
      expect(cardapioFormService.getCardapio).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(cardapioService.update).toHaveBeenCalledWith(expect.objectContaining(cardapio));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICardapio>>();
      const cardapio = { id: 123 };
      jest.spyOn(cardapioFormService, 'getCardapio').mockReturnValue({ id: null });
      jest.spyOn(cardapioService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cardapio: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cardapio }));
      saveSubject.complete();

      // THEN
      expect(cardapioFormService.getCardapio).toHaveBeenCalled();
      expect(cardapioService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICardapio>>();
      const cardapio = { id: 123 };
      jest.spyOn(cardapioService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cardapio });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(cardapioService.update).toHaveBeenCalled();
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
