import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { Subject } from 'rxjs';
import { map, takeUntil } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { IEstabelecimento, IEstabelecimentoProdutoDTO } from 'app/entities/estabelecimento/estabelecimento.model';
import { EstabelecimentoService } from 'app/entities/estabelecimento/service/estabelecimento.service';
import { IProduto } from 'app/entities/produto/produto.model';
import { ProdutoService } from 'app/entities/produto/service/produto.service';
import { CommonModule } from '@angular/common';

@Component({
  standalone: true,
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
  imports: [SharedModule, RouterModule, CommonModule],
})
export default class HomeComponent implements OnInit, OnDestroy {
  account: Account | null = null;

  private readonly destroy$ = new Subject<void>();
  estabelecimentoCollection?: IEstabelecimento[];
  estabelecimentoProdutoDTO?: IEstabelecimentoProdutoDTO[];
  produtoPesquisa: IProduto[] | undefined;
  estabelecimentoPesquisa: IEstabelecimento[] | undefined;

  produtoCollection?: IProduto[];

  showSearchResults = false;

  constructor(
    private accountService: AccountService,
    private router: Router,
    private estabelecimentoService: EstabelecimentoService,
    private produtoService: ProdutoService,
  ) {}

  ngOnInit(): void {
    this.accountService
      .getAuthenticationState()
      .pipe(takeUntil(this.destroy$))
      .subscribe(account => (this.account = account));

    this.estabelecimentoService.queryHome().subscribe(
      res => {
        this.estabelecimentoCollection = res.body!;
      },
      error => {
        console.error('Erro:', error);
      },
    );

    this.produtoService.queryHome().subscribe(
      res => {
        this.produtoCollection = res.body!;
      },
      error => {
        console.error('Erro:', error);
      },
    );
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

    if (searchInput) {
      this.estabelecimentoService.findSearch(searchInput.value).subscribe(
        res => {
          this.estabelecimentoProdutoDTO = res.body!;

          this.estabelecimentoPesquisa = this.estabelecimentoProdutoDTO[0].estabelecimento;

          this.produtoPesquisa = this.estabelecimentoProdutoDTO[0].produto;

          this.showSearchResults = true;
        },
        error => {
          console.error('Erro:', error);
        },
      );
    }
  }

  resetBusca() {
    this.showSearchResults = false;
  }

  navegar(estabelecimentoId: number) {
    console.log('estabelecimento:', estabelecimentoId);
    this.router.navigate(['/detalhe', estabelecimentoId]);
  }
}
