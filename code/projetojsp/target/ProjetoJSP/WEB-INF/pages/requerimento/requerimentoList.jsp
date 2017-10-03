<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/layout" prefix="layout" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="frm" uri="http://www.springframework.org/tags/form"%>
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
                        width : 75
                    }, {
                        label : 'Motivo',
                        name : 'motivo',
                        index : 'motivo',
                        width : 150,
                    }, {
                        label : 'Observação',
                        name : 'observacao',
                        index : 'observacao',
                        width : 650,
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
                    rowList : [ 10, 20, 30 ],
                    viewrecords : true,
                    caption : 'Requerimentos',
                    jsonReader : {
                        repeatitems : false,
                    }
                });
                // activate the toolbar searching
                $('#jqGrid').jqGrid('filterToolbar');
                $("#jqGrid").navGrid('#jqGridPager',
                    { edit: false, add: false, del: false, search: false, refresh: true, view: false, position: "left", cloneToTop: false },
                    {}, {}
                ).navButtonAdd("#jqGridPager", { //NOVO REQUERIMENTO
                    caption:"",
                    buttonicon:"glyphicon glyphicon-plus",
                    onClickButton: function () {
                        window.location.href = "/ProjetoJSP/requerimento/";
                    }, 
                    position: "last", 
                    title:"Novo requerimento",
                    cursor: "pointer"  
                }).navButtonAdd("#jqGridPager", { //EDITAR REQUERIMENTO
                    caption:"",
                    buttonicon:"glyphicon glyphicon-edit",
                    onClickButton: function () {
                        rowKey = getSelectedRow();
                        if (rowKey) {
                            window.location.href = "/ProjetoJSP/requerimento/edit/" + getSelectedRow();
                        } else {
                            swal("Selecione um requerimento!", "Nenhum requerimento selecionado!", "warning");
                        }
                    },
                    position: "last",
                    title:"Editar requerimento",
                    cursor: "pointer"
                }).navButtonAdd("#jqGridPager", { //EXCLUIR REQUERIMENTO
                    caption:"",
                    buttonicon:"glyphicon glyphicon-trash",
                    onClickButton: function () {
                        swal({
                            title: 'Confirma a remoção do registro?!',
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
                                        swal("Removido!", data.message, "success");
                                        $('#jqGrid').trigger( 'reloadGrid' );
                                    } else {
                                        swal("Falhou!", data.message, "error");
                                    }
                                },//Fim success
                                error : function() {
                                    swal("Erro!", "Falha ao remover registro.", "error");
                                }
                            }); //Fim ajax
                        });
                    },
                    position: "last",
                    title:"Excluir requerimento",
                    cursor: "pointer"
                });

                function getSelectedRow() {
                    var grid = $("#jqGrid");
                    var rowKey = grid.jqGrid('getGridParam',"selrow");
                    return rowKey;
                }
            });

        </script>
    </jsp:attribute>
    <jsp:body>
        <div class="container-fluid">
            <h3>Lista de Requerimentos</h3>
            <hr>
            <div class="row pt-3 container">
                <div class="col-lg-12">
                    <table id="jqGrid"></table>
                    <div id="jqGridPager"></div>
                </div>
            </div>
        </div>
    </jsp:body>
</layout:template>