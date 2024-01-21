import dayjs from 'dayjs/esm';
import { ICardapio } from 'app/entities/cardapio/cardapio.model';
import { IImagens } from 'app/entities/imagens/imagens.model';
import { ICupomDesconto } from 'app/entities/cupom-desconto/cupom-desconto.model';
import { ICidade } from 'app/entities/cidade/cidade.model';
import { TipoEstabelicimento } from 'app/entities/enumerations/tipo-estabelicimento.model';

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
  imagens?: IImagens[] | null;
  cupomDescontos?: ICupomDesconto[] | null;
  cidade?: ICidade | null;
}

export type NewEstabelecimento = Omit<IEstabelecimento, 'id'> & { id: null };
