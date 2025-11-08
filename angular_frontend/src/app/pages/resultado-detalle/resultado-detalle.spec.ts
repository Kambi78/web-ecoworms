import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResultadoDetalleComponent } from './resultado-detalle';

describe('ResultadoDetalle', () => {
  let component: ResultadoDetalleComponent;
  let fixture: ComponentFixture<ResultadoDetalleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ResultadoDetalleComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ResultadoDetalleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
