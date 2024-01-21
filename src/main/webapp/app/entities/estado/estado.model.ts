import { ICidade } from 'app/entities/cidade/cidade.model';

export interface IEstado {
  id: number;
  nome?: string | null;
  uf?: string | null;
  cidades?: ICidade[] | null;
}

export type NewEstado = Omit<IEstado, 'id'> & { id: null };
