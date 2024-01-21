import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEstabelecimento } from '../estabelecimento.model';
import { EstabelecimentoService } from '../service/estabelecimento.service';

export const estabelecimentoResolve = (route: ActivatedRouteSnapshot): Observable<null | IEstabelecimento> => {
  const id = route.params['id'];
  if (id) {
    return inject(EstabelecimentoService)
      .find(id)
      .pipe(
        mergeMap((estabelecimento: HttpResponse<IEstabelecimento>) => {
          if (estabelecimento.body) {
            return of(estabelecimento.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default estabelecimentoResolve;
