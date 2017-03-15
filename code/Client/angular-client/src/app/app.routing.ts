import { RouterModule, Routes } from '@angular/router';
import {ConvalidacaoComponent} from "./views/convalidacao/convalidacao.component";

const APP_ROUTES: Routes = [
  {path: '', redirectTo: '/', pathMatch: 'full'},
  {path: 'convalidacao', component: ConvalidacaoComponent}
];

export const routing = RouterModule.forRoot(APP_ROUTES);
