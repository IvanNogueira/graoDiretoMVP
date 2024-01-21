import { IEstado, NewEstado } from './estado.model';

export const sampleWithRequiredData: IEstado = {
  id: 24019,
  nome: 'upliftingly proper unabashedly',
  uf: 'ack wherever',
};

export const sampleWithPartialData: IEstado = {
  id: 4397,
  nome: 'baggy',
  uf: 'given like periodical',
};

export const sampleWithFullData: IEstado = {
  id: 14899,
  nome: 'astonishing ick sharp',
  uf: 'alongside',
};

export const sampleWithNewData: NewEstado = {
  nome: 'whose nearly hourly',
  uf: 'nor',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
