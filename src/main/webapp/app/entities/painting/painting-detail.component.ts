import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPainting } from 'app/shared/model/painting.model';

@Component({
  selector: 'jhi-painting-detail',
  templateUrl: './painting-detail.component.html',
})
export class PaintingDetailComponent implements OnInit {
  painting: IPainting | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ painting }) => (this.painting = painting));
  }

  previousState(): void {
    window.history.back();
  }
}
