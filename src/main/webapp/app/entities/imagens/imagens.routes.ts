import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ImagensComponent } from './list/imagens.component';
import { ImagensDetailComponent } from './detail/imagens-detail.component';
import { ImagensUpdateComponent } from './update/imagens-update.component';
import ImagensResolve from './route/imagens-routing-resolve.service';

const imagensRoute: Routes = [
  {
    path: '',
    component: ImagensComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ImagensDetailComponent,
    resolve: {
      imagens: ImagensResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ImagensUpdateComponent,
    resolve: {
      imagens: ImagensResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ImagensUpdateComponent,
    resolve: {
      imagens: ImagensResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default imagensRoute;
