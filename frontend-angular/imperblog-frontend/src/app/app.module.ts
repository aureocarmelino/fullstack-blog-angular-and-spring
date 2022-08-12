import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ToastrModule } from 'ngx-toastr';
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { RouterModule } from '@angular/router';
import { JwtHelperService, JwtModule } from '@auth0/angular-jwt';
import {CardModule} from 'primeng/card';
import { HomePageComponent } from './components/home-page/home-page.component';
import {ButtonModule} from 'primeng/button';
import {ImageModule} from 'primeng/image';
import {DialogModule} from 'primeng/dialog';
import {DividerModule} from 'primeng/divider';
import {ScrollTopModule} from 'primeng/scrolltop';
import {ScrollPanelModule} from 'primeng/scrollpanel';
import {AvatarModule} from 'primeng/avatar';
import {SpeedDialModule} from 'primeng/speeddial';
import { ChipModule } from 'primeng/chip';
import { ProfileComponent } from './components/profile/profile.component';
import { HeaderProfileComponent } from './components/header-profile/header-profile.component';
import {EditorModule} from 'primeng/editor';
import {InputTextModule} from 'primeng/inputtext';
import {TooltipModule} from 'primeng/tooltip';
import { ImperBlogHttpInterceptor } from './interceptor/imper-blog-http-interceptor';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { MessageService, ConfirmationService } from 'primeng/api';
import { AuthService } from './services/auth.service';
import {ChartModule} from 'primeng/chart';
import {RadioButtonModule} from 'primeng/radiobutton';
import { environment } from 'src/environments/environment';




export function tokenGetter(): string
{
  return localStorage.getItem('token')!;
}


@NgModule({
  declarations:
  [
    AppComponent,
    HeaderComponent,
    LoginComponent,
    RegisterComponent,
    HomePageComponent,
    ProfileComponent,
    HeaderProfileComponent,
    PageNotFoundComponent
  ],
  imports:
  [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    ToastrModule.forRoot(),
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    CardModule,
    ButtonModule,
    ImageModule,
    DialogModule,
    DividerModule,
    ScrollTopModule,
    ScrollPanelModule,
    AvatarModule,
    SpeedDialModule,
    ChipModule,
    RadioButtonModule,
    EditorModule,
    InputTextModule,
    ChartModule,
    TooltipModule,
    JwtModule.forRoot({
      config:
      {
        tokenGetter,
        allowedDomains: environment.tokenAllowedDomains,
        disallowedRoutes: environment.disallowedRoutes
      }
    })
  ],
  providers:
  [
    AuthService,
    MessageService,
    ConfirmationService,

    JwtHelperService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ImperBlogHttpInterceptor,
      multi: true,
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
