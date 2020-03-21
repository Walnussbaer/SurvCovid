import { Injectable } from '@angular/core';
import {HttpClient,HttpErrorResponse} from '@Angular/common/http';
import { Observable, throwError } from 'rxjs';
import{catchError,tap} from 'rxjs/operators';


@Injectable({
    providedIn:'root'
})

export class ArcGisService{
    private url = '';
    constructor(private http:HttpClient){}
}