import dayjs from 'dayjs/esm';

import { IEstabelecimento, NewEstabelecimento } from './estabelecimento.model';

export const sampleWithRequiredData: IEstabelecimento = {
  id: 29089,
  nome: 'circumnavigate decimate',
  logradouro: 'variety below eek',
  bairro: 'finally',
  cep: 'behalf anti',
};

export const sampleWithPartialData: IEstabelecimento = {
  id: 14104,
  nome: 'mmm good',
  criadoEm: dayjs('2024-01-19T20:54'),
  logradouro: 'irritably',
  bairro: 'penalise slog',
  cep: 'supposing regarding omnivore',
};

export const sampleWithFullData: IEstabelecimento = {
  id: 22063,
  nome: 'fully amidst down',
  telefone: 'um how yum',
  email: 'Felicia.Franco@hotmail.com',
  tipoEstabelecimento: 'ACAI',
  capa: '../fake-data/blob/hipster.png',
  capaContentType: 'unknown',
  logo: '../fake-data/blob/hipster.png',
  logoContentType: 'unknown',
  criadoEm: dayjs('2024-01-20T00:31'),
  logradouro: 'gah',
  numero: 'selfishly filch',
  complemento: 'squelch',
  bairro: 'requite',
  cep: 'brisk',
};

export const sampleWithNewData: NewEstabelecimento = {
  nome: 'phew',
  logradouro: 'cartoon fit',
  bairro: 'phew filing',
  cep: 'ick hmph',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
