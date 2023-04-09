import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { Discount } from './discount';
import { MessageService } from './message.service';

@Injectable({ providedIn: 'root' })
export class DiscountService {
  private discountsUrl = 'http://localhost:8080/discounts';  // URL to web api

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient,
    private messageService: MessageService) { }

  /** GET discount by name. Will 404 if name not found */
  getDiscount(name: string): Observable<Discount> {
    const url = `${this.discountsUrl}/${name}`;
    return this.http.get<Discount>(url).pipe(
      tap(_ => this.log(`fetched discount: ${name}`)),
      catchError(this.handleError<Discount>(`getDiscount: ${name}`))
    );
  }

  /** GET discounts from the server */
  getDiscounts(): Observable<Discount[]> {
    return this.http.get<Discount[]>(this.discountsUrl)
      .pipe(
        tap(_ => this.log('fetched discounts')),
        catchError(this.handleError<Discount[]>('getDiscounts', []))
      );
  }
  
  /** POST: add a new discount to the server */
  addDiscount(discount: Discount): Observable<Discount> {
    return this.http.post<Discount>(this.discountsUrl, discount, this.httpOptions).pipe(
      tap((newDiscounts: Discount) => this.log(`added discount w/ name=${newDiscounts.name}`)),
      catchError(this.handleError<Discount>('addDiscounts'))
    );
  }

  /** PUT: update the discount on the server */
  updateDiscount(discount: Discount): Observable<any> {
    return this.http.put(this.discountsUrl, discount, this.httpOptions).pipe(
      tap(_ => this.log(`updated discount name=${discount.name}`)),
      catchError(this.handleError<any>('updateDiscount'))
    );
  }

  /** DELETE: delete the discount from the server */
  deleteDiscount(name: string): Observable<Discount> {
    const url = `${this.discountsUrl}/${name}`;
    return this.http.delete<Discount>(url, this.httpOptions).pipe(
      tap(_ => this.log(`deleted discount name=${name}`)),
      catchError(this.handleError<Discount>('deleteDiscount'))
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

  /** Log a DiscountService message with the MessageService */
  private log(message: string) {
    this.messageService.add(`DiscountService: ${message}`);
  }
}
