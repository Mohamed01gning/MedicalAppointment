import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SecDialogComponent } from './sec-dialog.component';

describe('SecDialogComponent', () => {
  let component: SecDialogComponent;
  let fixture: ComponentFixture<SecDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SecDialogComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SecDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
