<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/layout" prefix="layout" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="frm" uri="http://www.springframework.org/tags/form"%>

<layout:template>
    <jsp:attribute name="cssEspecificos">
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/jqgrid/ui.jqgrid-bootstrap.css"/> " />
        <link rel="stylesheet" type="text/css" href="<c:url value="/webjars/bootstrap-datepicker/1.0.1/css/datepicker.css"/> " />
    </jsp:attribute>
    <jsp:attribute name="scriptsEspecificos">
        <script type="text/javascript" src="<c:url value="/resources/js/jqgrid/grid.locale-pt-br.js"/> "></script>
        <script type="text/javascript" src="<c:url value="/resources/js/jqgrid/jquery.jqGrid.min.js"/> "></script>
        <script type="text/javascript" src="<c:url value="/webjars/jquery-maskedinput/1.4.0/jquery.maskedinput.min.js"/> "></script>
        <script type="text/javascript" src="<c:url value="/webjars/bootstrap-datepicker/1.0.1/js/bootstrap-datepicker.js"/> "></script>
        <script type="text/javascript" src="<c:url value="/webjars/bootstrap-datepicker/1.0.1/js/locales/bootstrap-datepicker.pt-BR.js"/> "></script>
        <script type="text/javascript">
            $(document).ready(function () {
                $.jgrid.defaults.width = 1000;
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
                        width : 200,
                        sorttype: 'date',
                        formatter: 'date',
//                        srcformat: 'Y-m-d',
//                        newformat: 'n/j/Y',
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
                    rowList : [ 10, 20, 30 ],
//                    sortname : 'invid',
//                    sortorder : 'desc',
                    viewrecords : true,
//                    gridview : true,
//                    multiselect: true,
//                    multiboxonly: false,
                    caption : 'Requerimentos',
                    jsonReader : {
                        repeatitems : false,
                    }
                });
                // activate the toolbar searching
                $('#jqGrid').jqGrid('filterToolbar');
                $("#jqGrid").jqGrid('navGrid', '#jqGridPager', {
                    edit : false,
                    add : false,
                    del : false,
                    search : false
                });


                $('#showSelected').on('click',function(){

                    var selRowArr = jQuery("#jqGrid").getGridParam('selarrrow');
                    var selectedAppIds = [];
                    for(var i=0;i<selRowArr.length;i++){
                        var celValue =  $('#jqGrid').jqGrid('getCell', selRowArr[i], 'id');
                        selectedAppIds.push(celValue);
                    }
                    alert(selectedAppIds);
                    $('#jqGrid').trigger( 'reloadGrid' );


                });
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