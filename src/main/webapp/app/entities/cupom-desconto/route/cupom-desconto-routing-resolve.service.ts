import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICupomDesconto } from '../cupom-desconto.model';
import { CupomDescontoService } from '../service/cupom-desconto.service';

export const cupomDescontoResolve = (route: ActivatedRouteSnapshot): Observable<null | ICupomDesconto> => {
  const id = route.params['id'];
  if (id) {
    return inject(CupomDescontoService)
      .find(id)
      .pipe(
        mergeMap((cupomDesconto: HttpResponse<ICupomDesconto>) => {
          if (cupomDesconto.body) {
            return of(cupomDesconto.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default cupomDescontoResolve;
