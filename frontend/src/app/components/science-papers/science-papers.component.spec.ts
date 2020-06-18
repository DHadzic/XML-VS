import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SciencePapersComponent } from './science-papers.component';

describe('SciencePapersComponent', () => {
  let component: SciencePapersComponent;
  let fixture: ComponentFixture<SciencePapersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SciencePapersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SciencePapersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
