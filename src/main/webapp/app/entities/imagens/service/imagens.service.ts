import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IImagens, NewImagens } from '../imagens.model';

export type PartialUpdateImagens = Partial<IImagens> & Pick<IImagens, 'id'>;

export type EntityResponseType = HttpResponse<IImagens>;
export type EntityArrayResponseType = HttpResponse<IImagens[]>;

@Injectable({ providedIn: 'root' })
export class ImagensService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/imagens');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(imagens: NewImagens): Observable<EntityResponseType> {
    return this.http.post<IImagens>(this.resourceUrl, imagens, { observe: 'response' });
  }

  update(imagens: IImagens): Observable<EntityResponseType> {
    return this.http.put<IImagens>(`${this.resourceUrl}/${this.getImagensIdentifier(imagens)}`, imagens, { observe: 'response' });
  }

  partialUpdate(imagens: PartialUpdateImagens): Observable<EntityResponseType> {
    return this.http.patch<IImagens>(`${this.resourceUrl}/${this.getImagensIdentifier(imagens)}`, imagens, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IImagens>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IImagens[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getImagensIdentifier(imagens: Pick<IImagens, 'id'>): number {
    return imagens.id;
  }

  compareImagens(o1: Pick<IImagens, 'id'> | null, o2: Pick<IImagens, 'id'> | null): boolean {
    return o1 && o2 ? this.getImagensIdentifier(o1) === this.getImagensIdentifier(o2) : o1 === o2;
  }

  addImagensToCollectionIfMissing<Type extends Pick<IImagens, 'id'>>(
    imagensCollection: Type[],
    ...imagensToCheck: (Type | null | undefined)[]
  ): Type[] {
    const imagens: Type[] = imagensToCheck.filter(isPresent);
    if (imagens.length > 0) {
      const imagensCollectionIdentifiers = imagensCollection.map(imagensItem => this.getImagensIdentifier(imagensItem)!);
      const imagensToAdd = imagens.filter(imagensItem => {
        const imagensIdentifier = this.getImagensIdentifier(imagensItem);
        if (imagensCollectionIdentifiers.includes(imagensIdentifier)) {
          return false;
        }
        imagensCollectionIdentifiers.push(imagensIdentifier);
        return true;
      });
      return [...imagensToAdd, ...imagensCollection];
    }
    return imagensCollection;
  }
}
