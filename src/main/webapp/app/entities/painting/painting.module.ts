import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ArtGallerySharedModule } from 'app/shared/shared.module';
import { PaintingComponent } from './painting.component';
import { PaintingDetailComponent } from './painting-detail.component';
import { PaintingUpdateComponent } from './painting-update.component';
import { PaintingDeleteDialogComponent } from './painting-delete-dialog.component';
import { paintingRoute } from './painting.route';

@NgModule({
  imports: [ArtGallerySharedModule, RouterModule.forChild(paintingRoute)],
  declarations: [PaintingComponent, PaintingDetailComponent, PaintingUpdateComponent, PaintingDeleteDialogComponent],
  entryComponents: [PaintingDeleteDialogComponent],
})
export class ArtGalleryPaintingModule {}
