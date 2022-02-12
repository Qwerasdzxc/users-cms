import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MachinesTableComponent } from './machines-table.component';

describe('MachinesTableComponent', () => {
  let component: MachinesTableComponent;
  let fixture: ComponentFixture<MachinesTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MachinesTableComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MachinesTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
