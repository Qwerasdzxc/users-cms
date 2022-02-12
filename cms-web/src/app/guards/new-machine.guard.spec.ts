import { TestBed } from '@angular/core/testing';

import { NewMachineGuard } from './new-machine.guard';

describe('NewMachineGuard', () => {
  let guard: NewMachineGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(NewMachineGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
