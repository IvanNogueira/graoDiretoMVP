import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { CategoriaProdutoComponent } from './list/categoria-produto.component';
import { CategoriaProdutoDetailComponent } from './detail/categoria-produto-detail.component';
import { CategoriaProdutoUpdateComponent } from './update/categoria-produto-update.component';
import CategoriaProdutoResolve from './route/categoria-produto-routing-resolve.service';

const categoriaProdutoRoute: Routes = [
  {
    path: '',
    component: CategoriaProdutoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CategoriaProdutoDetailComponent,
    resolve: {
      categoriaProduto: CategoriaProdutoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CategoriaProdutoUpdateComponent,
    resolve: {
      categoriaProduto: CategoriaProdutoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CategoriaProdutoUpdateComponent,
    resolve: {
      categoriaProduto: CategoriaProdutoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default categoriaProdutoRoute;
