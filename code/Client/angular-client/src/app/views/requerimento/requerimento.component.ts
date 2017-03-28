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

  constructor() { }

  ngOnInit() {
  }

}
