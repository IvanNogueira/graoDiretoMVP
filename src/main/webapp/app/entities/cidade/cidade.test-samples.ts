import { ICidade, NewCidade } from './cidade.model';

export const sampleWithRequiredData: ICidade = {
  id: 31858,
  nome: 'emanate',
};

export const sampleWithPartialData: ICidade = {
  id: 10001,
  nome: 'gosh solemnize inasmuch',
};

export const sampleWithFullData: ICidade = {
  id: 24299,
  nome: 'dear',
};

export const sampleWithNewData: NewCidade = {
  nome: 'kindly peen gadzooks',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
