import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICardapio } from '../cardapio.model';
import { CardapioService } from '../service/cardapio.service';

export const cardapioResolve = (route: ActivatedRouteSnapshot): Observable<null | ICardapio> => {
  const id = route.params['id'];
  if (id) {
    return inject(CardapioService)
      .find(id)
      .pipe(
        mergeMap((cardapio: HttpResponse<ICardapio>) => {
          if (cardapio.body) {
            return of(cardapio.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default cardapioResolve;
