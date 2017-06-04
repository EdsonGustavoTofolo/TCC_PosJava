<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/layout" prefix="layout" %>

<layout:template>
    <jsp:attribute name="cssEspecificos"></jsp:attribute>
    <jsp:attribute name="scriptsEspecificos">
        <script type="text/javascript">
            $(document).ready(function () {
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
                        {id:  9, name: '2ª chamada da prova'},
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
                    var current = $input.typeahead("getActive");
                    if (current) {
                        // Some item from your model is active!
                        if (current.name == $input.val()) {
                            // This means the exact match is found. Use toLowerCase() if you want case insensitive match.
                        } else {
                            // This means it is only a partial match, you can either add a new item
                            // or take the active if you don't want new items
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
                <div class="form-group col-lg-4 col-md-12 col-6">
                    <div class="form-group">
                        <label for="codigo">Código</label>
                        <input type="text" class="form-control" id="codigo"/>
                    </div>
                </div>
                <div class="form-group col-lg-8 col-md-12 col-6">
                    <div class="form-group">
                        <label for="telefone">Telefone</label>
                        <input type="text" class="form-control" id="telefone"/>
                    </div>
                </div>
                <div class="form-group col-lg-6 col-md-12 col-6">
                    <div class="form-group">
                        <label for="aluno">Aluno</label>
                        <input type="text" class="form-control" id="aluno"/>
                    </div>
                </div>
                <div class="form-group col-lg-6 col-md-12 col-6">
                    <div class="form-group">
                        <label for="email">E-mail</label>
                        <input type="text" class="form-control" id="email"/>
                    </div>
                </div>
                <div class="form-group col-lg-12 col-md-12 col-12">
                    <div class="form-group">
                        <label for="curso">Curso</label>
                        <input type="text" class="form-control" id="curso"/>
                    </div>
                </div>
                <div class="form-group col-lg-12 col-md-12 col-12">
                    <div class="form-group">
                        <label for="motivo">Motivo do Requerimento:</label>
                        <input id="motivo" type="text" data-provide="typeahead" class="form-control" />
                            <%--<select id="tipo" name="tipo" class="form-control">--%>
                            <%--<option value=""> - Selecione - </option>--%>
                            <%--<c:forEach var="tipo" items="${tipos}">--%>
                                <%--<option value="${categoria.id}"--%>
                                    <%--${categoria.id==produto.categoria.id ? 'selected' : ''}>--%>
                                        <%--${categoria.descricao}</option>--%>
                            <%--</c:forEach>--%>
                        <%--</select>--%>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</layout:template>