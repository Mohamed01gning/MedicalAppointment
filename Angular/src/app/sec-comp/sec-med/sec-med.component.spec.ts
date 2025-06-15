import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SecMedComponent } from './sec-med.component';

describe('SecMedComponent', () => {
  let component: SecMedComponent;
  let fixture: ComponentFixture<SecMedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SecMedComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SecMedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
