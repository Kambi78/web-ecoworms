import { TestBed } from '@angular/core/testing';

import { Colaboracion } from './colaboracion';

describe('Colaboracion', () => {
  let service: Colaboracion;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Colaboracion);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
