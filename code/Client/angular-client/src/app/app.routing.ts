import { RouterModule, Routes } from '@angular/router';
import {ConvalidacaoComponent} from "./views/convalidacao/convalidacao.component";
import {RequerimentoComponent} from "./views/requerimento/requerimento.component";

const APP_ROUTES: Routes = [
  {path: '', redirectTo: '/', pathMatch: 'full'},
  {path: 'convalidacao', component: ConvalidacaoComponent},
  {path: 'requerimento', component: RequerimentoComponent}
];

export const routing = RouterModule.forRoot(APP_ROUTES);
