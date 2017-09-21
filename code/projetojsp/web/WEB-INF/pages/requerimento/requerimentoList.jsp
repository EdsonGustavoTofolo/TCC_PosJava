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

                $("#jqGrid").jqGrid({
                    url: 'http://trirand.com/blog/phpjqgrid/examples/jsonp/getjsonp.php?callback=?&qwery=longorders',
                    mtype: "GET",
                    styleUI : 'Bootstrap',
                    datatype: "jsonp",
                    colModel: [
                        { label: 'OrderID', name: 'OrderID', key: true, width: 75 },
                        { label: 'Customer ID', name: 'CustomerID', width: 150 },
                        { label: 'Order Date', name: 'OrderDate', width: 150 },
                        { label: 'Freight', name: 'Freight', width: 150 },
                        { label:'Ship Name', name: 'ShipName', width: 150 }
                    ],
                    viewrecords: true,
                    height: 250,
                    rowNum: 20,
                    pager: "#jqGridPager"
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