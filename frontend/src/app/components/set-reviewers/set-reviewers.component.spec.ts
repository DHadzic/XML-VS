import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SetReviewersComponent } from './set-reviewers.component';

describe('SetReviewersComponent', () => {
  let component: SetReviewersComponent;
  let fixture: ComponentFixture<SetReviewersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SetReviewersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SetReviewersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
