import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule }   from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RouterModule,Routes } from '@angular/router';
import { LoginPageComponent } from './pages/login-page/login-page.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http'; 
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { SciencePapersComponent } from './components/science-papers/science-papers.component';
import { LoginGuard } from './services/security/login.guard';
import { AuthGuard } from './services/security/auth.guard';
import { PutReviewerComponent } from './components/put-reviewer/put-reviewer.component';
import { JwtUtilService } from './services/security/jwt-util.service';
import { TokenInterService } from './services/security/token-inter.service';
import { MyRequestComponent } from './components/my-request/my-request.component';
import { AddAuthorReviewComponent } from './components/add-author-review/add-author-review.component';
import { ManageReviewsComponent } from './components/manage-reviews/manage-reviews.component';

const appRoutes: Routes = [
  { path: 'main', 
    component: SciencePapersComponent
  },
  { path: 'login',
    component: LoginPageComponent, 
    canActivate: [LoginGuard] 
  },
  { path: '',
    redirectTo: 'main',
    pathMatch: 'full'
  },
  {
    path: 'assignReviewer',
    component: PutReviewerComponent,
    data: {roles: ['ROLE_EDITOR']},
    canActivate: [AuthGuard]
  },
  {
    path: 'myRequests',
    component: MyRequestComponent,
    data: {roles: ['ROLE_REVIEWER']},
    canActivate: [AuthGuard]
  },
  {
    path: 'addReview',
    component: AddAuthorReviewComponent,
    data: {roles: ['ROLE_AUTHOR']},
    canActivate: [AuthGuard]
  },
  {
    path: 'manageReviews',
    component: ManageReviewsComponent,
    data: {roles: ['ROLE_EDITOR']},
    canActivate: [AuthGuard]
  }
];


@NgModule({
  declarations: [
    AppComponent,
    LoginPageComponent,
    LoginComponent,
    RegisterComponent,
    SciencePapersComponent,
    PutReviewerComponent,
    MyRequestComponent,
    AddAuthorReviewComponent,
    ManageReviewsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    RouterModule.forRoot(
      appRoutes,
      { enableTracing: true }),
    FormsModule,
    HttpClientModule
  ],
  providers: [
    AuthGuard,
    JwtUtilService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterService,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
