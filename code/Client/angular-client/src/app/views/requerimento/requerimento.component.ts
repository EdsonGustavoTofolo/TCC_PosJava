import { Component, OnInit } from '@angular/core';
import {motivosRequerimentos} from "./motivos-requerimento";

@Component({
  selector: 'app-requerimento',
  templateUrl: './requerimento.component.html',
  styleUrls: ['./requerimento.component.css']
})
export class RequerimentoComponent implements OnInit {
  motivos = motivosRequerimentos;
  motivoSelected: {key: 0, value: ''};

  //Se for selecionada a opção 9 - 2ª chamada da prova
  segundaChamadaProva: {disciplina: '', professor: '', data: Date};

  //Se for selecionada a opção "5 - Cancelamento das Disciplinas" ou
  //                          "15 - Matrícula nas disciplinas" ou
  //                          "17 - Planos Ensinos/Ementas das disciplinas"
  disciplinas: string[];

  //Se for selecionada a opção "16 - Mudança para as turmas"
  turmas: string[];

  //Se for selecionado a opção "20 - Outros", apenas abre um campo texto para digitar o que quiser
  constructor() { }

  ngOnInit() {
  }

}
