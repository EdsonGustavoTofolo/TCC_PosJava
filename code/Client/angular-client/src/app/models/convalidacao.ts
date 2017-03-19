import {ConvalidacaoItem} from "./convalidacao-item";
export class Convalidacao {
  constructor(public id?: number,
              public codigo?: number,
              public numero?: number,
              public aluno?: string,
              public coordenacao?: string,
              public items?: ConvalidacaoItem[]) {}
}
