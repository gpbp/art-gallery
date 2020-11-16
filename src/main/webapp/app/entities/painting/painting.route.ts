import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPainting, Painting } from 'app/shared/model/painting.model';
import { PaintingService } from './painting.service';
import { PaintingComponent } from './painting.component';
import { PaintingDetailComponent } from './painting-detail.component';
import { PaintingUpdateComponent } from './painting-update.component';

@Injectable({ providedIn: 'root' })
export class PaintingResolve implements Resolve<IPainting> {
  constructor(private service: PaintingService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPainting> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((painting: HttpResponse<Painting>) => {
          if (painting.body) {
            return of(painting.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Painting());
  }
}

export const paintingRoute: Routes = [
  {
    path: '',
    component: PaintingComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Paintings',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PaintingDetailComponent,
    resolve: {
      painting: PaintingResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Paintings',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PaintingUpdateComponent,
    resolve: {
      painting: PaintingResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Paintings',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PaintingUpdateComponent,
    resolve: {
      painting: PaintingResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Paintings',
    },
    canActivate: [UserRouteAccessService],
  },
];
