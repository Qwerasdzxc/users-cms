import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MachineOperationErrorsComponent } from './machine-operation-errors.component';

describe('MachineOperationErrorsComponent', () => {
  let component: MachineOperationErrorsComponent;
  let fixture: ComponentFixture<MachineOperationErrorsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MachineOperationErrorsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MachineOperationErrorsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
