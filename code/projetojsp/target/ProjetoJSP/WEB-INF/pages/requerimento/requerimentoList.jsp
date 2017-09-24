<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/layout" prefix="layout" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="frm" uri="http://www.springframework.org/tags/form"%>

<layout:template>
    <jsp:attribute name="cssEspecificos">
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/jqgrid/ui.jqgrid-bootstrap.css"/> " />
    </jsp:attribute>
    <jsp:attribute name="scriptsEspecificos">
        <script type="text/javascript" src="<c:url value="/resources/js/jqgrid/grid.locale-pt-br.js"/> "></script>
        <script type="text/javascript" src="<c:url value="/resources/js/jqgrid/jquery.jqGrid.min.js"/> "></script>
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
                    colNames : [ 'Name','Alias','Super Power'],
                    colModel : [ {
                        name : 'name',
                        index : 'name',
                        width : 150
                    }, {
                        name : 'alias',
                        index : 'alias',
                        width : 150,
                        editable : false
                    }, {
                        name : 'power',
                        index : 'power',
                        width : 550,
                        editable : false
                    }],
                    pager : '#pager',
                    rowNum : 10,
                    height: 'auto',
                    rowList : [ 10 ],
                    sortname : 'invid',
                    sortorder : 'desc',
                    viewrecords : true,
                    gridview : true,
                    multiselect: true,
                    multiboxonly: false,
                    caption : 'Super Heroes',
                    jsonReader : {
                        repeatitems : false,
                    }
                });
                jQuery("#jqGrid").jqGrid('navGrid', '#pager', {
                    edit : false,
                    add : false,
                    del : false,
                    search : false
                });


                $('#showSelected').on('click',function(){

                    var selRowArr = jQuery("#jqGrid").getGridParam('selarrrow');
                    var selectedAppIds = [];
                    for(var i=0;i<selRowArr.length;i++){
                        var celValue =  $('#jqGrid').jqGrid('getCell', selRowArr[i], 'alias');
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
                <div style="margin-left:20px">
                    <table id="jqGrid"></table>
                    <div id="jqGridPager"></div>
                </div>
            </div>
        </div>
    </jsp:body>
</layout:template>