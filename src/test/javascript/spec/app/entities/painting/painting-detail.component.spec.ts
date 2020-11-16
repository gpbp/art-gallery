import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ArtGalleryTestModule } from '../../../test.module';
import { PaintingDetailComponent } from 'app/entities/painting/painting-detail.component';
import { Painting } from 'app/shared/model/painting.model';

describe('Component Tests', () => {
  describe('Painting Management Detail Component', () => {
    let comp: PaintingDetailComponent;
    let fixture: ComponentFixture<PaintingDetailComponent>;
    const route = ({ data: of({ painting: new Painting(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ArtGalleryTestModule],
        declarations: [PaintingDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PaintingDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PaintingDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load painting on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.painting).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
