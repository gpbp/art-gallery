import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPainting, Painting } from 'app/shared/model/painting.model';
import { PaintingService } from './painting.service';

@Component({
  selector: 'jhi-painting-update',
  templateUrl: './painting-update.component.html',
})
export class PaintingUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    author: [],
    creationDate: [],
    price: [],
  });

  constructor(protected paintingService: PaintingService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ painting }) => {
      if (!painting.id) {
        const today = moment().startOf('day');
        painting.creationDate = today;
      }

      this.updateForm(painting);
    });
  }

  updateForm(painting: IPainting): void {
    this.editForm.patchValue({
      id: painting.id,
      name: painting.name,
      author: painting.author,
      creationDate: painting.creationDate ? painting.creationDate.format(DATE_TIME_FORMAT) : null,
      price: painting.price,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const painting = this.createFromForm();
    if (painting.id !== undefined) {
      this.subscribeToSaveResponse(this.paintingService.update(painting));
    } else {
      this.subscribeToSaveResponse(this.paintingService.create(painting));
    }
  }

  private createFromForm(): IPainting {
    return {
      ...new Painting(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      author: this.editForm.get(['author'])!.value,
      creationDate: this.editForm.get(['creationDate'])!.value
        ? moment(this.editForm.get(['creationDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      price: this.editForm.get(['price'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPainting>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
