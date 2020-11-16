import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'painting',
        loadChildren: () => import('./painting/painting.module').then(m => m.ArtGalleryPaintingModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class ArtGalleryEntityModule {}
