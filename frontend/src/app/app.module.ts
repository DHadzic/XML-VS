import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule }   from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RouterModule,Routes } from '@angular/router';
import { LoginPageComponent } from './pages/login-page/login-page.component';
import { HttpClientModule } from '@angular/common/http'; 
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { SciencePapersComponent } from './components/science-papers/science-papers.component';
import { SetReviewersComponent } from './components/set-reviewers/set-reviewers.component';
import { LoginGuard } from './services/security/login.guard';
import { AuthGuard } from './services/security/auth.guard';

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
    path: 'setReviewers',
    component: SetReviewersComponent,
    data: {roles: ['EDITOR_ROLE']},
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
    SetReviewersComponent
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
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
