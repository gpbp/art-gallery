import { NgModule } from '@angular/core';
import { ArtGallerySharedLibsModule } from './shared-libs.module';
import { AlertComponent } from './alert/alert.component';
import { AlertErrorComponent } from './alert/alert-error.component';
import { HasAnyAuthorityDirective } from './auth/has-any-authority.directive';

@NgModule({
  imports: [ArtGallerySharedLibsModule],
  declarations: [AlertComponent, AlertErrorComponent, HasAnyAuthorityDirective],
  exports: [ArtGallerySharedLibsModule, AlertComponent, AlertErrorComponent, HasAnyAuthorityDirective],
})
export class ArtGallerySharedModule {}
