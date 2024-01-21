import { ICategoriaProduto, NewCategoriaProduto } from './categoria-produto.model';

export const sampleWithRequiredData: ICategoriaProduto = {
  id: 2844,
  nome: 'provision reproachfully midst',
};

export const sampleWithPartialData: ICategoriaProduto = {
  id: 30050,
  nome: 'before fast youngster',
};

export const sampleWithFullData: ICategoriaProduto = {
  id: 19771,
  nome: 'phew',
  descricao: 'gosh phew',
};

export const sampleWithNewData: NewCategoriaProduto = {
  nome: 'mineshaft over',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
