import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddRvDialogComponent } from './add-rv-dialog.component';

describe('AddRvDialogComponent', () => {
  let component: AddRvDialogComponent;
  let fixture: ComponentFixture<AddRvDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddRvDialogComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddRvDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
