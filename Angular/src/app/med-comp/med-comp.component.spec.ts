import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MedCompComponent } from './med-comp.component';

describe('MedCompComponent', () => {
  let component: MedCompComponent;
  let fixture: ComponentFixture<MedCompComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MedCompComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MedCompComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
