<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/layout" prefix="layout" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="frm" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--http://www.guriddo.net/demo/bootstrap/--%>
<layout:template>
    <jsp:attribute name="cssEspecificos">
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/jqgrid/ui.jqgrid-bootstrap.css"/> " />
        <link rel="stylesheet" type="text/css" href="<c:url value="/webjars/bootstrap-datepicker/1.7.1/css/bootstrap-datepicker3.min.css"/> " />
    </jsp:attribute>
    <jsp:attribute name="scriptsEspecificos">
        <script type="text/javascript" src="<c:url value="/resources/js/jqgrid/grid.locale-pt-br.js"/> "></script>
        <script type="text/javascript" src="<c:url value="/resources/js/jqgrid/jquery.jqGrid.min.js"/> "></script>
        <script type="text/javascript" src="<c:url value="/webjars/jquery-maskedinput/1.4.0/jquery.maskedinput.min.js"/> "></script>
        <script type="text/javascript" src="<c:url value="/webjars/bootstrap-datepicker/1.7.1/js/bootstrap-datepicker.min.js"/> "></script>
        <script type="text/javascript" src="<c:url value="/webjars/bootstrap-datepicker/1.7.1/locales/bootstrap-datepicker.pt-BR.min.js"/> "></script>
        <script type="text/javascript" src="<c:url value="/resources/js/requerimento/motivoList.js"/> "></script>
        <script type="text/javascript" src="<c:url value="/resources/js/requerimento/requerimentoViewer.js"/> "></script>
        <script type="text/javascript" src="<c:url value="/resources/js/moment/moment-with-locales.min.js"/> "></script>
        <script type="text/javascript">
            $(document).ready(function () {
//                $.jgrid.defaults.width = 1000;
                $.jgrid.defaults.styleUI = 'Bootstrap';
                $.jgrid.defaults.responsive = true;
                $.jgrid.styleUI.Bootstrap.base.rowTable = "table table-bordered table-striped";

                $("#jqGrid").jqGrid({
                    url : "/ProjetoJSP/requerimento/loadData",
                    datatype : "json",
                    mtype : 'POST',
                    colModel : [ {
                        label : 'Id',
                        name : 'id',
                        index : 'id',
                        key: true,
                        width : 70
                    }, {
                        label: 'Status',
                        name: 'status',
                        index: 'status',
                        width: 150,
                        align: 'center',
                        stype: "select",
                        searchoptions: {
                            dataInit: function (e) {
                              $(e).addClass('form-control').removeAttr('size');
                            },
                            value: ":[Todos];1:Em Aberto;2:Aprovado;3:Com DERAC;4:Cancelado;5:Encaminhado Coordenação;6:Encaminhado DIRGRAD;7:Encaminhado NUAPE;8:Encaminhado DIRGE;9:Finalizado"
                        },
                        formatter: statusFormatter
                    }
                    <sec:authorize access="hasAnyRole('PROFESSOR', 'COORDENACAO', 'DERAC')">
                        ,{
                            label: 'Aluno',
                            name: 'aluno',
                            index: 'aluno',
                            width: 200,
                            jsonmap: 'usuario.nome'
                        }
                    </sec:authorize>
                    , {
                        label : 'Motivo',
                        name : 'motivo',
                        index : 'motivo',
                        width : 300,
                        formatter: function myformatter ( cellvalue, options, rowObject ) {
                            return motivoList[cellvalue - 1].descricao;
                        },
                        stype: "select",
                        searchoptions: {
                            dataUrl: '/ProjetoJSP/requerimento/motivos/findAll',
                            buildSelect: function (data) {
                                var response, s = '<select>', i;
                                response = jQuery.parseJSON(data);
                                s += '<option value="">[Todos]</option>';
                                if (response && response.length) {
                                    $.each(response, function (i) {
                                        s += '<option value="' + this.id+ '">' + this.descricao+ '</option>';
                                    });
                                }
                                return s + '</select>';
                            }
                        }
                    }, {
                        label : 'Observação',
                        name : 'observacao',
                        index : 'observacao',
                        width : 500,
                    }, {
                        label : 'Data',
                        name : 'data',
                        index : 'data',
                        width : 130,
                        sorttype: 'date',
                        formatter: 'date',
                        formatoptions: { srcformat: "ISO8601Long", newformat: "d/m/Y H:i" },
                        searchoptions: {
                            dataInit: function (element) {
                                $(element).datepicker({
                                    language: "pt-BR",
                                    todayBtn: "linked",
                                    keyboardNavigation: true,
                                    forceParse: true,
                                    calendarWeeks: true,
                                    autoclose: true,
                                    format: "dd/mm/yyyy",
                                    todayHighlight: true
                                });
                                $(element).mask("99/99/9999");
                            }
                        }
                    }],
                    pager : '#jqGridPager',
                    rowNum : 10,
                    height: 'auto',
                    with: 'auto',
//                    autoWidth: true,
                    shrinkToFit: true,
//                    forceFit: true,
                    rowList : [ 10, 20, 30 ],
                    viewrecords : true,
                    caption : 'Requerimentos',
                    jsonReader : {repeatitems : false},
                    subGrid: true,
                    subGridRowExpanded: showChildGrid,
                    subGridOptions : {
                        selectOnExpand : true
                    }
                
                });
                
                function statusFormatter(cellvalue, options, rowobject) {
                    if (cellvalue == "EM_ABERTO") {
                        return '<span class="label label-success">Encaminhado DERAC</span>'
                    } else if (cellvalue == "APROVADO") {
                        return '<span class="label label-primary">Aprovado</span>'
                    } else if (cellvalue == "DEVOLVIDO_DERAC") {
                        return '<span class="label label-warning">Com DERAC</span>'
                    } else if (cellvalue == "CANCELADO") {
                        return '<span class="label label-danger">Cancelado</span>'
                    } else if (cellvalue == "AGUARDANDO_COORDENACAO") {
                        return '<span class="label label-warning">Encaminhado Coordenação</span>'
                    } else if (cellvalue == "AGUARDANDO_DIRGRAD") {
                        return '<span class="label label-warning">Encaminhado DIRGRAD</span>'
                    } else if (cellvalue == "AGUARDANDO_NUAPNE") {
                        return '<span class="label label-warning">Encaminhado NUAPE</span>'
                    } else if (cellvalue == "AGUARDANDO_DIRGE") {
                        return '<span class="label label-warning">Encaminhado DIRGE</span>'
                    } else {
                        return '<span class="label label-primary">Finalizado</span>'
                    }
                }

                // activate the toolbar searching
                $('#jqGrid').jqGrid('filterToolbar');
                $("#jqGrid").navGrid('#jqGridPager',
                    { edit: false, add: false, del: false, search: false, refresh: true, view: false, position: "left", cloneToTop: false, refreshicon: "fa fa-refresh" },
                    {}, {}
                ).navButtonAdd("#jqGridPager", { //NOVO REQUERIMENTO
                    caption:"",
                    buttonicon:"fa fa-plus",
                    onClickButton: function () {
                        window.location.href = "/ProjetoJSP/requerimento/";
                    }, 
                    position: "last", 
                    title:"Novo requerimento",
                    cursor: "pointer"  
                }).navButtonAdd("#jqGridPager", { //EDITAR REQUERIMENTO
                    caption:"",
                    buttonicon:"fa fa-pencil-square",
                    onClickButton: function () {
                        rowKey = getSelectedRow();
                        if (rowKey) {
                            window.location.href = "/ProjetoJSP/requerimento/edit/" + getSelectedRow();
                        } else {
                            swal("Selecione um requerimento!", "Nenhum requerimento selecionado!", "warning").catch(swal.noop); // esse catch evita erro no console do browser
                        }
                    },
                    position: "last",
                    title:"Editar requerimento",
                    cursor: "pointer"
                }).navButtonAdd("#jqGridPager", { //CANCELAR REQUERIMENTO
                    caption:"",
                    buttonicon:"fa fa-trash",
                    onClickButton: function () {
                        swal({
                            title: 'Confirma o cancelamento do requerimento?!',
                            text: "Esta ação não poderá ser desfeita!",
                            type: 'question', //warning
                            showLoaderOnConfirm: true,
                            showCancelButton: true,
                            confirmButtonColor: '#3085d6',
                            cancelButtonColor: '#d33',
                            confirmButtonText: 'Sim, cancelar!!',
                            cancelButtonText: 'Não',
                            allowOutsideClick: false,
                            preConfirm: function() {
                                return new Promise(function (resolve, reject) {
                                    rowKey = getSelectedRow();
                                    if (rowKey) {
                                        resolve();
                                    } else {
                                        reject("Nenhum requerimento selecionado!");
                                    }
                                })
                            }
                        }).then(function () {
                            var url = '/ProjetoJSP/requerimento/cancel/' + getSelectedRow();
                            $.ajax({
                                type : 'PUT', //DELETE
                                url : url,
                                success : function(data) {
                                    data = JSON.parse(data);
                                    if (data.state == "OK"){
                                        swal("Cancelado!", data.message, "success").catch(swal.noop);
                                        $('#jqGrid').trigger( 'reloadGrid' );
                                    } else {
                                        swal("Falhou!", data.message, "error").catch(swal.noop);
                                    }
                                },//Fim success
                                error : function() {
                                    swal("Erro!", "Falha ao cancelar registro.", "error").catch(swal.noop);
                                }
                            }); //Fim ajax
                        }).catch(swal.noop); // esse catch evita erro no console do browser
                    },
                    position: "last",
                    title:"Cancelar requerimento",
                    cursor: "pointer"
                }).navButtonAdd("#jqGridPager", { //VISUALIZAR REQUERIMENTO
                    caption:"",
                    buttonicon:"fa fa-info-circle",
                    onClickButton: function () {
                        var rowKey = getSelectedRow();
                        if (rowKey) {
                            visualizarRequerimento(rowKey, $('#visualizarRequerimento'));
                        } else {
                            swal("Selecione um requerimento!", "Nenhum requerimento selecionado!", "warning").catch(swal.noop); // esse catch evita erro no console do browser
                        }
                    },
                    position: "last",
                    title:"Visualizar requerimento",
                    cursor: "pointer"
                }); //---** FIM DO JQGRID REQUERIMENTO **---

                // the event handler on expanding parent row receives two parameters
                // the ID of the grid tow  and the primary key of the row
                function showChildGrid(parentRowID, parentRowKey) {
                    var childGridID = parentRowID + "_table";
                    var childGridPagerID = parentRowID + "_pager";

                    // send the parent row primary key to the server so that we know which grid to show
//                    var childGridURL = parentRowKey+".json";
                    //childGridURL = childGridURL + "&parentRowID=" + encodeURIComponent(parentRowKey)

                    // add a table and pager HTML elements to the parent grid row - we will render the child grid here
                    $('#' + parentRowID).append('<table id=' + childGridID + '></table><div id=' + childGridPagerID + ' class=scroll></div>');

                    var cellMotivo = $("#jqGrid").jqGrid('getCell', parentRowKey, 'motivo');
                    var motivoId = 0;

                    for(var i = 0, len = motivoList.length; i < len; i++) {
                        if (motivoList[i].descricao == cellMotivo) {
                            motivoId = motivoList[i].id;
                            break;
                        }
                    }

                    var colModelReq = [];
                    var url = "/ProjetoJSP/requerimento/findDisciplinas?requerimentoId=" + parentRowKey;
                    if (motivoId > 0) {
                        if (motivoId == 9) { // segunda chamada
                            colModelReq = [
                                { label: 'Id', name: 'id', index: 'id', key: true, width: 75 },
                                { label: 'Professor', name: 'professor', index: 'professor', jsonmap: 'professor.nome', width: 300 },
                                { label: 'Data Prova', name: 'dataProva', index: 'dataProva', width: 100,
                                    sorttype: 'date',
                                    formatter: 'date',
                                    formatoptions: { srcformat: "ISO8601Long", newformat: "d/m/Y" }
                                },
                                { label: 'Disciplina', name: 'disciplina', index: 'disciplina', jsonmap: 'disciplina.nome', width: 300 },
                                { label: 'Curso', name: 'curso', index: 'curso', jsonmap: 'disciplina.curso.usuario.nome', width: 300 }
                            ];
                        } else if (motivoId == 21) { //convalidacao
                            url = "/ProjetoJSP/requerimento/findConvalidacao?requerimentoId=" + parentRowKey;
                            colModelReq = [
                                { label: 'Id', name: 'id', index: 'id', key: true, width: 75 },
                                { label: 'Disciplina', name: 'disciplinaUtfpr', index: 'disciplinaUtfpr', jsonmap: 'disciplinaUtfpr.nome', width: 300 },
                                { label: 'Disciplina', name: 'disciplinaConvalidacao', index: 'disciplinaConvalidacao', width: 250},
                                { label: 'CH', name: 'cargaHoraria', index: 'cargaHoraria', width: 100 },
                                { label: 'Nota', name: 'nota', index: 'nota', width: 100 },
                                { label: 'Freq', name: 'frequencia', index: 'frequencia', width: 100 },
                                { label: 'Nota Final', name: 'notaFinal', index: 'notaFinal', width: 100 },
                                { label: 'Freq Final', name: 'freqFinal', index: 'freqFinal', width: 100 },
                                { label: 'Dispensado', name: 'dispensado', index: 'dispensado', width: 100 },
                                { label: 'Curso', name: 'curso', index: 'curso', jsonmap: 'disciplinaUtfpr.curso.usuario.nome', width: 300 }
                            ];
                        } else {
                            colModelReq = [
                                { label: 'Id', name: 'id', index: 'id', key: true, width: 75 },
                                { label: 'Disciplina', name: 'disciplina', index: 'disciplina', jsonmap: 'disciplina.nome', width: 300 }
                            ];
                        }
                    }

                    $("#" + childGridID).jqGrid({
                        url : url,
                        datatype : "json",
                        mtype : 'GET',
                        rowList : [ 5, 10, 20 ],
                        viewrecords : true,
                        rowNum: 5,
                        jsonReader : {repeatitems : false},
                        colModel: colModelReq,
                        height: 'auto',
                        with: 'auto',
                        pager: "#" + childGridPagerID
                    });

                    if (motivoId == 21) {
                        $("#" + childGridID).setGroupHeaders({
                                useColSpanStyle: true,
                                groupHeaders: [
                                    { "numberOfColumns": 1, "titleText": "UTFPR", "startColumnName": "disciplinaUtfpr" },
                                    { "numberOfColumns": 4, "titleText": "CURSADA EM OUTRO CURSO/INSTITUIÇÃO", "startColumnName": "disciplinaConvalidacao" },
                                    { "numberOfColumns": 2, "titleText": "MÉDIA PONDERADA", "startColumnName": "notaFinal" },
                                    { "numberOfColumns": 1, "titleText": "DISPENSADO", "startColumnName": "dispensado" }
                                ]
                            });
                    }

                }

                function getSelectedRow() {
                    var rowKey = $("#jqGrid").jqGrid('getGridParam',"selrow");
                    return rowKey;
                }

                $(window).trigger('resize'); // para redimesionar
            });

        </script>
    </jsp:attribute>
    <jsp:body>
        <div class="container-fluid">
            <h3>Lista de Requerimentos</h3>
            <hr>
            <div>
                <table id="jqGrid"></table>
                <div id="jqGridPager"></div>
            </div>
        </div>

        <div id="visualizarRequerimento"></div>
    </jsp:body>
</layout:template>