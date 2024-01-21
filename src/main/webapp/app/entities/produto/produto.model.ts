import { ICategoriaProduto } from 'app/entities/categoria-produto/categoria-produto.model';
import { ICardapio } from 'app/entities/cardapio/cardapio.model';

export interface IProduto {
  id: number;
  nome?: string | null;
  descricao?: string | null;
  preco?: number | null;
  desconto?: number | null;
  imagemProduto?: string | null;
  imagemProdutoContentType?: string | null;
  categoriaProduto?: ICategoriaProduto | null;
  cardapio?: ICardapio | null;
}

export type NewProduto = Omit<IProduto, 'id'> & { id: null };
