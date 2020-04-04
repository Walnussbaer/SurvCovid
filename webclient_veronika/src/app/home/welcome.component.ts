import { Component } from '@angular/core';
import { Router } from '@angular/router';
@Component({
  selector : 'welcome',
  templateUrl: './welcome.component.html',
  styleUrls : ['./welcome.component.css']
})
export class WelcomeComponent {
  public pageTitle = 'SurvCovid App';
  submitted = false;
  constructor(private router: Router){}

  onSubmit(){
    this.submitted = true;
    this.processInitialServerCommunitaion();
  }

  processInitialServerCommunitaion(){
    //this.ngZone.run(() => this.router.navigate(['/game-start']))
    //this.router.navigate(['/game-start'], {state: {"Test":"test"}});
    this.router.navigate(['/game-start']);
   alert('from game start');
  }
}
