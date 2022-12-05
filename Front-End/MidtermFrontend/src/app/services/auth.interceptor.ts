import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Inject, Injectable } from "@angular/core";
import { OKTA_AUTH } from "@okta/okta-angular";
import OktaAuth from "@okta/okta-auth-js";
import { Observable } from "rxjs";

@Injectable()
export class AuthInterceptor implements HttpInterceptor{

    constructor(@Inject(OKTA_AUTH) private _oktaAuth: OktaAuth){}

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return this.handleAccess(req, next);
        
    }

    private handleAccess(req: HttpRequest<any>, next: HttpHandler):Observable<HttpEvent<any>>{
        const allowedOrigins = ['http://localhost'];
        if (allowedOrigins.some(url => req.urlWithParams.includes(url))){
            const accessToken = this._oktaAuth.getAccessToken();
            //console.log(accessToken);
            req = req.clone({
                setHeaders: {
                    Authorization: 'Bearer '+accessToken
                }
                //headers: req.headers.append('Access-Control-Allow-Origin', 'http://localhost:8181/')
            });
        }
        
        return next.handle(req);
    }
    
}