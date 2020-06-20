import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SciencePaperComponent } from './science-paper.component';

describe('SciencePaperComponent', () => {
  let component: SciencePaperComponent;
  let fixture: ComponentFixture<SciencePaperComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SciencePaperComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SciencePaperComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
