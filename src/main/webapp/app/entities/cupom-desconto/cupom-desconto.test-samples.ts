import { ICupomDesconto, NewCupomDesconto } from './cupom-desconto.model';

export const sampleWithRequiredData: ICupomDesconto = {
  id: 1904,
};

export const sampleWithPartialData: ICupomDesconto = {
  id: 26642,
  nome: 'pfft garland',
  valorDesconto: 25847.82,
};

export const sampleWithFullData: ICupomDesconto = {
  id: 10173,
  nome: 'whoa',
  valorDesconto: 19368.91,
  valorMinimo: false,
  valorMinimoRegra: 20958.34,
  descricaoRegras: 'certainly',
  valido: true,
};

export const sampleWithNewData: NewCupomDesconto = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
