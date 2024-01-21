import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICategoriaProduto } from '../categoria-produto.model';
import { CategoriaProdutoService } from '../service/categoria-produto.service';

export const categoriaProdutoResolve = (route: ActivatedRouteSnapshot): Observable<null | ICategoriaProduto> => {
  const id = route.params['id'];
  if (id) {
    return inject(CategoriaProdutoService)
      .find(id)
      .pipe(
        mergeMap((categoriaProduto: HttpResponse<ICategoriaProduto>) => {
          if (categoriaProduto.body) {
            return of(categoriaProduto.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default categoriaProdutoResolve;
