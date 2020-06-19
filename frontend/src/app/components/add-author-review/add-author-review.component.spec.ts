import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddAuthorReviewComponent } from './add-author-review.component';

describe('AddAuthorReviewComponent', () => {
  let component: AddAuthorReviewComponent;
  let fixture: ComponentFixture<AddAuthorReviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddAuthorReviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddAuthorReviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
