import { TestBed } from '@angular/core/testing';

import { Biodegradacion } from './biodegradacion';

describe('Biodegradacion', () => {
  let service: Biodegradacion;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Biodegradacion);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
