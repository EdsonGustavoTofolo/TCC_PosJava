<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/layout" prefix="layout" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="frm" uri="http://www.springframework.org/tags/form"%>

<layout:template>
    <jsp:attribute name="cssEspecificos"></jsp:attribute>
    <jsp:attribute name="scriptsEspecificos">
        <script type="text/javascript" src="<c:url value="/webjars/jquery-maskedinput/1.4.0/jquery.maskedinput.min.js"/> "></script>
        <script type="text/javascript" src="<c:url value="/webjars/jquery-validation/1.13.0/jquery.validate.min.js"/> "></script>
        <script type="text/javascript" src="<c:url value="/resources/js/pt_br_messages_jquery.js"/> "></script>
        <script type="text/javascript" src="<c:url value="/resources/js/confirmDadosUsuario/confirmaDadosUsuario.js"/> "></script>
    </jsp:attribute>
    <jsp:body>
        <div class="container-fluid">
            <h3>Conclusão de Cadastro</h3>
            <hr>
            <div class="row pt-3 container">
                <spring:url value="/usuario/gravar/" var="actUrl" />

                <frm:form id="frm" role="form" method="post" modelAttribute="usuarioForm" action="${actUrl}" autocomplete="off">
                    <input id="id" name="id" hidden type="text" value="${usuario.id}" />
                    <input id="dataCadastro" name="dataCadastro" hidden type="date" value="${usuario.dataCadastro}" />
                    <input id="senha" name="senha" hidden type="text" value="${usuario.senha}"/>
                    <div class="form-group">
                        <label for="codigo">Código</label>
                        <input id="codigo" name="codigo" type="text" class="form-control" required
                            value="${usuario.codigo}">
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
                        <input id="curso" name="curso" type="text" class="form-control" required
                               value="${usuario.curso}">
                    </div>

                    <button type="reset" class="btn btn-default">Limpar</button>
                    <button type="submit" class="btn btn-primary">Salvar</button>
                </frm:form>
            </div>
        </div>
    </jsp:body>
</layout:template>