import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddRvComponent } from './add-rv.component';

describe('AddRvComponent', () => {
  let component: AddRvComponent;
  let fixture: ComponentFixture<AddRvComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddRvComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddRvComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
