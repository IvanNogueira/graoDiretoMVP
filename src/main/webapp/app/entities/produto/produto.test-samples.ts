import { IProduto, NewProduto } from './produto.model';

export const sampleWithRequiredData: IProduto = {
  id: 3386,
  nome: 'dogwood lively',
  preco: 3403.63,
};

export const sampleWithPartialData: IProduto = {
  id: 17955,
  nome: 'molder yet ack',
  preco: 21990.04,
  desconto: 898.14,
};

export const sampleWithFullData: IProduto = {
  id: 10730,
  nome: 'but forceful',
  descricao: 'neatly',
  preco: 26191.46,
  desconto: 2229.18,
};

export const sampleWithNewData: NewProduto = {
  nome: 'unbearably airspace',
  preco: 15047.65,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
