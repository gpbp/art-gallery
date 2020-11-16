import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPainting } from 'app/shared/model/painting.model';

type EntityResponseType = HttpResponse<IPainting>;
type EntityArrayResponseType = HttpResponse<IPainting[]>;

@Injectable({ providedIn: 'root' })
export class PaintingService {
  public resourceUrl = SERVER_API_URL + 'api/paintings';

  constructor(protected http: HttpClient) {}

  create(painting: IPainting): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(painting);
    return this.http
      .post<IPainting>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(painting: IPainting): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(painting);
    return this.http
      .put<IPainting>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPainting>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPainting[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(painting: IPainting): IPainting {
    const copy: IPainting = Object.assign({}, painting, {
      creationDate: painting.creationDate && painting.creationDate.isValid() ? painting.creationDate.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.creationDate = res.body.creationDate ? moment(res.body.creationDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((painting: IPainting) => {
        painting.creationDate = painting.creationDate ? moment(painting.creationDate) : undefined;
      });
    }
    return res;
  }
}
