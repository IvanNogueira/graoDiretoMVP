import { IEstabelecimento } from 'app/entities/estabelecimento/estabelecimento.model';
import { IEstado } from 'app/entities/estado/estado.model';

export interface ICidade {
  id: number;
  nome?: string | null;
  estabelecimentos?: IEstabelecimento[] | null;
  estado?: IEstado | null;
}

export type NewCidade = Omit<ICidade, 'id'> & { id: null };
