import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICategoriaProduto, NewCategoriaProduto } from '../categoria-produto.model';

export type PartialUpdateCategoriaProduto = Partial<ICategoriaProduto> & Pick<ICategoriaProduto, 'id'>;

export type EntityResponseType = HttpResponse<ICategoriaProduto>;
export type EntityArrayResponseType = HttpResponse<ICategoriaProduto[]>;

@Injectable({ providedIn: 'root' })
export class CategoriaProdutoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/categoria-produtos');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(categoriaProduto: NewCategoriaProduto): Observable<EntityResponseType> {
    return this.http.post<ICategoriaProduto>(this.resourceUrl, categoriaProduto, { observe: 'response' });
  }

  update(categoriaProduto: ICategoriaProduto): Observable<EntityResponseType> {
    return this.http.put<ICategoriaProduto>(
      `${this.resourceUrl}/${this.getCategoriaProdutoIdentifier(categoriaProduto)}`,
      categoriaProduto,
      { observe: 'response' },
    );
  }

  partialUpdate(categoriaProduto: PartialUpdateCategoriaProduto): Observable<EntityResponseType> {
    return this.http.patch<ICategoriaProduto>(
      `${this.resourceUrl}/${this.getCategoriaProdutoIdentifier(categoriaProduto)}`,
      categoriaProduto,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICategoriaProduto>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICategoriaProduto[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCategoriaProdutoIdentifier(categoriaProduto: Pick<ICategoriaProduto, 'id'>): number {
    return categoriaProduto.id;
  }

  compareCategoriaProduto(o1: Pick<ICategoriaProduto, 'id'> | null, o2: Pick<ICategoriaProduto, 'id'> | null): boolean {
    return o1 && o2 ? this.getCategoriaProdutoIdentifier(o1) === this.getCategoriaProdutoIdentifier(o2) : o1 === o2;
  }

  addCategoriaProdutoToCollectionIfMissing<Type extends Pick<ICategoriaProduto, 'id'>>(
    categoriaProdutoCollection: Type[],
    ...categoriaProdutosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const categoriaProdutos: Type[] = categoriaProdutosToCheck.filter(isPresent);
    if (categoriaProdutos.length > 0) {
      const categoriaProdutoCollectionIdentifiers = categoriaProdutoCollection.map(
        categoriaProdutoItem => this.getCategoriaProdutoIdentifier(categoriaProdutoItem)!,
      );
      const categoriaProdutosToAdd = categoriaProdutos.filter(categoriaProdutoItem => {
        const categoriaProdutoIdentifier = this.getCategoriaProdutoIdentifier(categoriaProdutoItem);
        if (categoriaProdutoCollectionIdentifiers.includes(categoriaProdutoIdentifier)) {
          return false;
        }
        categoriaProdutoCollectionIdentifiers.push(categoriaProdutoIdentifier);
        return true;
      });
      return [...categoriaProdutosToAdd, ...categoriaProdutoCollection];
    }
    return categoriaProdutoCollection;
  }
}
