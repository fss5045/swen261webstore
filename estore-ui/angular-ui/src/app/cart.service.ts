import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { Product } from './product';
import { User } from './user';
import { MessageService } from './message.service';

@Injectable({ providedIn: 'root' })
export class CartService {

  private url = 'http://localhost:8080';  // URL to web api

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })

  };

  constructor(
    private http: HttpClient,
    private messageService: MessageService) { }

    /** PUT: add item to cart*/
    add(id: number): Observable<any> {
        const url = `${this.url}/cart`;
        this.log(`adding ${id} to url ${url}`);
        return this.http.put<User>(url, id, this.httpOptions).pipe(
            tap((user: User) => {
                this.log(`added product ${id}, new quantity: ${user.cart[id]}`);
            }),
            catchError(this.handleError<User>(`addCart`))
        );
    }
    
    
    /** DELETE: add item to cart*/
    // remove(id: number): Observable<User> {
    //     return this.http.delete<User>(this.cartUrl,id).pipe(
    //         tap((user: User) => this.log(`removed ${id} to cart of ${user.username}`)),
    //         catchError(this.handleError<User>('addToCart'))
    //     );
    // }

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
