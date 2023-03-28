import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpClientModule, HttpResponse } from '@angular/common/http';

import { Observable, of, pipe } from 'rxjs';
import { catchError, map, subscribeOn, tap } from 'rxjs/operators';

import { User } from './user';
import { UserType } from './user.type';

import { MessageService } from './message.service';


@Injectable({ providedIn: 'root' })
export class LoginService {
  private url = 'http://localhost:8080';  // URL to web api
  UserType = UserType;
  currentUser: User | undefined;

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient,
    private messageService: MessageService) {
      this.getCurrentUser().subscribe(current => this.currentUser = current);
    }
    
    login(username: string): Observable<User> {
      const url = `${this.url}/login`;
      return this.http.post<User>(url, username, this.httpOptions).pipe(
        tap((user: User) => this.log(`logged in w/ username =${user.username}`)),
        catchError(this.handleError<User>('login'))
        );
    }

    logout(): Observable<boolean> {
      const url = `${this.url}/logout`;
      return this.http.patch<boolean>(url, this.httpOptions).pipe(
        tap((status: boolean) => this.log(`logged out w/ status =${status}`)),
        catchError(this.handleError<boolean>('logout'))
        );
    }

    getCurrentUser(): Observable<User> {
      const url = `${this.url}/current`;
      return this.http.get<User>(url).pipe( 
        tap((user: User) => this.log(`current user =${user.username}, usertype =${user.userType}`)),
        catchError(this.handleError<User>('current user'))
        );
    }

    isAdmin(): Observable<boolean> {
      // return of(true).pipe(
      // tap((status: boolean) => this.log(`isAdmin =${status}`))
      // );
      // this.log(`value  = ${UserType.Admin.valueOf()}, ${UserType.Customer.valueOf()}`)
      // this.log(`value 1 = ${JSON.stringify(UserType.Admin) === JSON.stringify(UserType.Customer)}`)
      // this.log(`value 2 = ${UserType.Admin.valueOf() === UserType.Customer.valueOf()}`)
      return of(this.currentUser?.userType.valueOf() === UserType.Admin.valueOf()).pipe(
        tap((status: boolean) => this.log(`status =${status}, currentUsertype =${this.currentUser?.userType}`)),
        catchError(this.handleError<boolean>('isAdmin'))
      )
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
