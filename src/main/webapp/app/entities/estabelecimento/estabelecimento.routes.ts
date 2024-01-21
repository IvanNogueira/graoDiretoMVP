import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { EstabelecimentoComponent } from './list/estabelecimento.component';
import { EstabelecimentoDetailComponent } from './detail/estabelecimento-detail.component';
import { EstabelecimentoUpdateComponent } from './update/estabelecimento-update.component';
import EstabelecimentoResolve from './route/estabelecimento-routing-resolve.service';

const estabelecimentoRoute: Routes = [
  {
    path: '',
    component: EstabelecimentoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EstabelecimentoDetailComponent,
    resolve: {
      estabelecimento: EstabelecimentoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EstabelecimentoUpdateComponent,
    resolve: {
      estabelecimento: EstabelecimentoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EstabelecimentoUpdateComponent,
    resolve: {
      estabelecimento: EstabelecimentoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default estabelecimentoRoute;
