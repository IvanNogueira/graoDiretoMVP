import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { CardapioComponent } from './list/cardapio.component';
import { CardapioDetailComponent } from './detail/cardapio-detail.component';
import { CardapioUpdateComponent } from './update/cardapio-update.component';
import CardapioResolve from './route/cardapio-routing-resolve.service';

const cardapioRoute: Routes = [
  {
    path: '',
    component: CardapioComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CardapioDetailComponent,
    resolve: {
      cardapio: CardapioResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CardapioUpdateComponent,
    resolve: {
      cardapio: CardapioResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CardapioUpdateComponent,
    resolve: {
      cardapio: CardapioResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default cardapioRoute;
