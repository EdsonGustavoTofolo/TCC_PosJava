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
  </jsp:attribute>
  <jsp:attribute name="scriptsEspecificos">
    <script type="text/javascript" src="<c:url value="/resources/js/requerimento/motivoList.js"/> "></script>
    <script type="text/javascript" src="<c:url value="/resources/js/jqxKanban/jqxcore.js"/> "></script>
    <script type="text/javascript" src="<c:url value="/resources/js/jqxKanban/jqxsortable.js"/> "></script>
    <script type="text/javascript" src="<c:url value="/resources/js/jqxKanban/jqxkanban.js"/> "></script>
    <script type="text/javascript" src="<c:url value="/resources/js/jqxKanban/jqxdata.js"/> "></script>
    <script type="text/javascript" src="<c:url value="/resources/js/jqxKanban/demos.js"/> "></script>
    <script type="text/javascript" src="<c:url value="/resources/js/moment/moment-with-locales.min.js"/> "></script>
    <script type="text/javascript" src="<c:url value="/resources/js/jqgrid/grid.locale-pt-br.js"/> "></script>
    <script type="text/javascript" src="<c:url value="/resources/js/jqgrid/jquery.jqGrid.min.js"/> "></script>
    <script  type="text/javascript">
        $(document).ready(function () {
            $('#requerimentoViewer').tooltip({
                selector: "[data-toggle=tooltip]",
                container: "body"
            });

            $.jgrid.defaults.styleUI = 'Bootstrap';
            $.jgrid.defaults.responsive = true;
            $.jgrid.styleUI.Bootstrap.base.rowTable = "table table-bordered table-striped";

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
                  { text: "Recusados", dataField: "RECUSADO" },
                  { text: "Enviar DERAC", dataField: "AGUARDANDO_DERAC" }
              ];
            </sec:authorize>
            <sec:authorize access="hasRole('COORDENACAO')">
              urlRequerimentos = '/ProjetoJSP/requerimento/findToCoordenacao';
              boardColumns =  [
                { text: "Aguardando Coordenação", dataField: "AGUARDANDO_COORDENACAO" },
                { text: "Aprovar", dataField: "APROVADO_COORDENACAO" },
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

                var url = '/ProjetoJSP/requerimento/edit/' + itemId + '/changeStatusKanban/' + newColumn.dataField;
                $.ajax({
                    type : 'PUT',
                    url : url,
                    success : function(data) {
                        data = JSON.parse(data);
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
                    },//Fim success
                    error : function() {
                        swal("Erro!", "Falha ao alterar status.", "error").catch(swal.noop);
                    }
                }); //Fim ajax
            });

            $('#kanban').on('itemAttrClicked', function (event) {
                var args = event.args;
                var itemId = args.itemId;
                var attribute = args.attribute; // template, colorStatus, content, keyword, text, avatar

                if (attribute == "template") {
                    $.getJSON('/ProjetoJSP/requerimento/findById', {"id": itemId}, function (requerimento) {
                        if (!$('#motivo9').hasClass('hidden')) {
                            $("#motivo9").addClass('hidden');
                        }
                        if (!$("#disciplinas").hasClass('hidden')) {
                            $("#disciplinas").addClass('hidden');
                        }
                        if (!$("#anexos").hasClass('hidden')) {
                            $("#anexos").addClass('hidden');
                        }

                        $("#requerimentoId").html('');
                        $("#requerimentoData").html('');
                        $("#anexos").find(".dz-preview").remove();

                        var dataRequerimento = new Date(requerimento.data);
                        var momentData = moment(dataRequerimento).locale('pt-br');

                        var dataRequerimentoAgo = momentData.fromNow(); //quanto tempo atrás
                        dataRequerimento = momentData.format('llll');

                        $("#requerimentoId").append(requerimento.id);
                        $("#requerimentoData").append(dataRequerimento);
                        $("#requerimentoData").attr("data-original-title", dataRequerimentoAgo);

                        $("#alunoNome").val(requerimento.usuario.nome);
                        $("#motivo").val(motivoList[requerimento.motivo - 1].descricao);
                        $("#curso").val(requerimento.usuario.curso.usuario.nome);
                        $("#observacao").val(requerimento.observacao);

                        if (requerimento.motivo == 9) { //2 chamada de prova
                          $("#motivo9").removeClass('hidden');
                          $("#curso").val(requerimento.disciplinas[0].disciplina.curso.usuario.nome);
                          $("#disciplina").val(requerimento.disciplinas[0].disciplina.codigo + " - " + requerimento.disciplinas[0].disciplina.nome);
                          $("#professor").val(requerimento.disciplinas[0].professor.nome);
                          $("#data").val(requerimento.disciplinas[0].dataProva);

                        } else if (requerimento.motivo == 5 || requerimento.motivo == 15 || requerimento.motivo == 17) { //exibir tabela com disciplinas
                          $("#disciplinas").removeClass('hidden');
                          $("#curso").val(requerimento.disciplinas[0].disciplina.curso.usuario.nome);

                          $("#disciplinaList").html('');
                          $("#disciplinaList").append('<table id="jqGrid"></table><div id="jqGridPager"></div>');

                          $("#jqGrid").jqGrid({
                              datatype : "jsonstring",
                              datastr: JSON.stringify(requerimento.disciplinas),
                              jsonReader: { repeatitems: false },
                              colModel: [
                                  { label: "Código", name: "codigo", jsonmap: 'disciplina.codigo', width: 100 },
                                  { label: "Nome", name: "nome", jsonmap: 'disciplina.nome', width: 400 }
                              ],
                              pager: '#jqGridPager',
                              rowNum: 5,
                              width: 'auto',
                              heigth: '200',
                              scroll: 1,
                              viewrecords: true
                          });
                        }

                        if (requerimento.anexos.length > 0) { //exibir anexos para efetuar downloads
                          $("#anexos").removeClass('hidden');
                          requerimento.anexos.forEach(function (anexo) {
                              $(".attached").append(
                                  $('<div id="anexo' + anexo.id + '" class="dz-preview dz-file-preview">' +
                                      '<div class="dz-image"><img data-dz-thumbnail=""></div>' +
                                      '<div class="dz-details">'+
                                        '<div class="dz-size">'+
                                          '<span><strong>' + anexo.size + '</strong> ' + anexo.unitSize + '</span>'+
                                        '</div>'+
                                        '<div class="dz-filename">'+
                                          '<span>' + anexo.nome + '</span>'+
                                        '</div>'+
                                      '</div>'+
                                      '<div class="dz-download" style="top: 80%;">'+
                                        '<a href="/ProjetoJSP/requerimento/anexos/download/' + anexo.id + '">Download</a>'+
                                      '</div>'+
                                  '</div>'));
                          });
                        }

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
            <h4 class="modal-title">Requerimento <small id="requerimentoId"></small> <small>/</small> <small id="requerimentoData" data-toggle="tooltip" data-placement="right"></small></h4>
          </div>
          <div class="modal-body">
            <label for="alunoNome">Aluno:</label>
            <input id="alunoNome" type="text" value="" class="form-control" disabled/>
            <br>
            <label for="motivo">Motivo do Requerimento:</label>
            <input id="motivo" type="text" value="" class="form-control" disabled/>
            <br>
            <label for="curso">Curso:</label>
            <input id="curso" type="text" value="" class="form-control" disabled/>
            <div id="motivo9" class="hidden">
              <br>
              <label for="disciplina">Disciplina:</label>
              <input id="disciplina" type="text" value="" class="form-control" disabled/>
              <br>
              <label for="professor">Professor:</label>
              <input id="professor" type="text" value="" class="form-control" disabled/>
              <br>
              <label for="data">Data:</label>
              <input id="data" type="date" pattern="dd/MM/yyyy" value="" class="form-control" disabled/>
            </div>
            <div id="disciplinas" class="hidden">
              <br>
              <label>Disciplinas:</label>
              <div id="disciplinaList">
              </div>
            </div>
            <div id="anexos" class="hidden">
              <br>
              <label>Anexos:</label>
              <div class="attached">
              </div>
            </div>
            <br>
            <label for="observacao">Observações:</label>
            <textarea id="observacao" name="observacao" class="form-control" rows="5" disabled></textarea>
          </div>
          <%--<div class="modal-footer">--%>
            <%--<button class="btn btn-primary" type="button">Ok</button>--%>
          <%--</div>--%>
        </div>
      </div>
    </div>
  </jsp:body>
</layout:template>