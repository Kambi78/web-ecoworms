import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NuevoResultadoComponent } from './nuevo-resultado';

describe('NuevoResultado', () => {
  let component: NuevoResultadoComponent;
  let fixture: ComponentFixture<NuevoResultadoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NuevoResultadoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NuevoResultadoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
