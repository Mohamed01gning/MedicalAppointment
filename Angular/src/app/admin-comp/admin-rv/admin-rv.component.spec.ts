import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminRvComponent } from './admin-rv.component';

describe('AdminRvComponent', () => {
  let component: AdminRvComponent;
  let fixture: ComponentFixture<AdminRvComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminRvComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminRvComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
