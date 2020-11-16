import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ArtGalleryTestModule } from '../../../test.module';
import { PaintingUpdateComponent } from 'app/entities/painting/painting-update.component';
import { PaintingService } from 'app/entities/painting/painting.service';
import { Painting } from 'app/shared/model/painting.model';

describe('Component Tests', () => {
  describe('Painting Management Update Component', () => {
    let comp: PaintingUpdateComponent;
    let fixture: ComponentFixture<PaintingUpdateComponent>;
    let service: PaintingService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ArtGalleryTestModule],
        declarations: [PaintingUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PaintingUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PaintingUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PaintingService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Painting(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Painting();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
