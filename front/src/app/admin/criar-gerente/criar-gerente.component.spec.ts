import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CriarGerenteComponent } from './criar-gerente.component';

describe('CriarGerenteComponent', () => {
  let component: CriarGerenteComponent;
  let fixture: ComponentFixture<CriarGerenteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CriarGerenteComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CriarGerenteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
