import dayjs from 'dayjs/esm';
import { ICardapio } from 'app/entities/cardapio/cardapio.model';
import { ICupomDesconto } from 'app/entities/cupom-desconto/cupom-desconto.model';
import { ICidade } from 'app/entities/cidade/cidade.model';
import { TipoEstabelicimento } from 'app/entities/enumerations/tipo-estabelicimento.model';
import { IUser } from '../user/user.model';
import { IProduto } from '../produto/produto.model';

export interface IEstabelecimento {
  id: number;
  nome?: string | null;
  telefone?: string | null;
  email?: string | null;
  tipoEstabelecimento?: keyof typeof TipoEstabelicimento | null;
  capa?: string | null;
  capaContentType?: string | null;
  logo?: string | null;
  logoContentType?: string | null;
  criadoEm?: dayjs.Dayjs | null;
  logradouro?: string | null;
  numero?: string | null;
  complemento?: string | null;
  bairro?: string | null;
  cep?: string | null;
  cardapios?: ICardapio[] | null;
  cupomDescontos?: ICupomDesconto[] | null;
  cidade?: ICidade | null;
  user?: IUser | null;
}

export interface IEstabelecimentoProdutoDTO {
  estabelecimento?: IEstabelecimento[];
  produto?: IProduto[];
}

export type NewEstabelecimento = Omit<IEstabelecimento, 'id'> & { id: null };
