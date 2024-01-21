import { IProduto, NewProduto } from './produto.model';

export const sampleWithRequiredData: IProduto = {
  id: 11209,
  nome: 'yippee bump amidst',
  preco: 23573.01,
};

export const sampleWithPartialData: IProduto = {
  id: 30320,
  nome: 'fine all kissingly',
  preco: 11874.21,
};

export const sampleWithFullData: IProduto = {
  id: 4306,
  nome: 'absent neatly',
  descricao: 'tremendously spike excitedly',
  preco: 9424.51,
  desconto: 28426.96,
  imagemProduto: '../fake-data/blob/hipster.png',
  imagemProdutoContentType: 'unknown',
};

export const sampleWithNewData: NewProduto = {
  nome: 'zone between',
  preco: 8218.94,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
