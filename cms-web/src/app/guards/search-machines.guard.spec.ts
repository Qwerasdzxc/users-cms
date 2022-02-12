import { TestBed } from '@angular/core/testing';

import { SearchMachinesGuard } from './search-machines.guard';

describe('SearchMachinesGuard', () => {
  let guard: SearchMachinesGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(SearchMachinesGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
