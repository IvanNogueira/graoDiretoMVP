import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IEstabelecimento } from 'app/entities/estabelecimento/estabelecimento.model';
import { EstabelecimentoService } from 'app/entities/estabelecimento/service/estabelecimento.service';
import { IProduto } from 'app/entities/produto/produto.model';
import { ProdutoService } from 'app/entities/produto/service/produto.service';
import { IImagens } from '../imagens.model';
import { ImagensService } from '../service/imagens.service';
import { ImagensFormService } from './imagens-form.service';

import { ImagensUpdateComponent } from './imagens-update.component';

describe('Imagens Management Update Component', () => {
  let comp: ImagensUpdateComponent;
  let fixture: ComponentFixture<ImagensUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let imagensFormService: ImagensFormService;
  let imagensService: ImagensService;
  let estabelecimentoService: EstabelecimentoService;
  let produtoService: ProdutoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), ImagensUpdateComponent],
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
      .overrideTemplate(ImagensUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ImagensUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    imagensFormService = TestBed.inject(ImagensFormService);
    imagensService = TestBed.inject(ImagensService);
    estabelecimentoService = TestBed.inject(EstabelecimentoService);
    produtoService = TestBed.inject(ProdutoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Estabelecimento query and add missing value', () => {
      const imagens: IImagens = { id: 456 };
      const estabelecimento: IEstabelecimento = { id: 26401 };
      imagens.estabelecimento = estabelecimento;

      const estabelecimentoCollection: IEstabelecimento[] = [{ id: 4547 }];
      jest.spyOn(estabelecimentoService, 'query').mockReturnValue(of(new HttpResponse({ body: estabelecimentoCollection })));
      const additionalEstabelecimentos = [estabelecimento];
      const expectedCollection: IEstabelecimento[] = [...additionalEstabelecimentos, ...estabelecimentoCollection];
      jest.spyOn(estabelecimentoService, 'addEstabelecimentoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ imagens });
      comp.ngOnInit();

      expect(estabelecimentoService.query).toHaveBeenCalled();
      expect(estabelecimentoService.addEstabelecimentoToCollectionIfMissing).toHaveBeenCalledWith(
        estabelecimentoCollection,
        ...additionalEstabelecimentos.map(expect.objectContaining),
      );
      expect(comp.estabelecimentosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Produto query and add missing value', () => {
      const imagens: IImagens = { id: 456 };
      const produto: IProduto = { id: 7386 };
      imagens.produto = produto;

      const produtoCollection: IProduto[] = [{ id: 27571 }];
      jest.spyOn(produtoService, 'query').mockReturnValue(of(new HttpResponse({ body: produtoCollection })));
      const additionalProdutos = [produto];
      const expectedCollection: IProduto[] = [...additionalProdutos, ...produtoCollection];
      jest.spyOn(produtoService, 'addProdutoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ imagens });
      comp.ngOnInit();

      expect(produtoService.query).toHaveBeenCalled();
      expect(produtoService.addProdutoToCollectionIfMissing).toHaveBeenCalledWith(
        produtoCollection,
        ...additionalProdutos.map(expect.objectContaining),
      );
      expect(comp.produtosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const imagens: IImagens = { id: 456 };
      const estabelecimento: IEstabelecimento = { id: 31134 };
      imagens.estabelecimento = estabelecimento;
      const produto: IProduto = { id: 14690 };
      imagens.produto = produto;

      activatedRoute.data = of({ imagens });
      comp.ngOnInit();

      expect(comp.estabelecimentosSharedCollection).toContain(estabelecimento);
      expect(comp.produtosSharedCollection).toContain(produto);
      expect(comp.imagens).toEqual(imagens);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IImagens>>();
      const imagens = { id: 123 };
      jest.spyOn(imagensFormService, 'getImagens').mockReturnValue(imagens);
      jest.spyOn(imagensService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ imagens });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: imagens }));
      saveSubject.complete();

      // THEN
      expect(imagensFormService.getImagens).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(imagensService.update).toHaveBeenCalledWith(expect.objectContaining(imagens));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IImagens>>();
      const imagens = { id: 123 };
      jest.spyOn(imagensFormService, 'getImagens').mockReturnValue({ id: null });
      jest.spyOn(imagensService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ imagens: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: imagens }));
      saveSubject.complete();

      // THEN
      expect(imagensFormService.getImagens).toHaveBeenCalled();
      expect(imagensService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IImagens>>();
      const imagens = { id: 123 };
      jest.spyOn(imagensService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ imagens });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(imagensService.update).toHaveBeenCalled();
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

    describe('compareProduto', () => {
      it('Should forward to produtoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(produtoService, 'compareProduto');
        comp.compareProduto(entity, entity2);
        expect(produtoService.compareProduto).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
