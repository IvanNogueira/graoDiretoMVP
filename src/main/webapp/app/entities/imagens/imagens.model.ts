import { IEstabelecimento } from 'app/entities/estabelecimento/estabelecimento.model';
import { IProduto } from 'app/entities/produto/produto.model';
import { LocalImagem } from 'app/entities/enumerations/local-imagem.model';

export interface IImagens {
  id: number;
  imagemContent?: string | null;
  imagemContentContentType?: string | null;
  imagemContentType?: string | null;
  localImagem?: keyof typeof LocalImagem | null;
  estabelecimento?: IEstabelecimento | null;
  produto?: IProduto | null;
}

export type NewImagens = Omit<IImagens, 'id'> & { id: null };
