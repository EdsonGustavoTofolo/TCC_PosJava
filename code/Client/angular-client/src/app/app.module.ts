import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {FormsModule} from '@angular/forms';
import { HttpModule } from '@angular/http';
import {MaterialModule} from "@angular/material";
import { AppComponent } from './app.component';
import { NavComponent } from './views/common/nav/nav.component';
import { FooterComponent } from './views/common/footer/footer.component';
import { ConvalidacaoComponent } from './views/convalidacao/convalidacao.component';
import {routing} from "./app.routing";
import 'hammerjs';
import { ConvalidacaoItemsComponent } from './views/convalidacao/convalidacao-items/convalidacao-items.component';

@NgModule({
  declarations: [
    AppComponent,
    NavComponent,
    FooterComponent,
    ConvalidacaoComponent,
    ConvalidacaoItemsComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    MaterialModule,
    routing
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
