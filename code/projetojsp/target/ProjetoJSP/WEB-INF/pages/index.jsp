<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<spring:url value="/usuario/conta/" var="urlContaUsuario" />

<layout:template>
  <jsp:attribute name="cssEspecificos">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/jqxKanban/jqx.base.css"/> " />
  </jsp:attribute>
  <jsp:attribute name="scriptsEspecificos">
    <script type="text/javascript" src="<c:url value="/resources/js/requerimento/motivoList.js"/> "></script>
    <script type="text/javascript" src="<c:url value="/resources/js/jqxKanban/jqxcore.js"/> "></script>
    <script type="text/javascript" src="<c:url value="/resources/js/jqxKanban/jqxsortable.js"/> "></script>
    <script type="text/javascript" src="<c:url value="/resources/js/jqxKanban/jqxkanban.js"/> "></script>
    <script type="text/javascript" src="<c:url value="/resources/js/jqxKanban/jqxdata.js"/> "></script>
    <script type="text/javascript" src="<c:url value="/resources/js/jqxKanban/demos.js"/> "></script>
    <script type="text/javascript" src="<c:url value="/webjars/jquery-blockui/2.70/jquery.blockUI.js"/> "></script>
    <script  type="text/javascript">
        $(document).ready(function () {

            function getColor(status) {
                if (status == "AGUARDANDO_DERAC") {
                    return '#f8ac59';
                } else if (status == "APROVADO_DERAC") {
                    return '#1c84c6'
                } else if (status == "FALTA_DOCUMENTOS") {
                    return '#ed5565'
                } else if (status == "RECUSADO") {
                    return '#ed5565'
                } else if (status == "AGUARDANDO_COORDENACAO") {
                    return '#f8ac59'
                } else if (status == "APROVADO_COORDENACAO") {
                    return '#1c84c6'
                } else {
                    return '#1ab394'
                }
            }

            var urlRequerimentos = '';
            var boardColumns = [];

            <sec:authorize access="hasRole('ALUNO')">
              urlRequerimentos = '/ProjetoJSP/requerimento/findToAluno';
              boardColumns =  [
                  { text: "Falta de Documentos", dataField: "FALTA_DOCUMENTOS" },
                  { text: "Enviar DERAC", dataField: "AGUARDANDO_DERAC" }
              ];
            </sec:authorize>
            <sec:authorize access="hasRole('COORDENACAO')">
              urlRequerimentos = '/ProjetoJSP/requerimento/findToCoordenacao';
              boardColumns =  [
                { text: "Aguardando Coordenação", dataField: "AGUARDANDO_COORDENACAO" },
                { text: "Aprovar", dataField: "AGUARDANDO_COORDENACAO" },
                { text: "Recusado", dataField: "RECUSADO" },
                { text: "Finalizado", dataField: "FINALIZADO" }
              ];
            </sec:authorize>
            <sec:authorize access="hasRole('DERAC')">
              urlRequerimentos = '/ProjetoJSP/requerimento/findToDerac';
              boardColumns =  [
                  { text: "Aguardando DERAC", dataField: "AGUARDANDO_DERAC" },
                  { text: "Aprovar", dataField: "APROVADO_DERAC" },
                  { text: "Enviar Coordenação", dataField: "AGUARDANDO_COORDENACAO" },
                  { text: "Falta de Documentos", dataField: "FALTA_DOCUMENTOS" },
                  { text: "Recusado", dataField: "RECUSADO" },
                  { text: "Finalizado", dataField: "FINALIZADO" }
              ];
            </sec:authorize>
            <sec:authorize access="hasRole('PROFESSOR')">
              urlRequerimentos = '/ProjetoJSP/requerimento/findToProfessor';
              boardColumns =  [
                { text: "Aguardando Professor", dataField: "AGUARDANDO_PROFESSOR" },
                { text: "Finalizado", dataField: "FINALIZADO" }
              ];
            </sec:authorize>

            $.getJSON(urlRequerimentos, {}, function (data) {
                var localData = [];
                data.forEach(function(requerimento, index) {
                    localData.push(
                        {
                          id: requerimento.id,
                          status: requerimento.status,
                          text: motivoList[requerimento.motivo - 1].descricao,
                          tags: requerimento.usuario.curso.usuario.nome,
                          content: requerimento.usuario.nome,
                          color: getColor(requerimento.status),
                          resourceId: null
                        }
                    );
                });

                var source =
                    {
                        localData: localData,
                        dataType: "array",
                        dataFields:
                            [
                                { name: "id", type: "number" },
                                { name: "status", type: "string" },
                                { name: "text", type: "string" },
                                { name: "tags", type: "string" },
                                { name: "content", type: "string" },
                                { name: "color", type: "string" },
                                { name: "resourceId", type: "number" }
                            ]
                    };

                var dataAdapter = new $.jqx.dataAdapter(source);

                $('#kanban').jqxKanban({
                    width: '100%',
                    height: '100%',
                    source: dataAdapter,
                    itemRenderer: function(element, item, resource) {
                        var url = "/ProjetoJSP/requerimento/edit/" + item.id;
                        $(element).find(".jqx-kanban-item-avatar img").attr('title', item.content);
                        $(element).find(".jqx-kanban-item-text").html('<a href=' + url + '>' + item.text + '</a>');
                        $("<div class='jqx-icon jqx-kanban-item-template-content jqx-kanban-template-icon'>" +
                            "<i class='fa fa-info-circle'></i></div>")
                            .insertAfter($(element).find(".jqx-kanban-item-avatar"));
                    },
                    columns: boardColumns
                });
            });//FIM GETJSON

            $('#kanban').on('itemMoved', function (event) {
                var args = event.args;
                var itemId = args.itemId;
                var oldParentId = args.oldParentId;
                var newParentId = args.newParentId;
                var itemData = args.itemData;
                var oldColumn = args.oldColumn;
                var newColumn = args.newColumn;
            });
            $('#kanban').on('itemAttrClicked', function (event) {
                var args = event.args;
                var itemId = args.itemId;
                var attribute = args.attribute; // template, colorStatus, content, keyword, text, avatar
//                console.log(args);
//                console.dir(args);

                if (attribute == "template") {
                    $.blockUI({message: $('#loadingModal')});
                    $.getJSON('/ProjetoJSP/requerimento/findById', {"id": itemId}, function (data) {
                        console.dir(data);
                        $.unblockUI();
                        $("#alunoNome").val(data.usuario.nome);
                        $("#motivo").val(motivoList[data.motivo - 1].descricao);
                        $("#linkOpenModalReq").click();
                    });
                }
            });
            $('#kanban').on('columnAttrClicked', function (event) {
                var args = event.args;
                var column = args.column;
                var cancelToggle = args.cancelToggle; // false by default. Set to true to cancel toggling dynamically.
                var attribute = args.attribute; // title, button
            });
        });
    </script>
  </jsp:attribute>
  <jsp:body>
    <c:if test="${mensagem != null && !mensagem.equals('')}">
      <div class="alert alert-warning alert-dismissable">
        <button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>
        <a href="${urlContaUsuario}">
          ${mensagem}
        </a>
      </div>
    </c:if>
    <div id="kanban"></div>

    <a id="linkOpenModalReq" class="hidden" data-toggle='modal' href='index.jsp#requerimentoViewer'></a>

    <div aria-hidden="true" aria-labelledby="requerimentoDialog" role="dialog" tabindex="-1" id="requerimentoViewer" class="modal fade">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title">Requerimento</h4>
          </div>
          <div class="modal-body">
            <label for="alunoNome">Aluno:</label>
            <input id="alunoNome" type="text" value="" class="form-control" disabled/>
            <label for="motivo">Motivo do Requerimento:</label>
            <input id="motivo" type="text" value="" class="form-control" disabled/>
          </div>
          <div class="modal-footer">
            <button class="btn btn-primary" type="button">Ok</button>
          </div>
        </div>
      </div>
    </div>
  </jsp:body>
</layout:template>