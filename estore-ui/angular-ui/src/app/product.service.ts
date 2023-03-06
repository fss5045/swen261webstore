import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { Products } from './product';
import { MessageService } from './message.service';


@Injectable({ providedIn: 'root' })
export class ProductsService {

  private productsUrl = 'http://localhost:8080/products';  // URL to web api

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient,
    private messageService: MessageService) { }

  /** GET product from the server */
  getProducts(): Observable<Products[]> {
    return this.http.get<Products[]>(this.productsUrl)
      .pipe(
        tap(_ => this.log('fetched products')),
        catchError(this.handleError<Products[]>('getProducts', []))
      );
  }

  /** GET product by id. Return `undefined` when id not found */
  getProductNo404<Data>(id: number): Observable<Products> {
    const url = `${this.productsUrl}/?id=${id}`;
    return this.http.get<Products[]>(url)
      .pipe(
        map(products => products[0]), // returns a {0|1} element array
        tap(h => {
          const outcome = h ? 'fetched' : 'did not find';
          this.log(`${outcome} product id=${id}`);
        }),
        catchError(this.handleError<Products>(`getProducts id=${id}`))
      );
  }

  /** GET product by id. Will 404 if id not found */
  getProduct(id: number): Observable<Products> {
    const url = `${this.productsUrl}/${id}`;
    return this.http.get<Products>(url).pipe(
      tap(_ => this.log(`fetched product id=${id}`)),
      catchError(this.handleError<Products>(`getProduct id=${id}`))
    );
  }

  /* GET products whose name contains search term */
  searchProducts(term: string): Observable<Products[]> {
    if (!term.trim()) {
      // if not search term, return empty hero array.
      return of([]);
    }
    return this.http.get<Products[]>(`${this.productsUrl}/?name=${term}`).pipe(
      tap(x => x.length ?
         this.log(`found products matching "${term}"`) :
         this.log(`no products matching "${term}"`)),
      catchError(this.handleError<Products[]>('searchProducts', []))
    );
  }

  //////// Save methods //////////

  /** POST: add a new product to the server */
  addProduct(product: Products): Observable<Products> {
    return this.http.post<Products>(this.productsUrl, product, this.httpOptions).pipe(
      tap((newProducts: Products) => this.log(`added product w/ id=${newProducts.id}`)),
      catchError(this.handleError<Products>('addProducts'))
    );
  }

  /** DELETE: delete the product from the server */
  deleteProduct(id: number): Observable<Products> {
    const url = `${this.productsUrl}/${id}`;

    return this.http.delete<Products>(url, this.httpOptions).pipe(
      tap(_ => this.log(`deleted product id=${id}`)),
      catchError(this.handleError<Products>('deleteProduct'))
    );
  }

  /** PUT: update the product on the server */
  updateProduct(product: Products): Observable<any> {
    return this.http.put(this.productsUrl, product, this.httpOptions).pipe(
      tap(_ => this.log(`updated product id=${product.id}`)),
      catchError(this.handleError<any>('updateProduct'))
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

  /** Log a ProductService message with the MessageService */
  private log(message: string) {
    this.messageService.add(`ProductService: ${message}`);
  }
}