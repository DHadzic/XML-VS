import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PutReviewerComponent } from './put-reviewer.component';

describe('PutReviewerComponent', () => {
  let component: PutReviewerComponent;
  let fixture: ComponentFixture<PutReviewerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PutReviewerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PutReviewerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
