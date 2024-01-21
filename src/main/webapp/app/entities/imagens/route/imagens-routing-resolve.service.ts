import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IImagens } from '../imagens.model';
import { ImagensService } from '../service/imagens.service';

export const imagensResolve = (route: ActivatedRouteSnapshot): Observable<null | IImagens> => {
  const id = route.params['id'];
  if (id) {
    return inject(ImagensService)
      .find(id)
      .pipe(
        mergeMap((imagens: HttpResponse<IImagens>) => {
          if (imagens.body) {
            return of(imagens.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default imagensResolve;
