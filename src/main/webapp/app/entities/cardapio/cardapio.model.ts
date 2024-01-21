import dayjs from 'dayjs/esm';
import { IProduto } from 'app/entities/produto/produto.model';
import { IEstabelecimento } from 'app/entities/estabelecimento/estabelecimento.model';

export interface ICardapio {
  id: number;
  nome?: string | null;
  dataCriacao?: dayjs.Dayjs | null;
  ativo?: boolean | null;
  produtos?: IProduto[] | null;
  estabelecimento?: IEstabelecimento | null;
}

export type NewCardapio = Omit<ICardapio, 'id'> & { id: null };
