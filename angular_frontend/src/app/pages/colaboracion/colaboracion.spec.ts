import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ColaboracionComponent } from './colaboracion';

describe('Colaboracion', () => {
  let component: ColaboracionComponent;
  let fixture: ComponentFixture<ColaboracionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ColaboracionComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ColaboracionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
