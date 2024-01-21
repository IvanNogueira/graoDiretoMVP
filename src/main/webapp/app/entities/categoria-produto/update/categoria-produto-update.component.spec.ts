import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CategoriaProdutoService } from '../service/categoria-produto.service';
import { ICategoriaProduto } from '../categoria-produto.model';
import { CategoriaProdutoFormService } from './categoria-produto-form.service';

import { CategoriaProdutoUpdateComponent } from './categoria-produto-update.component';

describe('CategoriaProduto Management Update Component', () => {
  let comp: CategoriaProdutoUpdateComponent;
  let fixture: ComponentFixture<CategoriaProdutoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let categoriaProdutoFormService: CategoriaProdutoFormService;
  let categoriaProdutoService: CategoriaProdutoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), CategoriaProdutoUpdateComponent],
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
      .overrideTemplate(CategoriaProdutoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CategoriaProdutoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    categoriaProdutoFormService = TestBed.inject(CategoriaProdutoFormService);
    categoriaProdutoService = TestBed.inject(CategoriaProdutoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const categoriaProduto: ICategoriaProduto = { id: 456 };

      activatedRoute.data = of({ categoriaProduto });
      comp.ngOnInit();

      expect(comp.categoriaProduto).toEqual(categoriaProduto);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICategoriaProduto>>();
      const categoriaProduto = { id: 123 };
      jest.spyOn(categoriaProdutoFormService, 'getCategoriaProduto').mockReturnValue(categoriaProduto);
      jest.spyOn(categoriaProdutoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ categoriaProduto });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: categoriaProduto }));
      saveSubject.complete();

      // THEN
      expect(categoriaProdutoFormService.getCategoriaProduto).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(categoriaProdutoService.update).toHaveBeenCalledWith(expect.objectContaining(categoriaProduto));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICategoriaProduto>>();
      const categoriaProduto = { id: 123 };
      jest.spyOn(categoriaProdutoFormService, 'getCategoriaProduto').mockReturnValue({ id: null });
      jest.spyOn(categoriaProdutoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ categoriaProduto: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: categoriaProduto }));
      saveSubject.complete();

      // THEN
      expect(categoriaProdutoFormService.getCategoriaProduto).toHaveBeenCalled();
      expect(categoriaProdutoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICategoriaProduto>>();
      const categoriaProduto = { id: 123 };
      jest.spyOn(categoriaProdutoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ categoriaProduto });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(categoriaProdutoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
