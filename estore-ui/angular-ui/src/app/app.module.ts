import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';


import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ProdcutsDetailComponent } from './products-detail/products-detail.component';
import { Productsomponent } from './products/products.component';
import { ProductsSearchComponent } from './products-search/products-search.component';
import { MessagesComponent } from './messages/messages.component';

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,

    // The HttpClientInMemoryWebApiModule module intercepts HTTP requests
    // and returns simulated server responses.
    // Remove it when a real server is ready to receive requests.
  ],
  declarations: [
    AppComponent,
    DashboardComponent,
    ProductsComponent,
    ProductsDetailComponent,
    MessagesComponent,
    ProductsSearchComponent
  ],
  bootstrap: [ AppComponent ]
})
export class AppModule { }