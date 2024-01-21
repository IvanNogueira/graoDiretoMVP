import { ICupomDesconto, NewCupomDesconto } from './cupom-desconto.model';

export const sampleWithRequiredData: ICupomDesconto = {
  id: 28026,
};

export const sampleWithPartialData: ICupomDesconto = {
  id: 23179,
  valorDesconto: 27031.21,
  valorMinimo: false,
  valorMinimoRegra: 15208.45,
};

export const sampleWithFullData: ICupomDesconto = {
  id: 27516,
  valorDesconto: 22061.44,
  valorMinimo: false,
  valorMinimoRegra: 18468.81,
  descricaoRegras: 'aw yoga',
  valido: true,
};

export const sampleWithNewData: NewCupomDesconto = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
