import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminSecComponent } from './admin-sec.component';

describe('AdminSecComponent', () => {
  let component: AdminSecComponent;
  let fixture: ComponentFixture<AdminSecComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminSecComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminSecComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
