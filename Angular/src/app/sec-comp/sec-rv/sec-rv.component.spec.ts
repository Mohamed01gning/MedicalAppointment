import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SecRvComponent } from './sec-rv.component';

describe('SecRvComponent', () => {
  let component: SecRvComponent;
  let fixture: ComponentFixture<SecRvComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SecRvComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SecRvComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
