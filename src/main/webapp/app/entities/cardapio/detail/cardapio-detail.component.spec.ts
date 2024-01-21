import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { CardapioDetailComponent } from './cardapio-detail.component';

describe('Cardapio Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CardapioDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: CardapioDetailComponent,
              resolve: { cardapio: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CardapioDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load cardapio on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CardapioDetailComponent);

      // THEN
      expect(instance.cardapio).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
