<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/layout" prefix="layout" %>

<layout:template>
    <jsp:attribute name="cssEspecificos"></jsp:attribute>
    <jsp:attribute name="scriptsEspecificos">
        <script type="text/javascript">
            $(document).ready(function () {
                $("#telefone").mask("(99) 99999-9999");
                var $input = $("#motivo");
                $input.typeahead({
                    source: [
                        {id:  1, name: 'Afastamento para estudos no exterior'},
                        {id:  2, name: 'Atividades Domiciliares'},
                        {id:  3, name: 'Avaliação Antecipada'},
                        {id:  4, name: 'Avaliação Diferenciada'},
                        {id:  5, name: 'Cancelamento de disciplinas'},
                        {id:  6, name: 'Declaração de Matrícula'},
                        {id:  7, name: 'Declaração de Provável Formando'},
                        {id:  8, name: 'Desistência do Curso'},
                        {id:  9, name: '2ª chamada de prova'},
                        {id: 10, name: 'Diploma (2ª via)'},
                        {id: 11, name: 'Histórico Atualizado'},
                        {id: 12, name: 'Histórico de Conclusão do Ensino Superior'},
                        {id: 13, name: 'Matrícula em Atividades Complementares'},
                        {id: 14, name: 'Matrícula em Estágio Supervisionado'},
                        {id: 15, name: 'Matrícula nas disciplinas'},
                        {id: 16, name: 'Mudança para as turmas'},
                        {id: 17, name: 'Planos de Ensino/Ementas'},
                        {id: 18, name: 'Trancamento Total da Matrícula'},
                        {id: 19, name: '2ª via do crachá'},
                        {id: 20, name: 'Outros'},
                        {id: 21, name: 'Convalidação'}
                    ],
                    autoSelect: true
                });
                $input.change(function() {
                    var motivo = $input.typeahead("getActive");
                    if (motivo) { // Some item from your model is active!
                        if ((motivo.id != 9) && !$('#motivo9').hasClass('hidden')) { //se nao for 2o. chamada e a div do motivo9 nao estiver invisivel
                            $('#motivo9').addClass('hidden');
                        }

                        if (motivo.id == 21) { //Convalidação
                            //TODO ver para habilitar os campos aqui ou abrir outra página
                        } else if (motivo.id == 9) {//2o. chamada
                            $('#motivo9').removeClass('hidden');
                        }
                    } else {
                        // Nothing is active so it is a new value (or maybe empty value)
                    }
                });
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <div class="container-fluid">
            <h3>Requerimento <small>5000</small></h3>
            <hr>
            <div class="row pt-3 container">
                <form action="<c:url value="/requerimento/" />" method="post">
                    <div class="form-group col-lg-12 col-md-12 col-sm-12">
                        <div class="form-group">
                            <label for="motivo">Motivo do Requerimento:</label>
                            <input id="motivo" type="text" data-provide="typeahead" class="form-control"
                                   placeholder="Comece a digitar. Exemplo: convalidação, chamada, histórico..." required/>
                        </div>
                    </div>
                    <div id="motivo9" class="form-group col-lg-12 col-md-12 col-sm-12 hidden">
                        <hr>
                        <div class="form-group">
                            <label for="disciplina">Disciplina:</label>
                            <input id="disciplina" type="text" class="form-control"/>
                        </div>
                        <div class="form-group">
                            <label for="professor">Professor:</label>
                            <input id="professor" type="text" class="form-control"/>
                        </div>
                        <div class="form-group">
                            <label for="data">Data:</label>
                            <input id="data" type="text" class="form-control"/>
                        </div>
                    </div>
                    <div class="form-group col-lg-12 col-md-12 col-sm-12">
                        <label for="observacao">Observações:</label>
                        <textarea id="observacao" class="form-control" rows="5"></textarea>
                    </div>
                    <button type="reset" class="btn btn-default">Limpar</button>
                    <button type="submit" class="btn btn-primary">Salvar</button>
                </form>
            </div>
        </div>
    </jsp:body>
</layout:template>