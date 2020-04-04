import { BrowserModule } from '@angular/platform-browser';
import {HttpClientModule} from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { FormsModule }   from '@angular/forms';

import { AppComponent } from './app.component';
import { WelcomeComponent } from './home/welcome.component';
import { ActivityComponent } from './shared_component/activitiy-progress.component';
import { SmartWatchComponent } from './smart-watch/smart-watch.component';
import { SmartPhoneComponent } from './smart-phone/smart-phone.component';
import { SmartPhoneMenuComponent } from './smart-phone-menu/smart-phone-menu.component';
import { GameStartComponent } from './game-start/game-start.component';


@NgModule({
  declarations: [
    AppComponent,
    WelcomeComponent,
    ActivityComponent,
    SmartWatchComponent,
    SmartPhoneComponent,
    SmartPhoneMenuComponent,
    GameStartComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot([
      
      {path:'welcome',component: WelcomeComponent},
      {path:'',redirectTo:'welcome',pathMatch:'full'},
      {path : 'smartPhone-menu',component:SmartPhoneMenuComponent},
      {path : 'game-start',component :GameStartComponent}
      /*{path:'**',redirectTo:'welcome', pathMatch: 'full'}*/
    ])
  
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
