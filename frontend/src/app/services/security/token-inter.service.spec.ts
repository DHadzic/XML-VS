import { TestBed } from '@angular/core/testing';

import { TokenInterService } from './token-inter.service';

describe('TokenInterService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: TokenInterService = TestBed.get(TokenInterService);
    expect(service).toBeTruthy();
  });
});
