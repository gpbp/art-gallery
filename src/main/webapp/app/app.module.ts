import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { ArtGallerySharedModule } from 'app/shared/shared.module';
import { ArtGalleryCoreModule } from 'app/core/core.module';
import { ArtGalleryAppRoutingModule } from './app-routing.module';
import { ArtGalleryHomeModule } from './home/home.module';
import { ArtGalleryEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    ArtGallerySharedModule,
    ArtGalleryCoreModule,
    ArtGalleryHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    ArtGalleryEntityModule,
    ArtGalleryAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class ArtGalleryAppModule {}
