import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MedRvComponent } from './med-rv.component';

describe('MedRvComponent', () => {
  let component: MedRvComponent;
  let fixture: ComponentFixture<MedRvComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MedRvComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MedRvComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
