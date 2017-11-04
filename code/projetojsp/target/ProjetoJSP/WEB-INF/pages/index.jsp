<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<spring:url value="/usuario/conta/" var="urlContaUsuario" />

<layout:template>
  <jsp:attribute name="cssEspecificos">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/jqxKanban/jqx.base.css"/> " />
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/jqgrid/ui.jqgrid-bootstrap.css"/> " />
    <link rel="stylesheet" type="text/css" href="<c:url value="/webjars/select2/4.0.3/dist/css/select2.min.css"/> " />
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/select2-v3/select2-bootstrap.min.css"/> " />
  </jsp:attribute>
  <jsp:attribute name="scriptsEspecificos">
    <script type="text/javascript" src="<c:url value="/resources/js/requerimento/motivoList.js"/> "></script>
    <%--<script type="text/javascript" src="<c:url value="/resources/js/requerimento/requerimentoViewer.js"/> "></script>--%>
    <script type="text/javascript" src="<c:url value="/resources/js/requerimento/requerimentoChangeStatus.js"/> "></script>
    <script type="text/javascript" src="<c:url value="/resources/js/requerimento/requerimentoViewer2.js"/> "></script>
    <script type="text/javascript" src="<c:url value="/resources/js/jqxKanban/jqxcore.js"/> "></script>
    <script type="text/javascript" src="<c:url value="/resources/js/jqxKanban/jqxsortable.js"/> "></script>
    <script type="text/javascript" src="<c:url value="/resources/js/jqxKanban/jqxkanban.js"/> "></script>
    <script type="text/javascript" src="<c:url value="/resources/js/jqxKanban/jqxdata.js"/> "></script>
    <script type="text/javascript" src="<c:url value="/resources/js/jqxKanban/demos.js"/> "></script>
    <script type="text/javascript" src="<c:url value="/resources/js/moment/moment-with-locales.min.js"/> "></script>
    <script type="text/javascript" src="<c:url value="/resources/js/jqgrid/grid.locale-pt-br.js"/> "></script>
    <script type="text/javascript" src="<c:url value="/resources/js/jqgrid/jquery.jqGrid.min.js"/> "></script>
    <script type="text/javascript" src="<c:url value="/webjars/select2/4.0.3/dist/js/select2.full.min.js"/> "></script>
    <script  type="text/javascript">
        $(document).ready(function () {
            $.jgrid.defaults.styleUI = 'Bootstrap';
            $.jgrid.defaults.responsive = true;
            $.jgrid.styleUI.Bootstrap.base.rowTable = "table table-bordered table-striped";
            $.fn.select2.defaults.set( "theme", "bootstrap" );
            $.fn.select2.defaults.set( "width", "100%" );

            function getColor(status) {
                if (status == "EM_ABERTO") {
                    return '#1c84c6';
                } else if (status == "APROVADO") {
                    return '#1ab394'
                } else if (status == "DEVOLVIDO_DERAC") {
                    return '#f8ac59'
                } else if (status == "CANCELADO") {
                    return '#ed5565'
                } else if (status == "AGUARDANDO_COORDENACAO") {
                    return '#f8ac59'
                } else if (status == "AGUARDANDO_DIRGRAD") {
                    return '#f8ac59'
                } else if (status == "AGUARDANDO_NUAPNE") {
                    return '#f8ac59'
                } else if (status == "AGUARDANDO_DIRGE") {
                    return '#f8ac59'
                } else { //finalizado
                    return '#1ab394'
                }
            }

            var urlRequerimentos = '';
            var boardColumns = [];

            <sec:authorize access="hasRole('ALUNO')">
              urlRequerimentos = '/ProjetoJSP/requerimento/findToAluno';
              boardColumns =  [
                  { text: "Cancelado", dataField: "CANCELADO" },
                  { text: "Enviar DERAC", dataField: "EM_ABERTO" }
              ];
            </sec:authorize>
            <sec:authorize access="hasRole('COORDENACAO')">
              urlRequerimentos = '/ProjetoJSP/requerimento/findToCoordenacao';
              boardColumns =  [
                { text: "Aguardando Coordenação", dataField: "AGUARDANDO_COORDENACAO" },
                { text: "Devolver DERAC", dataField: "DEVOLVIDO_DERAC" }
              ];
            </sec:authorize>
            <sec:authorize access="hasRole('DERAC')">
              urlRequerimentos = '/ProjetoJSP/requerimento/findToDerac';
              boardColumns =  [
                  { text: "Aguardando DERAC", dataField: "EM_ABERTO" },
                  { text: "Devolvido", dataField: "DEVOLVIDO_DERAC" },
                  { text: "Aprovar", dataField: "APROVADO" },
                  { text: "Encaminhar Coordenação", dataField: "AGUARDANDO_COORDENACAO" },
                  { text: "Encaminhar DIRGRAD", dataField: "AGUARDANDO_DIRGRAD" },
                  { text: "Encaminhar DIRGE", dataField: "AGUARDANDO_DIRGE" },
                  { text: "Encaminhar NUAPNE", dataField: "AGUARDANDO_NUAPNE" },
                  { text: "Cancelar", dataField: "CANCELADO" },
                  { text: "Finalizar", dataField: "FINALIZADO" }
              ];
            </sec:authorize>
            <sec:authorize access="hasRole('PROFESSOR')">
              urlRequerimentos = '/ProjetoJSP/requerimento/findToProfessor';
              boardColumns =  [
                { text: "Aguardando Professor", dataField: "AGUARDANDO_PROFESSOR" },
                { text: "Finalizar", dataField: "FINALIZADO" }
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
                          tags: requerimento.motivo == 9 || requerimento.motivo == 5 || requerimento.motivo == 15 || requerimento.motivo == 17 ? requerimento.disciplinas[0].disciplina.curso.usuario.nome : requerimento.usuario.curso.usuario.nome,
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

                var resourcesAdapterFunc = function () {
                    var resourcesSource =
                        {
                            localData: [
                                { id: 0, name: "No name", image: "<c:url value="/resources/images/user.jpg"/> ", common: true }
                            ],
                            dataType: "array",
                            dataFields: [
                                { name: "id", type: "number" },
                                { name: "name", type: "string" },
                                { name: "image", type: "string" },
                                { name: "common", type: "boolean" }
                            ]
                        };
                    var resourcesDataAdapter = new $.jqx.dataAdapter(resourcesSource);
                    return resourcesDataAdapter;
                }

                $('#kanban').jqxKanban({
                    width: '100%',
                    height: '100%',
                    source: dataAdapter,
                    resources: resourcesAdapterFunc(), //tem só pra não dar erro ao utilizar a função addItem
                    itemRenderer: function(element, item, resource) {
                        var url = "/ProjetoJSP/requerimento/edit/" + item.id;
                        $(element).find(".jqx-kanban-item-avatar img").attr('data-original-title', item.content);
                        $(element).find(".jqx-kanban-item-avatar img").attr('title', item.content);
                        $(element).find(".jqx-kanban-item-avatar img").attr('data-toggle', 'tooltip');
                        $(element).find(".jqx-kanban-item-avatar img").attr('data-placement', 'bottom');
                        $(element).find(".jqx-kanban-item-text").html('<a href=' + url + '>' + item.text + '</a>');
                        $("<div class='jqx-icon jqx-kanban-item-template-content jqx-kanban-template-icon' data-original-title='Ver requerimento' title='Ver requerimento' data-toggle='tooltip' data-placement='top'>" +
                            "<i class='fa fa-info-circle'></i></div>")
                            .insertAfter($(element).find(".jqx-kanban-item-avatar"));
                    },
                    columns: boardColumns
                });

                $('.jqx-kanban-item').tooltip({
                    selector: "[data-toggle=tooltip]",
                    container: "body"
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

                var itemDataBeforeMove = JSON.parse(JSON.stringify(itemData)); //clonar o obj

                changeStatus($('#changeStatus'), newColumn.dataField, itemId,
                    function(data) {
                        if (data.state == "FAIL") {
                            swal({
                                title : "Falhou!",
                                text : data.message,
                                type : "error",
                                confirmButtonText : "Ok",
                                showCancelButton : false,
                                allowEscapeKey: false,
                                allowOutsideClick: false,
                            }).then(function() {
                                $('#kanban').jqxKanban('removeItem', itemId);
                                $('#kanban').jqxKanban('addItem', itemDataBeforeMove); //volta para posição original
                            }).catch(swal.noop);

                        } else if (data.state == "ERROR") {
                            swal("Falhou!", data.message, "error").catch(swal.noop);
                        }
                  },
                function () {
                    window.location.reload();
                });
            });

            $('#kanban').on('itemAttrClicked', function (event) {
                var args = event.args;
                var itemId = args.itemId;
                var attribute = args.attribute; // template, colorStatus, content, keyword, text, avatar

                if (attribute == "template") {
                    visualizarRequerimento(itemId, $('#visualizarRequerimento'));
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

    <div id="visualizarRequerimento"></div>

    <div id="changeStatus"></div>
  </jsp:body>
</layout:template>