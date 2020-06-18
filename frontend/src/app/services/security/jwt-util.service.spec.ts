import { TestBed } from '@angular/core/testing';

import { JwtUtilService } from './jwt-util.service';

describe('JwtUtilService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: JwtUtilService = TestBed.get(JwtUtilService);
    expect(service).toBeTruthy();
  });
});
