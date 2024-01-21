import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICupomDesconto, NewCupomDesconto } from '../cupom-desconto.model';

export type PartialUpdateCupomDesconto = Partial<ICupomDesconto> & Pick<ICupomDesconto, 'id'>;

export type EntityResponseType = HttpResponse<ICupomDesconto>;
export type EntityArrayResponseType = HttpResponse<ICupomDesconto[]>;

@Injectable({ providedIn: 'root' })
export class CupomDescontoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/cupom-descontos');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(cupomDesconto: NewCupomDesconto): Observable<EntityResponseType> {
    return this.http.post<ICupomDesconto>(this.resourceUrl, cupomDesconto, { observe: 'response' });
  }

  update(cupomDesconto: ICupomDesconto): Observable<EntityResponseType> {
    return this.http.put<ICupomDesconto>(`${this.resourceUrl}/${this.getCupomDescontoIdentifier(cupomDesconto)}`, cupomDesconto, {
      observe: 'response',
    });
  }

  partialUpdate(cupomDesconto: PartialUpdateCupomDesconto): Observable<EntityResponseType> {
    return this.http.patch<ICupomDesconto>(`${this.resourceUrl}/${this.getCupomDescontoIdentifier(cupomDesconto)}`, cupomDesconto, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICupomDesconto>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICupomDesconto[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCupomDescontoIdentifier(cupomDesconto: Pick<ICupomDesconto, 'id'>): number {
    return cupomDesconto.id;
  }

  compareCupomDesconto(o1: Pick<ICupomDesconto, 'id'> | null, o2: Pick<ICupomDesconto, 'id'> | null): boolean {
    return o1 && o2 ? this.getCupomDescontoIdentifier(o1) === this.getCupomDescontoIdentifier(o2) : o1 === o2;
  }

  addCupomDescontoToCollectionIfMissing<Type extends Pick<ICupomDesconto, 'id'>>(
    cupomDescontoCollection: Type[],
    ...cupomDescontosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const cupomDescontos: Type[] = cupomDescontosToCheck.filter(isPresent);
    if (cupomDescontos.length > 0) {
      const cupomDescontoCollectionIdentifiers = cupomDescontoCollection.map(
        cupomDescontoItem => this.getCupomDescontoIdentifier(cupomDescontoItem)!,
      );
      const cupomDescontosToAdd = cupomDescontos.filter(cupomDescontoItem => {
        const cupomDescontoIdentifier = this.getCupomDescontoIdentifier(cupomDescontoItem);
        if (cupomDescontoCollectionIdentifiers.includes(cupomDescontoIdentifier)) {
          return false;
        }
        cupomDescontoCollectionIdentifiers.push(cupomDescontoIdentifier);
        return true;
      });
      return [...cupomDescontosToAdd, ...cupomDescontoCollection];
    }
    return cupomDescontoCollection;
  }
}
