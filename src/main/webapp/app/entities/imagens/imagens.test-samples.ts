import { IImagens, NewImagens } from './imagens.model';

export const sampleWithRequiredData: IImagens = {
  id: 24372,
};

export const sampleWithPartialData: IImagens = {
  id: 1393,
  imagemContentType: 'self-confidence geez teeming',
  localImagem: 'PRODUTO',
};

export const sampleWithFullData: IImagens = {
  id: 11986,
  imagemContent: '../fake-data/blob/hipster.png',
  imagemContentContentType: 'unknown',
  imagemContentType: 'even unless socialize',
  localImagem: 'CAPA_ESTABELECIMENTO',
};

export const sampleWithNewData: NewImagens = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
