import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { CupomDescontoComponent } from './list/cupom-desconto.component';
import { CupomDescontoDetailComponent } from './detail/cupom-desconto-detail.component';
import { CupomDescontoUpdateComponent } from './update/cupom-desconto-update.component';
import CupomDescontoResolve from './route/cupom-desconto-routing-resolve.service';

const cupomDescontoRoute: Routes = [
  {
    path: '',
    component: CupomDescontoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CupomDescontoDetailComponent,
    resolve: {
      cupomDesconto: CupomDescontoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CupomDescontoUpdateComponent,
    resolve: {
      cupomDesconto: CupomDescontoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CupomDescontoUpdateComponent,
    resolve: {
      cupomDesconto: CupomDescontoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default cupomDescontoRoute;
