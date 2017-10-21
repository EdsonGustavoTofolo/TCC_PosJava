<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/layout" prefix="layout" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="frm" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<layout:template>
    <jsp:attribute name="cssEspecificos">
        <link rel="stylesheet" type="text/css" href="<c:url value="/webjars/select2/4.0.3/dist/css/select2.min.css"/> " />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/select2-v3/select2-bootstrap.min.css"/> " />
        <link rel="stylesheet" type="text/css" href="<c:url value="/webjars/bootstrap-datepicker/1.7.1/css/bootstrap-datepicker3.min.css"/> " />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/dualListbox/bootstrap-duallistbox.min.css"/> " />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/dropzone/basic.css"/> " />
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/dropzone/dropzone.css"/> " />
        <style type="text/css">
            .dropzone {
                min-height: 140px;
                border: 1px dashed #0087F7;
                background: white;
                padding: 20px 20px;
            }
        </style>
    </jsp:attribute>
    <jsp:attribute name="scriptsEspecificos">
        <script type="text/javascript" src="<c:url value="/webjars/select2/4.0.3/dist/js/select2.full.min.js"/> "></script>
        <script type="text/javascript" src="<c:url value="/webjars/jquery-validation/1.13.0/jquery.validate.min.js"/> "></script>
        <script type="text/javascript" src="<c:url value="/resources/js/pt_br_messages_jquery.js"/> "></script>
        <script type="text/javascript" src="<c:url value="/webjars/jquery-maskedinput/1.4.0/jquery.maskedinput.min.js"/> "></script>
        <script type="text/javascript" src="<c:url value="/webjars/bootstrap-datepicker/1.7.1/js/bootstrap-datepicker.min.js"/> "></script>
        <script type="text/javascript" src="<c:url value="/webjars/bootstrap-datepicker/1.7.1/locales/bootstrap-datepicker.pt-BR.min.js"/> "></script>
        <script type="text/javascript" src="<c:url value="/resources/js/requerimento/requerimento.js"/> "></script>
        <script type="text/javascript" src="<c:url value="/resources/js/dualListbox/jquery.bootstrap-duallistbox.min.js"/> "></script>
        <script type="text/javascript" src="<c:url value="/resources/js/dropzone/dropzone.js"/> "></script>
    </jsp:attribute>
    <jsp:body>
        <sec:authorize access="hasAnyRole('COORDENACAO', 'DERAC')">
            <c:set value="disabled" var="disabledFields" scope="page" />
        </sec:authorize>
        <div class="container-fluid">
            <h3>Requerimento <small>${id}</small></h3>
            <hr>
            <div class="row pt-3 container">
                <spring:url value="/requerimento/salvar" var="requerimentoActUrl" />

                <frm:form id="frm" name="frm" method="post" modelAttribute="requerimentoForm" action="${requerimentoActUrl}"
                            enctype="multipart/form-data" autocomplete="off">
                    <input id="id" name="id" type="text" value="${id}" hidden/>
                    <c:if test="${id > 0}">
                        <sec:authorize access="hasAnyRole('COORDENACAO', 'DERAC')">
                            <div class="form-group col-lg-12 col-md-12 col-sm-12">
                                <label for="alunoNome">Aluno:</label>
                                <input id="alunoNome" type="text" value="${alunoNome}" class="form-control" disabled/>
                            </div>
                            <div class="form-group col-lg-12 col-md-12 col-sm-12">
                                <label for="status">Status:</label>
                                <select name="status" id="status" class="form-control">
                                    <c:forEach items="${statuses}" var="status">
                                        <option value="${status.id}" ${status.id == requerimento.status.ordinal() ? 'selected="selected"' : ''}>
                                                ${status.descricao}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </sec:authorize>
                    </c:if>
                    <div class="form-group col-lg-12 col-md-12 col-sm-12">
                        <label for="motivo">Motivo do Requerimento:</label>
                        <select name="motivo" id="motivo" class="form-control" ${disabledFields}>
                            <c:forEach items="${motivos}" var="motivo">
                                <option value="${motivo.id}" ${motivo.id == requerimento.motivo ? 'selected="selected"' : ''}>
                                    ${motivo.descricao}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group col-lg-12 col-md-12 col-sm-12">
                        <label for="curso">Curso:</label>
                        <select id="curso" name="curso" class="form-control" ${disabledFields}>
                            <c:forEach items="${cursos}" var="curso">
                                <option value="${curso.id}" ${curso.id == cursoId ? 'selected="selected"' : ''}>
                                        ${curso.usuario.nome}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div id="motivoDisciplinas" class="form-group col-lg-12 col-md-12 col-sm-12
                            ${requerimento.motivo == 5 || requerimento.motivo == 15 || requerimento.motivo == 17 ? '' : 'hidden'}">
                        <hr>
                        <select id="disciplinas" class="form-control dual_select" ${disabledFields} multiple>
                            <c:forEach items="${disciplinas}" var="disciplina">
                                <option value="${disciplina.id}">
                                        ${disciplina.codigo} - ${disciplina.nome}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div id="motivo9" class="form-group col-lg-12 col-md-12 col-sm-12  ${requerimento.motivo == 9 ? '' : 'hidden'}">
                        <hr>
                        <div class="form-group">
                            <label for="disciplina">Disciplina:</label>
                            <select id="disciplina" name="disciplina" class="form-control" ${disabledFields}>
                                <c:forEach items="${disciplinas}" var="disciplina">
                                    <option value="${disciplina.id}"
                                                ${requerimento.motivo == 9 && disciplina.id == requerimento.disciplinas.get(0).disciplina.id ? 'selected="selected"' : ''}>
                                            ${disciplina.nome}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="professor">Professor:</label>
                            <select id="professor" name="professor" class="form-control" ${disabledFields}>
                                <c:forEach items="${professores}" var="professor">
                                    <option value="${professor.id}"
                                        ${requerimento.motivo == 9 && professor.id == requerimento.disciplinas.get(0).professor.id ? 'selected="selected"' : ''}>
                                            ${professor.nome}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="data">Data:</label>
                            <div class="input-group date">
                                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                <input id="data" name="data" type="text" class="form-control" ${disabledFields}
                                       value='<fmt:formatDate value="${requerimento.motivo == 9 ? requerimento.disciplinas.get(0).dataProva : ''}"
                                       pattern="dd/MM/yyyy" />'/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group col-lg-12 col-md-12 col-sm-12">
                        <label for="observacao">Observações:</label>
                        <textarea id="observacao" name="observacao" class="form-control" rows="5" ${disabledFields}>${requerimento.observacao}</textarea>
                    </div>
                    <c:if test="${requerimento.anexos.size() > 0}">
                        <div id="requerimentoAnexos" class="form-group col-lg-12 col-md-12 col-sm-12">
                            <label>Documentos Anexados:</label><br/>
                            <div class="attached">
                                <c:forEach items="${requerimento.anexos}" var="anexo">
                                    <div id="anexo${anexo.id}" class="dz-preview dz-file-preview">
                                        <div class="dz-image"><img data-dz-thumbnail=""></div>
                                        <div class="dz-details">
                                            <div class="dz-size">
                                                <span><strong>${anexo.size}</strong> ${anexo.unitSize}</span>
                                            </div>
                                            <div class="dz-filename">
                                                <span>${anexo.nome}</span>
                                            </div>
                                        </div>
                                        <div class="dz-download" style="${disabledFields == 'disabled' ? 'top: 80%;' : ''}">
                                            <a href="<c:url value="/requerimento/anexos/download/${anexo.id}"/>">Download</a>
                                        </div>
                                        <sec:authorize access="hasRole('ALUNO')">
                                            <a href="#" onclick="deleteAnexo(${anexo.id})" class="dz-remove">Excluir</a>
                                        </sec:authorize>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </c:if>
                    <sec:authorize access="hasRole('ALUNO')">
                        <div class="form-group col-lg-12 col-md-12 col-sm-12">
                            <label for="file">Anexar Documentos:</label>
                            <div action="#" class="dropzone" id="dropzoneForm">
                                <div class="fallback">
                                    <input id="file" name="file" type="file" multiple />
                                </div>
                            </div>
                        </div>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ALUNO')">
                        <button type="reset" class="btn btn-default">Limpar</button>
                        <button id="salvar" type="submit" class="btn btn-primary">Salvar</button>
                    </sec:authorize>
                </frm:form>
            </div>
        </div>
    </jsp:body>
</layout:template>