import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminMedComponent } from './admin-med.component';

describe('AdminMedComponent', () => {
  let component: AdminMedComponent;
  let fixture: ComponentFixture<AdminMedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminMedComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminMedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
