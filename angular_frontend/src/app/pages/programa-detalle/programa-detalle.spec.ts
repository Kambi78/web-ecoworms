import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProgramaDetalleComponent } from './programa-detalle';

describe('ProgramaDetalle', () => {
  let component: ProgramaDetalleComponent;
  let fixture: ComponentFixture<ProgramaDetalleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProgramaDetalleComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProgramaDetalleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
