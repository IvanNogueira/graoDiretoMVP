import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { CupomDescontoDetailComponent } from './cupom-desconto-detail.component';

describe('CupomDesconto Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CupomDescontoDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: CupomDescontoDetailComponent,
              resolve: { cupomDesconto: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CupomDescontoDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load cupomDesconto on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CupomDescontoDetailComponent);

      // THEN
      expect(instance.cupomDesconto).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
