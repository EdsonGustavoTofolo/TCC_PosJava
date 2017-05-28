import { Component, OnInit } from '@angular/core';
import {NgForm} from "@angular/forms";
import {Convalidacao} from "../../models/convalidacao";

@Component({
  selector: 'app-convalidacao',
  templateUrl: './convalidacao.component.html',
  styleUrls: ['./convalidacao.component.css']
})
export class ConvalidacaoComponent implements OnInit {
  convalidacao: Convalidacao;

  constructor() {
    this.convalidacao = {id: 0, aluno: '', coordenacao: '', items: []}; // inicia com vazio, pois se não, ocorre erro
  }

  ngOnInit() {

  }

  onSubmit(form: NgForm) {

  }
}
