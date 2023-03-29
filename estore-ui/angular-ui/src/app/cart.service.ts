import { Injectable } from '@angular/core';
import { HttpClient, HttpEvent, HttpHeaders, HttpSentEvent } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { Product } from './product';
import { User } from './user';
import { MessageService } from './message.service';

@Injectable({ providedIn: 'root' })
export class CartService {

  private cartUrl = 'http://localhost:8080/cart';  // URL to web api

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient,
    private messageService: MessageService) { }

    /** PUT: add item to cart*/
    add(id: number): Observable<User> {
        this.log(`adding ${id} to url ${this.cartUrl}`);
        return this.http.put<User>(this.cartUrl, id, this.httpOptions).pipe(
            tap((user: User) => {this.log(`added product ${id} to ${user.username}'s cart`)}),
            catchError(this.handleError<User>(`addCart`))
        );
    }
    
    
    /** DELETE: add item to cart*/
    remove(id: number): Observable<User> {
      const options = {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
        body: id
      };
      this.log(`adding ${id} to url ${this.cartUrl}`);
      return this.http.delete<User>(this.cartUrl, options).pipe(
        tap((user: User) => {this.log(`removing product ${id} from ${user.username}'s cart`)}),
        catchError(this.handleError<User>(`removeCart`))
      )
    }

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
    
      /** Log a CartService message with the MessageService */
      private log(message: string) {
        this.messageService.add(`CartService: ${message}`);
      }
  }
