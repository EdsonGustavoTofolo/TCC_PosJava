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
                            value: ":[Todos];1:Enviado DERAC;2:Aprovado DERAC;3:Falta Documentos;4:Recusado;5:Enviado Coordenação;6:Aprovado Coordenação;7:Finalizado"
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
                    if (cellvalue == "AGUARDANDO_DERAC") {
                        return '<span class="label label-warning">Enviado DERAC</span>'
                    } else if (cellvalue == "APROVADO_DERAC") {
                        return '<span class="label label-success">Aprovado DERAC</span>'
                    } else if (cellvalue == "FALTA_DOCUMENTOS") {
                        return '<span class="label label-danger">Falta de Documentos</span>'
                    } else if (cellvalue == "RECUSADO") {
                        return '<span class="label label-danger">Recusado</span>'
                    } else if (cellvalue == "AGUARDANDO_COORDENACAO") {
                        return '<span class="label label-warning">Enviado Coordenação</span>'
                    } else if (cellvalue == "APROVADO_COORDENACAO") {
                        return '<span class="label label-success">Aprovado Coordenação</span>'
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
                }).navButtonAdd("#jqGridPager", { //EXCLUIR REQUERIMENTO
                    caption:"",
                    buttonicon:"fa fa-trash",
                    onClickButton: function () {
                        swal({
                            title: 'Confirma a exclusão do registro?!',
                            text: "Esta ação não poderá ser desfeita!",
                            type: 'question', //warning
                            showLoaderOnConfirm: true,
                            showCancelButton: true,
                            confirmButtonColor: '#3085d6',
                            cancelButtonColor: '#d33',
                            confirmButtonText: 'Sim, excluir!!',
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
                                    /*setTimeout(function () {
                                        rowKey = getSelectedRow();
                                        if (rowKey) { resolve(); } else { reject("Nenhum requerimento selecionado!"); }
                                    }, 2000)*/
                                })
                            }
                        }).then(function () {
                            var url = '/ProjetoJSP/requerimento/delete/' + getSelectedRow();
                            $.ajax({
                                type : 'DELETE',
                                url : url,
                                success : function(data) {
                                    data = JSON.parse(data);
                                    if (data.state == "OK"){
                                        swal("Removido!", data.message, "success").catch(swal.noop);
                                        $('#jqGrid').trigger( 'reloadGrid' );
                                    } else {
                                        swal("Falhou!", data.message, "error").catch(swal.noop);
                                    }
                                },//Fim success
                                error : function() {
                                    swal("Erro!", "Falha ao remover registro.", "error").catch(swal.noop);
                                }
                            }); //Fim ajax
                        }).catch(swal.noop); // esse catch evita erro no console do browser
                    },
                    position: "last",
                    title:"Excluir requerimento",
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
                        } else {
                            colModelReq = [
                                { label: 'Id', name: 'id', index: 'id', key: true, width: 75 },
                                { label: 'Disciplina', name: 'disciplina', index: 'disciplina', jsonmap: 'disciplina.nome', width: 300 }
                            ];
                        }
                    }

                    $("#" + childGridID).jqGrid({
                        url : "/ProjetoJSP/requerimento/findDisciplinas?requerimentoId=" + parentRowKey,
                        datatype : "json",
                        mtype : 'GET',
                        page: 1,
                        rownum: 15,
                        colModel: colModelReq,
                        height: 'auto',
//                        with: '100%',
                        autoWidth: true,
                        pager: "#" + childGridPagerID
                    });

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
    </jsp:body>
</layout:template>