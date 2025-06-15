import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MedAllRvComponent } from './med-all-rv.component';

describe('MedAllRvComponent', () => {
  let component: MedAllRvComponent;
  let fixture: ComponentFixture<MedAllRvComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MedAllRvComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MedAllRvComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
