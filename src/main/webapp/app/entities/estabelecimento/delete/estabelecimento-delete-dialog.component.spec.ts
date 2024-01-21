jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { EstabelecimentoService } from '../service/estabelecimento.service';

import { EstabelecimentoDeleteDialogComponent } from './estabelecimento-delete-dialog.component';

describe('Estabelecimento Management Delete Component', () => {
  let comp: EstabelecimentoDeleteDialogComponent;
  let fixture: ComponentFixture<EstabelecimentoDeleteDialogComponent>;
  let service: EstabelecimentoService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, EstabelecimentoDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(EstabelecimentoDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(EstabelecimentoDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(EstabelecimentoService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      }),
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
