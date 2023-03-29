import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpClientModule, HttpResponse } from '@angular/common/http';

import { Observable, of, pipe, Subscription } from 'rxjs';
import { catchError, map, subscribeOn, tap } from 'rxjs/operators';

import { User } from './user';
import { UserType } from './user.type';

import { MessageService } from './message.service';
import { JsonPipe } from '@angular/common';


@Injectable({ providedIn: 'root' })
export class LoginService {
  private url = 'http://localhost:8080';  // URL to web api

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient,
    private messageService: MessageService) {}
    
    /** POST: login */
    login(username: string): Observable<User> {
      // this.log(`logging in`);
      const url = `${this.url}/login`;
      return this.http.post<User>(url, username, this.httpOptions).pipe(
        tap((user: User) => this.log(`logged in w/ username =${user.username}`)),
        catchError(this.handleError<User>('login'))
        );
    }

    /** PATCH: logout */
    logout(): Observable<boolean> {
      const url = `${this.url}/logout`;
      // this.log(`logging out`);
      return this.http.patch<boolean>(url, null).pipe(
        tap((status: boolean) => this.log(`logged out w/ status =${status}`)),
        catchError(this.handleError<boolean>('logout'))
        );
    }
    /** GET: get the current(logged in) user */
    getCurrentUser(): Observable<User> {
      const url = `${this.url}/current`;
      return this.http.get<User>(url).pipe( 
        tap((user: User) => this.log(`current user =${user.username}, usertype =${user.userType}`)),
        catchError(this.handleError<User>('current user'))
        );
    }

    /**
   * Handle Http operation that failed.
   * Let the app continue.
   *
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
        console.error(error); // log to console instead

        // TODO: better job of transforming error for user consumption
        this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  /** Log a LoginService message with the MessageService */
  private log(message: string) {
    this.messageService.add(`LoginService: ${message}`);
  }
}
