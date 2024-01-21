export interface ICategoriaProduto {
  id: number;
  nome?: string | null;
  descricao?: string | null;
}

export type NewCategoriaProduto = Omit<ICategoriaProduto, 'id'> & { id: null };
