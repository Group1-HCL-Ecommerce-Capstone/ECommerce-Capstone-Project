import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrdersCrudComponent } from './orders-crud.component';

describe('OrdersCrudComponent', () => {
  let component: OrdersCrudComponent;
  let fixture: ComponentFixture<OrdersCrudComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OrdersCrudComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OrdersCrudComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
