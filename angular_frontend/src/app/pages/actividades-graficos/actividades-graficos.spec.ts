import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActividadesGraficosComponent } from './actividades-graficos';

describe('ActividadesGraficos', () => {
  let component: ActividadesGraficosComponent;
  let fixture: ComponentFixture<ActividadesGraficosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ActividadesGraficosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ActividadesGraficosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
