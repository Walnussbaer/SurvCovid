import { BrowserModule } from '@angular/platform-browser';
import {HttpClientModule} from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { WelcomeComponent } from './home/welcome.component';
import { ActivityComponent } from './shared_component/activitiy-progress.component';


@NgModule({
  declarations: [
    AppComponent,
    WelcomeComponent,
    ActivityComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot([
      
      {path:'welcome',component: WelcomeComponent},
      {path:'',redirectTo:'welcome',pathMatch:'full'},
      {path:'**',redirectTo:'welcome', pathMatch: 'full'}
    ])
  
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
