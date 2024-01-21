import { IEstabelecimento } from 'app/entities/estabelecimento/estabelecimento.model';

export interface ICupomDesconto {
  id: number;
  valorDesconto?: number | null;
  valorMinimo?: boolean | null;
  valorMinimoRegra?: number | null;
  descricaoRegras?: string | null;
  valido?: boolean | null;
  estabelecimento?: IEstabelecimento | null;
}

export type NewCupomDesconto = Omit<ICupomDesconto, 'id'> & { id: null };
