import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
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
      .subscribe(account => ((this.account = account), console.log(this.account)));

    this.estabelecimentoService.query().subscribe(
      res => {
        this.estabelecimentoCollection = res.body!;
      },
      error => {
        console.error('Erro:', error);
      },
    );

    this.produtoService.query().subscribe(
      res => {
        this.produtoCollection = res.body!;
        console.log(this.produtoCollection);
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
    this.showSearchResults = true;

    if (searchInput) {
    }
  }
  navegar(estabelecimentoId: number) {
    console.log('estabelecimento:', estabelecimentoId);
    this.router.navigate(['/detalhe', estabelecimentoId]);
  }
}
