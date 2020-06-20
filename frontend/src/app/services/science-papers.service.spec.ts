import { TestBed } from '@angular/core/testing';

import { SciencePapersService } from './science-papers.service';

describe('SciencePapersService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: SciencePapersService = TestBed.get(SciencePapersService);
    expect(service).toBeTruthy();
  });
});
