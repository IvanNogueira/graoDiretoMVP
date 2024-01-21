import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ICidade } from 'app/entities/cidade/cidade.model';
import { CidadeService } from 'app/entities/cidade/service/cidade.service';
import { EstabelecimentoService } from '../service/estabelecimento.service';
import { IEstabelecimento } from '../estabelecimento.model';
import { EstabelecimentoFormService } from './estabelecimento-form.service';

import { EstabelecimentoUpdateComponent } from './estabelecimento-update.component';

describe('Estabelecimento Management Update Component', () => {
  let comp: EstabelecimentoUpdateComponent;
  let fixture: ComponentFixture<EstabelecimentoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let estabelecimentoFormService: EstabelecimentoFormService;
  let estabelecimentoService: EstabelecimentoService;
  let cidadeService: CidadeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), EstabelecimentoUpdateComponent],
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
      .overrideTemplate(EstabelecimentoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EstabelecimentoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    estabelecimentoFormService = TestBed.inject(EstabelecimentoFormService);
    estabelecimentoService = TestBed.inject(EstabelecimentoService);
    cidadeService = TestBed.inject(CidadeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Cidade query and add missing value', () => {
      const estabelecimento: IEstabelecimento = { id: 456 };
      const cidade: ICidade = { id: 21525 };
      estabelecimento.cidade = cidade;

      const cidadeCollection: ICidade[] = [{ id: 22469 }];
      jest.spyOn(cidadeService, 'query').mockReturnValue(of(new HttpResponse({ body: cidadeCollection })));
      const additionalCidades = [cidade];
      const expectedCollection: ICidade[] = [...additionalCidades, ...cidadeCollection];
      jest.spyOn(cidadeService, 'addCidadeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ estabelecimento });
      comp.ngOnInit();

      expect(cidadeService.query).toHaveBeenCalled();
      expect(cidadeService.addCidadeToCollectionIfMissing).toHaveBeenCalledWith(
        cidadeCollection,
        ...additionalCidades.map(expect.objectContaining),
      );
      expect(comp.cidadesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const estabelecimento: IEstabelecimento = { id: 456 };
      const cidade: ICidade = { id: 5299 };
      estabelecimento.cidade = cidade;

      activatedRoute.data = of({ estabelecimento });
      comp.ngOnInit();

      expect(comp.cidadesSharedCollection).toContain(cidade);
      expect(comp.estabelecimento).toEqual(estabelecimento);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEstabelecimento>>();
      const estabelecimento = { id: 123 };
      jest.spyOn(estabelecimentoFormService, 'getEstabelecimento').mockReturnValue(estabelecimento);
      jest.spyOn(estabelecimentoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ estabelecimento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: estabelecimento }));
      saveSubject.complete();

      // THEN
      expect(estabelecimentoFormService.getEstabelecimento).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(estabelecimentoService.update).toHaveBeenCalledWith(expect.objectContaining(estabelecimento));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEstabelecimento>>();
      const estabelecimento = { id: 123 };
      jest.spyOn(estabelecimentoFormService, 'getEstabelecimento').mockReturnValue({ id: null });
      jest.spyOn(estabelecimentoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ estabelecimento: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: estabelecimento }));
      saveSubject.complete();

      // THEN
      expect(estabelecimentoFormService.getEstabelecimento).toHaveBeenCalled();
      expect(estabelecimentoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEstabelecimento>>();
      const estabelecimento = { id: 123 };
      jest.spyOn(estabelecimentoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ estabelecimento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(estabelecimentoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCidade', () => {
      it('Should forward to cidadeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(cidadeService, 'compareCidade');
        comp.compareCidade(entity, entity2);
        expect(cidadeService.compareCidade).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
