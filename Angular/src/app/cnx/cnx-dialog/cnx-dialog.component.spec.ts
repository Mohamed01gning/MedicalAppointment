import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CnxDialogComponent } from './cnx-dialog.component';

describe('CnxDialogComponent', () => {
  let component: CnxDialogComponent;
  let fixture: ComponentFixture<CnxDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CnxDialogComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CnxDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
