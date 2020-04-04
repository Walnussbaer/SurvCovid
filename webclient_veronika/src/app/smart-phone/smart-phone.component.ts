import { Component } from "@angular/core";
import { Router } from '@angular/router';

@Component({
    selector : 'smart-phone',
    templateUrl : './smart-phone.component.html',
    styleUrls : ['./smart-phone.component.css']
})

export class SmartPhoneComponent{
    constructor(private router: Router) {}
    onMenuButtonClick(event){
        alert("Menu button clicked");
        this.router.navigate(['/smartPhone-menu']);

    }
}