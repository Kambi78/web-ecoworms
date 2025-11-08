import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NuevoProgramaComponent } from './nuevo-programa';

describe('NuevoPrograma', () => {
  let component: NuevoProgramaComponent;
  let fixture: ComponentFixture<NuevoProgramaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NuevoProgramaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NuevoProgramaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
