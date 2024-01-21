import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { CategoriaProdutoDetailComponent } from './categoria-produto-detail.component';

describe('CategoriaProduto Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CategoriaProdutoDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: CategoriaProdutoDetailComponent,
              resolve: { categoriaProduto: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CategoriaProdutoDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load categoriaProduto on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CategoriaProdutoDetailComponent);

      // THEN
      expect(instance.categoriaProduto).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
