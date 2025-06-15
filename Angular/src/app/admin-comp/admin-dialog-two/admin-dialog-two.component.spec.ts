import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminDialogTwoComponent } from './admin-dialog-two.component';

describe('AdminDialogTwoComponent', () => {
  let component: AdminDialogTwoComponent;
  let fixture: ComponentFixture<AdminDialogTwoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminDialogTwoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminDialogTwoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
