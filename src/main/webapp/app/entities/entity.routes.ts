import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'estabelecimento',
    data: { pageTitle: 'graoDiretoMvpApp.estabelecimento.home.title' },
    loadChildren: () => import('./estabelecimento/estabelecimento.routes'),
  },
  {
    path: 'cupom-desconto',
    data: { pageTitle: 'graoDiretoMvpApp.cupomDesconto.home.title' },
    loadChildren: () => import('./cupom-desconto/cupom-desconto.routes'),
  },
  {
    path: 'produto',
    data: { pageTitle: 'graoDiretoMvpApp.produto.home.title' },
    loadChildren: () => import('./produto/produto.routes'),
  },
  {
    path: 'categoria-produto',
    data: { pageTitle: 'graoDiretoMvpApp.categoriaProduto.home.title' },
    loadChildren: () => import('./categoria-produto/categoria-produto.routes'),
  },
  {
    path: 'cardapio',
    data: { pageTitle: 'graoDiretoMvpApp.cardapio.home.title' },
    loadChildren: () => import('./cardapio/cardapio.routes'),
  },
  {
    path: 'estado',
    data: { pageTitle: 'graoDiretoMvpApp.estado.home.title' },
    loadChildren: () => import('./estado/estado.routes'),
  },
  {
    path: 'cidade',
    data: { pageTitle: 'graoDiretoMvpApp.cidade.home.title' },
    loadChildren: () => import('./cidade/cidade.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
