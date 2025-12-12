import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TotalSoldComponent } from './total-sold.component';

describe('TotalSoldComponent', () => {
  let component: TotalSoldComponent;
  let fixture: ComponentFixture<TotalSoldComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TotalSoldComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TotalSoldComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
