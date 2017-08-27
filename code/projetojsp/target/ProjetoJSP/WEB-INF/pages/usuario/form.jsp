<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/layout" prefix="layout" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="frm" uri="http://www.springframework.org/tags/form"%>

<layout:template>
    <jsp:attribute name="cssEspecificos"></jsp:attribute>
    <jsp:attribute name="scriptsEspecificos"></jsp:attribute>
    <jsp:body>
        <div class="container-fluid">
            <h3>Cadastro de Usuário</h3>
            <hr>
            <div class="row pt-3 container">
                <spring:url value="/usuario/" var="actUrl" />

                <frm:form id="frm" method="post" modelAttribute="usuarioForm" action="${actUrl}" autocomplete="off">

                    <button type="reset" class="btn btn-default">Limpar</button>
                    <button type="submit" class="btn btn-primary">Salvar</button>
                </frm:form>
            </div>
        </div>
    </jsp:body>
</layout:template>