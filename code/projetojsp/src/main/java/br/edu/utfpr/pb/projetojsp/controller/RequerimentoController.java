package br.edu.utfpr.pb.projetojsp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * Created by Edson on 29/05/2017.
 */
@Controller
@RequestMapping("/requerimento")
public class RequerimentoController {
    @RequestMapping("/")
    public String initRequerimento(Map<String, Object> model) {



        /*export const motivosRequerimentos = [
        {key:  1, value: 'Afastamento para estudos no exterior'},
        {key:  2, value: 'Atividades Domiciliares'},
        {key:  3, value: 'Avaliação Antecipada'},
        {key:  4, value: 'Avaliação Diferenciada'},
        {key:  5, value: 'Cancelamento de disciplinas'},
        {key:  6, value: 'Declaração de Matrícula'},
        {key:  7, value: 'Declaração de Provável Formando'},
        {key:  8, value: 'Desistência do Curso'},
        {key:  9, value: '2ª chamada da prova'},
        {key: 10, value: 'Diploma (2ª via)'},
        {key: 11, value: 'Histórico Atualizado'},
        {key: 12, value: 'Histórico de Conclusão do Ensino Superior'},
        {key: 13, value: 'Matrícula em Atividades Complementares'},
        {key: 14, value: 'Matrícula em Estágio Supervisionado'},
        {key: 15, value: 'Matrícula nas disciplinas'},
        {key: 16, value: 'Mudança para as turmas'},
        {key: 17, value: 'Planos de Ensino/Ementas'},
        {key: 18, value: 'Trancamento Total da Matrícula'},
        {key: 19, value: '2ª via do crachá'},
        {key: 20, value: 'Outros'},
        {key: 21, value: 'Convalidação' */






        model.put("message", "HELLO TO THE JSP WORLD!");
        return "requerimento/requerimentoForm";
    }
}
