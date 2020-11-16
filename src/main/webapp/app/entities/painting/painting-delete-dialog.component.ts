import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPainting } from 'app/shared/model/painting.model';
import { PaintingService } from './painting.service';

@Component({
  templateUrl: './painting-delete-dialog.component.html',
})
export class PaintingDeleteDialogComponent {
  painting?: IPainting;

  constructor(protected paintingService: PaintingService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.paintingService.delete(id).subscribe(() => {
      this.eventManager.broadcast('paintingListModification');
      this.activeModal.close();
    });
  }
}
