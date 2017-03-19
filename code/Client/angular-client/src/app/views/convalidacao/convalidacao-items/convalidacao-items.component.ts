import {Component, OnInit, Input} from '@angular/core';
import {Convalidacao} from "../../../models/convalidacao";
import {ConvalidacaoItem} from "../../../models/convalidacao-item";

@Component({
  selector: 'app-convalidacao-items',
  templateUrl: './convalidacao-items.component.html',
  styleUrls: ['./convalidacao-items.component.css']
})
export class ConvalidacaoItemsComponent implements OnInit {
  @Input()
  convalidacao: Convalidacao;

  convalidacaoItem: ConvalidacaoItem;
  disciplina: string;
  icon = 'add';
  toAdd = false;

  constructor() {
    this.convalidacaoItem = {id: 0, codigo: '', disciplina: '', disciplinas: []};
    this.disciplina = '';
  }

  ngOnInit() {
  }

  addDisciplinaNoItem() {
    if (this.convalidacao.id == 0) {
      this.convalidacao.id = 1;
    }
    if (this.convalidacaoItem.id == 0) {
      this.convalidacaoItem.id = this.convalidacao.items.length + 1;
      this.icon = 'save';
      this.toAdd = true;
    } else {
      this.convalidacao.items.push(this.convalidacaoItem);
      console.log(this.convalidacao);
      this.convalidacaoItem = {id: 0, codigo: '', disciplina: '', disciplinas: []};
      this.disciplina = '';
      this.icon = 'add';
      this.toAdd = false;
    }
  }

  addDisciplina() {
    this.convalidacaoItem.disciplinas.push(this.disciplina);
    console.log(this.convalidacaoItem.disciplinas);
    this.disciplina = '';
  }
}
