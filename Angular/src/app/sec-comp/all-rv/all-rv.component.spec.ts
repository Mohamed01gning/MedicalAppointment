import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllRvComponent } from './all-rv.component';

describe('AllRvComponent', () => {
  let component: AllRvComponent;
  let fixture: ComponentFixture<AllRvComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AllRvComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AllRvComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
