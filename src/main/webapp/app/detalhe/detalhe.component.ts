import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { Subject } from 'rxjs';
import { map, takeUntil } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { IEstabelecimento } from 'app/entities/estabelecimento/estabelecimento.model';
import { EstabelecimentoService } from 'app/entities/estabelecimento/service/estabelecimento.service';
import { IProduto } from 'app/entities/produto/produto.model';
import { ProdutoService } from 'app/entities/produto/service/produto.service';
import { CommonModule } from '@angular/common';
import { ICardapio } from 'app/entities/cardapio/cardapio.model';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ICupomDesconto } from 'app/entities/cupom-desconto/cupom-desconto.model';

interface ProdutosPorCategoria {
  [categoria: string]: IProduto[];
}

@Component({
  standalone: true,
  selector: 'jhi-detalhe',
  templateUrl: './detalhe.component.html',
  styleUrl: './detalhe.component.scss',
  imports: [SharedModule, RouterModule, CommonModule],
})
export default class DetalheComponent implements OnInit, OnDestroy {
  account: Account | null = null;

  private readonly destroy$ = new Subject<void>();
  estabelecimento?: IEstabelecimento;
  produtoCollection?: IProduto[];
  cupomCollection?: ICupomDesconto[];
  showSearchResults = false;
  produtosPorCategoria: ProdutosPorCategoria = {};
  @ViewChild('cupom') cupom: any;

  constructor(
    private accountService: AccountService,
    private router: Router,
    private estabelecimentoService: EstabelecimentoService,
    private produtoService: ProdutoService,
    private activatedRoute: ActivatedRoute,
    private modalService: NgbModal,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => {
      const estabelecimentoId = +params['id'];
      console.log('navegar', estabelecimentoId);
      this.estabelecimentoService.find(estabelecimentoId).subscribe(
        res => {
          this.estabelecimento = res.body!;
          this.getProdutosAtivosDoEstabelecimento();
          this.getCuponsAtivos();
        },
        error => {
          console.error('Erro:', error);
        },
      );
    });

    this.accountService
      .getAuthenticationState()
      .pipe(takeUntil(this.destroy$))
      .subscribe(account => ((this.account = account), console.log(this.account)));
  }

  getCuponsAtivos() {
    if (this.estabelecimento!.cupomDescontos && Array.isArray(this.estabelecimento!.cupomDescontos)) {
      this.cupomCollection = this.estabelecimento!.cupomDescontos.filter(cupom => cupom.valido);
      console.log('cupons: ', this.cupomCollection);
    }
  }

  getProdutosAtivosDoEstabelecimento(): void {
    // Verifique se a propriedade 'cardapio' está definida e é um array
    if (this.estabelecimento!.cardapios && Array.isArray(this.estabelecimento!.cardapios)) {
      // Filtra apenas os cardápios ativos
      const cardapiosAtivos: ICardapio[] = this.estabelecimento!.cardapios.filter(cardapio => cardapio.ativo);

      // Mapeia os produtos de todos os cardápios ativos
      const produtos: IProduto[] = cardapiosAtivos.reduce((acc, cardapio) => {
        return acc.concat(cardapio!.produtos!);
      }, [] as IProduto[]);

      this.produtosPorCategoria = produtos.reduce((acc, produto) => {
        const categoriaNome = produto.categoriaProduto ? produto.categoriaProduto.nome : 'Sem Categoria';
        acc[categoriaNome!] = acc[categoriaNome!] || [];
        acc[categoriaNome!].push(produto);
        return acc;
      }, {} as ProdutosPorCategoria);

      // Agora, 'produtosPorCategoria' está disponível como uma variável de classe
      console.log('Produtos Por Categoria:', this.produtosPorCategoria);
    }
  }

  getKeys(obj: any): string[] {
    return Object.keys(obj);
  }

  login(): void {
    this.router.navigate(['/login']);
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
  realizarBusca() {
    if (event) {
      event.stopPropagation();
    }
    const searchInput = document.getElementById('search-input') as HTMLInputElement;
    this.showSearchResults = true;

    if (searchInput) {
    }
  }

  openFullscreen() {
    this.modalService.open(this.cupom, { size: 'xl', windowClass: 'cupom', backdrop: 'static', keyboard: false });
  }
}
