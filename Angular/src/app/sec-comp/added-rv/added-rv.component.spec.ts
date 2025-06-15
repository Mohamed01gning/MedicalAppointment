import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddedRvComponent } from './added-rv.component';

describe('AddedRvComponent', () => {
  let component: AddedRvComponent;
  let fixture: ComponentFixture<AddedRvComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddedRvComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddedRvComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
