import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEstabelecimento, IEstabelecimentoProdutoDTO, NewEstabelecimento } from '../estabelecimento.model';

export type PartialUpdateEstabelecimento = Partial<IEstabelecimento> & Pick<IEstabelecimento, 'id'>;

type RestOf<T extends IEstabelecimento | NewEstabelecimento> = Omit<T, 'criadoEm'> & {
  criadoEm?: string | null;
};

export type RestEstabelecimento = RestOf<IEstabelecimento>;

export type NewRestEstabelecimento = RestOf<NewEstabelecimento>;

export type PartialUpdateRestEstabelecimento = RestOf<PartialUpdateEstabelecimento>;

export type EntityResponseType = HttpResponse<IEstabelecimento>;
export type EntityArrayResponseType = HttpResponse<IEstabelecimento[]>;

@Injectable({ providedIn: 'root' })
export class EstabelecimentoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/estabelecimentos');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(estabelecimento: NewEstabelecimento): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(estabelecimento);
    return this.http
      .post<RestEstabelecimento>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(estabelecimento: IEstabelecimento): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(estabelecimento);
    return this.http
      .put<RestEstabelecimento>(`${this.resourceUrl}/${this.getEstabelecimentoIdentifier(estabelecimento)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(estabelecimento: PartialUpdateEstabelecimento): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(estabelecimento);
    return this.http
      .patch<RestEstabelecimento>(`${this.resourceUrl}/${this.getEstabelecimentoIdentifier(estabelecimento)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestEstabelecimento>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  findSearch(pesquisar: String): Observable<HttpResponse<IEstabelecimentoProdutoDTO>> {
    return this.http.get<IEstabelecimentoProdutoDTO>(`${this.resourceUrl}/pesquisar/${pesquisar}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestEstabelecimento[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEstabelecimentoIdentifier(estabelecimento: Pick<IEstabelecimento, 'id'>): number {
    return estabelecimento.id;
  }

  compareEstabelecimento(o1: Pick<IEstabelecimento, 'id'> | null, o2: Pick<IEstabelecimento, 'id'> | null): boolean {
    return o1 && o2 ? this.getEstabelecimentoIdentifier(o1) === this.getEstabelecimentoIdentifier(o2) : o1 === o2;
  }

  addEstabelecimentoToCollectionIfMissing<Type extends Pick<IEstabelecimento, 'id'>>(
    estabelecimentoCollection: Type[],
    ...estabelecimentosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const estabelecimentos: Type[] = estabelecimentosToCheck.filter(isPresent);
    if (estabelecimentos.length > 0) {
      const estabelecimentoCollectionIdentifiers = estabelecimentoCollection.map(
        estabelecimentoItem => this.getEstabelecimentoIdentifier(estabelecimentoItem)!,
      );
      const estabelecimentosToAdd = estabelecimentos.filter(estabelecimentoItem => {
        const estabelecimentoIdentifier = this.getEstabelecimentoIdentifier(estabelecimentoItem);
        if (estabelecimentoCollectionIdentifiers.includes(estabelecimentoIdentifier)) {
          return false;
        }
        estabelecimentoCollectionIdentifiers.push(estabelecimentoIdentifier);
        return true;
      });
      return [...estabelecimentosToAdd, ...estabelecimentoCollection];
    }
    return estabelecimentoCollection;
  }

  protected convertDateFromClient<T extends IEstabelecimento | NewEstabelecimento | PartialUpdateEstabelecimento>(
    estabelecimento: T,
  ): RestOf<T> {
    return {
      ...estabelecimento,
      criadoEm: estabelecimento.criadoEm?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restEstabelecimento: RestEstabelecimento): IEstabelecimento {
    return {
      ...restEstabelecimento,
      criadoEm: restEstabelecimento.criadoEm ? dayjs(restEstabelecimento.criadoEm) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestEstabelecimento>): HttpResponse<IEstabelecimento> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestEstabelecimento[]>): HttpResponse<IEstabelecimento[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
