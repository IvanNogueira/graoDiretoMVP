import dayjs from 'dayjs/esm';

import { ICardapio, NewCardapio } from './cardapio.model';

export const sampleWithRequiredData: ICardapio = {
  id: 8679,
  nome: 'plop until venerate',
};

export const sampleWithPartialData: ICardapio = {
  id: 16010,
  nome: 'sore whether',
  ativo: false,
};

export const sampleWithFullData: ICardapio = {
  id: 8757,
  nome: 'if',
  dataCriacao: dayjs('2024-01-19'),
  ativo: false,
};

export const sampleWithNewData: NewCardapio = {
  nome: 'amused though extraneous',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
