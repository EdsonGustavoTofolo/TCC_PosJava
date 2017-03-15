import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { AppComponent } from './app.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { NavComponent } from './views/common/nav/nav.component';
import { FooterComponent } from './views/common/footer/footer.component';
import { ConvalidacaoComponent } from './views/convalidacao/convalidacao.component';
import {routing} from "./app.routing";

@NgModule({
  declarations: [
    AppComponent,
    NavComponent,
    FooterComponent,
    ConvalidacaoComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    NgbModule.forRoot(),
    routing
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
