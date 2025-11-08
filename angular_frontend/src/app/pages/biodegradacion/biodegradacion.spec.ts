import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BiodegradacionComponent } from './biodegradacion';

describe('Biodegradacion', () => {
  let component: BiodegradacionComponent;
  let fixture: ComponentFixture<BiodegradacionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BiodegradacionComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BiodegradacionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
