import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CrearUsuarioComponent } from './crear-usuario';

describe('CrearUsuario', () => {
  let component: CrearUsuarioComponent;
  let fixture: ComponentFixture<CrearUsuarioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CrearUsuarioComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CrearUsuarioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
