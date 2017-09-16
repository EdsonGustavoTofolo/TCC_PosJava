<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/layout" prefix="layout" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="frm" uri="http://www.springframework.org/tags/form"%>

<layout:template>
    <jsp:attribute name="cssEspecificos">
        <link rel="stylesheet" type="text/css" href="<c:url value="/webjars/select2/4.0.3/dist/css/select2.min.css"/> " />
    </jsp:attribute>
    <jsp:attribute name="scriptsEspecificos">
        <script type="text/javascript" src="<c:url value="/webjars/select2/4.0.3/dist/js/select2.full.min.js"/> "></script>
        <script type="text/javascript" src="<c:url value="/webjars/jquery-maskedinput/1.4.0/jquery.maskedinput.min.js"/> "></script>
        <script type="text/javascript" src="<c:url value="/webjars/jquery-validation/1.13.0/jquery.validate.min.js"/> "></script>
        <script type="text/javascript" src="<c:url value="/resources/js/pt_br_messages_jquery.js"/> "></script>
        <script type="text/javascript" src="<c:url value="/resources/js/confirmDadosUsuario/confirmaDadosUsuario.js"/> "></script>
    </jsp:attribute>
    <jsp:body>
        <div class="container-fluid">
            <h3>${titulo}</h3>
            <hr>
            <div class="row pt-3 container">
                <spring:url value="/usuario/gravar/" var="actUrl" />

                <frm:form id="frm" role="form" method="post" modelAttribute="usuarioForm" action="${actUrl}" autocomplete="off">
                    <input id="id" name="id" hidden type="text" value="${usuario.id}" />
                    <div class="form-group">
                        <label for="username">Código</label>
                        <input id="username" name="username" type="text" class="form-control" required
                            value="${usuario.username}" disabled>
                    </div>
                    <div class="form-group">
                        <label for="nome">Nome</label>
                        <input id="nome" name="nome" type="text" class="form-control" required
                            value="${usuario.nome}">
                    </div>
                    <div class="form-group">
                        <label for="email">E-mail</label>
                        <input id="email" name="email" type="email" class="form-control" required
                            value="${usuario.email}">
                    </div>
                    <div class="form-group">
                        <label for="telefone">Telefone</label>
                        <input id="telefone" name="telefone" type="text" class="form-control" required
                               value="${usuario.telefone}">
                    </div>
                    <div class="form-group">
                        <label for="curso">Curso</label>
                        <select name="curso" id="curso" class="form-control" required>
                            <option>- Selecione -</option>
                            <c:forEach items="${cursos}" var="curso">
                                <option value="${curso.id}" ${curso.id == usuario.curso.id ? 'selected="selected"' : ''}>
                                    ${curso.usuario.nome}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <button type="reset" class="btn btn-default">Limpar</button>
                    <button type="submit" class="btn btn-primary">Salvar</button>
                </frm:form>
            </div>
        </div>
    </jsp:body>
</layout:template>