import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ICategoriaProduto } from 'app/entities/categoria-produto/categoria-produto.model';
import { CategoriaProdutoService } from 'app/entities/categoria-produto/service/categoria-produto.service';
import { ICardapio } from 'app/entities/cardapio/cardapio.model';
import { CardapioService } from 'app/entities/cardapio/service/cardapio.service';
import { IProduto } from '../produto.model';
import { ProdutoService } from '../service/produto.service';
import { ProdutoFormService } from './produto-form.service';

import { ProdutoUpdateComponent } from './produto-update.component';

describe('Produto Management Update Component', () => {
  let comp: ProdutoUpdateComponent;
  let fixture: ComponentFixture<ProdutoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let produtoFormService: ProdutoFormService;
  let produtoService: ProdutoService;
  let categoriaProdutoService: CategoriaProdutoService;
  let cardapioService: CardapioService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), ProdutoUpdateComponent],
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
      .overrideTemplate(ProdutoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProdutoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    produtoFormService = TestBed.inject(ProdutoFormService);
    produtoService = TestBed.inject(ProdutoService);
    categoriaProdutoService = TestBed.inject(CategoriaProdutoService);
    cardapioService = TestBed.inject(CardapioService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call CategoriaProduto query and add missing value', () => {
      const produto: IProduto = { id: 456 };
      const categoriaProduto: ICategoriaProduto = { id: 24648 };
      produto.categoriaProduto = categoriaProduto;

      const categoriaProdutoCollection: ICategoriaProduto[] = [{ id: 10722 }];
      jest.spyOn(categoriaProdutoService, 'query').mockReturnValue(of(new HttpResponse({ body: categoriaProdutoCollection })));
      const additionalCategoriaProdutos = [categoriaProduto];
      const expectedCollection: ICategoriaProduto[] = [...additionalCategoriaProdutos, ...categoriaProdutoCollection];
      jest.spyOn(categoriaProdutoService, 'addCategoriaProdutoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ produto });
      comp.ngOnInit();

      expect(categoriaProdutoService.query).toHaveBeenCalled();
      expect(categoriaProdutoService.addCategoriaProdutoToCollectionIfMissing).toHaveBeenCalledWith(
        categoriaProdutoCollection,
        ...additionalCategoriaProdutos.map(expect.objectContaining),
      );
      expect(comp.categoriaProdutosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Cardapio query and add missing value', () => {
      const produto: IProduto = { id: 456 };
      const cardapio: ICardapio = { id: 20575 };
      produto.cardapio = cardapio;

      const cardapioCollection: ICardapio[] = [{ id: 22644 }];
      jest.spyOn(cardapioService, 'query').mockReturnValue(of(new HttpResponse({ body: cardapioCollection })));
      const additionalCardapios = [cardapio];
      const expectedCollection: ICardapio[] = [...additionalCardapios, ...cardapioCollection];
      jest.spyOn(cardapioService, 'addCardapioToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ produto });
      comp.ngOnInit();

      expect(cardapioService.query).toHaveBeenCalled();
      expect(cardapioService.addCardapioToCollectionIfMissing).toHaveBeenCalledWith(
        cardapioCollection,
        ...additionalCardapios.map(expect.objectContaining),
      );
      expect(comp.cardapiosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const produto: IProduto = { id: 456 };
      const categoriaProduto: ICategoriaProduto = { id: 30189 };
      produto.categoriaProduto = categoriaProduto;
      const cardapio: ICardapio = { id: 22158 };
      produto.cardapio = cardapio;

      activatedRoute.data = of({ produto });
      comp.ngOnInit();

      expect(comp.categoriaProdutosSharedCollection).toContain(categoriaProduto);
      expect(comp.cardapiosSharedCollection).toContain(cardapio);
      expect(comp.produto).toEqual(produto);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProduto>>();
      const produto = { id: 123 };
      jest.spyOn(produtoFormService, 'getProduto').mockReturnValue(produto);
      jest.spyOn(produtoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ produto });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: produto }));
      saveSubject.complete();

      // THEN
      expect(produtoFormService.getProduto).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(produtoService.update).toHaveBeenCalledWith(expect.objectContaining(produto));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProduto>>();
      const produto = { id: 123 };
      jest.spyOn(produtoFormService, 'getProduto').mockReturnValue({ id: null });
      jest.spyOn(produtoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ produto: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: produto }));
      saveSubject.complete();

      // THEN
      expect(produtoFormService.getProduto).toHaveBeenCalled();
      expect(produtoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProduto>>();
      const produto = { id: 123 };
      jest.spyOn(produtoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ produto });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(produtoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCategoriaProduto', () => {
      it('Should forward to categoriaProdutoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(categoriaProdutoService, 'compareCategoriaProduto');
        comp.compareCategoriaProduto(entity, entity2);
        expect(categoriaProdutoService.compareCategoriaProduto).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareCardapio', () => {
      it('Should forward to cardapioService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(cardapioService, 'compareCardapio');
        comp.compareCardapio(entity, entity2);
        expect(cardapioService.compareCardapio).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
