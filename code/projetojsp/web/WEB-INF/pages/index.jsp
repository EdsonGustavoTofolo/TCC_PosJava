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
                        $(element).find(".jqx-kanban-item-text").html('<a href=' + url + '>' + item.text + '</a>')
                    },
                    columns: boardColumns
                });
            });//FIM GETJSON

            var log = new Array();
            var updateLog = function () {
                var count = 0;
                var str = "";
                for (var i = log.length - 1; i >= 0; i--) {
                    str += log[i] + "<br/>";
                    count++;
                    if (count > 10)
                        break;
                }
                $("#log").html(str);
            }

            $('#kanban').on('itemMoved', function (event) {
                var args = event.args;
                var itemId = args.itemId;
                var oldParentId = args.oldParentId;
                var newParentId = args.newParentId;
                var itemData = args.itemData;
                var oldColumn = args.oldColumn;
                var newColumn = args.newColumn;
                log.push("itemMoved is raised");
                updateLog();
            });
            $('#kanban').on('columnCollapsed', function (event) {
                var args = event.args;
                var column = args.column;
                log.push("columnCollapsed is raised");
                updateLog();
            });
            $('#kanban').on('columnExpanded', function (event) {
                var args = event.args;
                var column = args.column;
                log.push("columnExpanded is raised");
                updateLog();
            });
            $('#kanban').on('itemAttrClicked', function (event) {
                var args = event.args;
                var itemId = args.itemId;
                var attribute = args.attribute; // template, colorStatus, content, keyword, text, avatar
                log.push("itemAttrClicked is raised");
                updateLog();
            });
            $('#kanban').on('columnAttrClicked', function (event) {
                var args = event.args;
                var column = args.column;
                var cancelToggle = args.cancelToggle; // false by default. Set to true to cancel toggling dynamically.
                var attribute = args.attribute; // title, button
                log.push("columnAttrClicked is raised");
                updateLog();
            });

//            $('#kanban1').on("itemAttrClicked", function (event) {
//                var args = event.args;
//                if (args.attribute == "template") {
//                    $('#kanban1').jqxKanban('removeItem', args.item.id);
//                }
//            });
//            var itemIndex = 0;
//            $('#kanban1').on('columnAttrClicked', function (event) {
//                var args = event.args;
//                if (args.attribute == "button") {
//                    args.cancelToggle = true;
//                    if (!args.column.collapsed) {
//                        var colors = ['#f19b60', '#5dc3f0', '#6bbd49', '#732794'];
//                        $('#kanban1').jqxKanban('addItem', { status: args.column.dataField, text: "<input placeholder='(No Title)' style='width: 96%; margin-top:2px; border-radius: 3px; border-color: #ddd; line-height:20px; height: 20px;' class='jqx-input' id='newItem" + itemIndex + "' value=''/>", tags: "new task", color: colors[Math.floor(Math.random() * 4)], resourceId: Math.floor(Math.random() * 4) });
//                        var input =  $("#newItem" + itemIndex);
//                        input.mousedown(function (event) {
//                            event.stopPropagation();
//                        });
//                        input.mouseup(function (event) {
//                            event.stopPropagation();
//                        });
//
//                        input.keydown(function (event) {
//                            if (event.keyCode == 13) {
//                                $("<span>" + $(event.target).val() + "</span>").insertBefore($(event.target));
//                                $(event.target).remove();
//                            }
//                        });
//                        input.focus();
//                        itemIndex++;
//                    }
//                }
//            });
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
    <div id="log"></div>
    <hr>
    <div id="kanban1"></div>
  </jsp:body>
</layout:template>