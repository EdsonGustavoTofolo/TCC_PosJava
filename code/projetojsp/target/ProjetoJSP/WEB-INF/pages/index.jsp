<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout"%>

<layout:template>
  <jsp:body>
    <h1>Bem vindo!!!</h1>
    <c:if test="${mensagem != null && !mensagem.equals('')}">
      <div class="alert alert-warning alert-dismissable">
        <button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>
        <a href="<c:url value="/usuario/conta/"/> ">
          ${mensagem}
        </a>
      </div>
    </c:if>
  </jsp:body>
</layout:template>