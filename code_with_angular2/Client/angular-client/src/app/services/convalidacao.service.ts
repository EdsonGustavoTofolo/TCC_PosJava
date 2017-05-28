import { Injectable } from '@angular/core';
import {Convalidacao} from "../models/convalidacao";

@Injectable()
export class ConvalidacaoService {
  private convalidacao: Convalidacao;

  constructor() { }

  getConvalidacao() {
    return this.convalidacao;
  }

}
