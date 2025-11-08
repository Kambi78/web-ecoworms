import { TestBed } from '@angular/core/testing';

import { Recycling } from './recycling';

describe('Recycling', () => {
  let service: Recycling;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Recycling);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
