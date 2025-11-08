import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EducacionAmbientalComponent } from './educacion-ambiental';

describe('EducacionAmbiental', () => {
  let component: EducacionAmbientalComponent;
  let fixture: ComponentFixture<EducacionAmbientalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EducacionAmbientalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EducacionAmbientalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
