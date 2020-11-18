import { Moment } from 'moment';

export interface IPainting {
  id?: number;
  name?: string;
  author?: string;
  creationDate?: Moment;
  price?: number;
  imageUrl?: string;
}

export class Painting implements IPainting {
  constructor(
    public id?: number,
    public name?: string,
    public author?: string,
    public creationDate?: Moment,
    public price?: number,
    public imageUrl?: string
  ) {}
}
