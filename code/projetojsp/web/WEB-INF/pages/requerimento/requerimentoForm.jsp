<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/layout" prefix="layout" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="frm" uri="http://www.springframework.org/tags/form"%>

<layout:template>
    <jsp:attribute name="cssEspecificos"></jsp:attribute>
    <jsp:attribute name="scriptsEspecificos">
        <script type="text/javascript" src="<c:url value="/resources/js/requerimento/requerimento.js"/> "></script>
    </jsp:attribute>
    <jsp:body>
        <div class="container-fluid">
            <h3>Requerimento <small>5000</small></h3>
            <hr>
            <div class="row pt-3 container">
                <spring:url value="/requerimento/salvar" var="requerimentoActUrl" />

                <frm:form id="frm" method="post" modelAttribute="requerimentoForm" action="${requerimentoActUrl}" autocomplete="off">
                    <input id="id" name="id" type="text" hidden/>
                    <input id="motivo" name="motivo" type="text" hidden/>
                    <div class="form-group col-lg-12 col-md-12 col-sm-12">
                        <div class="form-group">
                            <label for="motivoTxt">Motivo do Requerimento:</label>
                            <input id="motivoTxt" type="text" data-provide="typeahead" class="form-control"
                                   placeholder="Comece a digitar. Exemplo: convalidação, chamada, histórico..." required/>
                        </div>
                    </div>
                    <div id="motivo9" class="form-group col-lg-12 col-md-12 col-sm-12 hidden">
                        <hr>
                        <div class="form-group">
                            <label for="disciplina">Disciplina:</label>
                            <input id="disciplina" name="disciplina" type="text" class="form-control"/>
                        </div>
                        <div class="form-group">
                            <label for="professor">Professor:</label>
                            <input id="professor" name="profesor" type="text" class="form-control"/>
                        </div>
                        <div class="form-group">
                            <label for="data">Data:</label>
                            <input id="data" name="data" type="text" class="form-control"/>
                        </div>
                    </div>
                    <div class="form-group col-lg-12 col-md-12 col-sm-12">
                        <label for="observacao">Observações:</label>
                        <textarea id="observacao" name="observacao" class="form-control" rows="5"></textarea>
                    </div>
                    <button type="reset" class="btn btn-default">Limpar</button>
                    <button type="submit" class="btn btn-primary">Salvar</button>
                </frm:form>
            </div>
        </div>
    </jsp:body>
</layout:template>