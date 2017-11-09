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
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/jqgrid/ui.jqgrid-bootstrap.css"/> " />
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
        <script type="text/javascript" src="<c:url value="/resources/js/jquery.regex-limit.js"/> "></script>
        <script type="text/javascript" src="<c:url value="/webjars/select2/4.0.3/dist/js/select2.full.min.js"/> "></script>
        <script type="text/javascript" src="<c:url value="/webjars/jquery-validation/1.13.0/jquery.validate.min.js"/> "></script>
        <script type="text/javascript" src="<c:url value="/resources/js/pt_br_messages_jquery.js"/> "></script>
        <script type="text/javascript" src="<c:url value="/webjars/jquery-maskedinput/1.4.0/jquery.maskedinput.min.js"/> "></script>
        <script type="text/javascript" src="<c:url value="/webjars/bootstrap-datepicker/1.7.1/js/bootstrap-datepicker.min.js"/> "></script>
        <script type="text/javascript" src="<c:url value="/webjars/bootstrap-datepicker/1.7.1/locales/bootstrap-datepicker.pt-BR.min.js"/> "></script>
        <script type="text/javascript" src="<c:url value="/resources/js/requerimento/requerimentoChangeStatus.js"/> "></script>
        <script type="text/javascript" src="<c:url value="/resources/js/requerimento/requerimento.js"/> "></script>
        <script type="text/javascript" src="<c:url value="/resources/js/dualListbox/jquery.bootstrap-duallistbox.min.js"/> "></script>
        <script type="text/javascript" src="<c:url value="/resources/js/dropzone/dropzone.js"/> "></script>
    </jsp:attribute>
    <jsp:body>
        <sec:authorize access="hasAnyRole('COORDENACAO', 'DERAC', 'PROFESSOR')">
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
                        <label for="curso">Curso:</label>
                        <select id="curso" name="curso" class="form-control" ${disabledFields}>
                            <c:forEach items="${cursos}" var="curso">
                                <option value="${curso.id}" ${curso.id == cursoId ? 'selected="selected"' : ''}>
                                        ${curso.usuario.nome}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
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
                                            ${disciplina.codigo} - ${disciplina.nome}
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
                    <div id="motivo21" class="form-group col-lg-12 col-md-12 col-sm-12  ${requerimento.motivo == 21 ? '' : 'hidden'}">
                        <hr>
                        <div class="form-group">
                            <div class="ui-jqgrid-view table-responsive">
                                <table id="disciplinasConvalidacao" class="ui-jqgrid-btable ui-common-table table table-bordered">
                                    <thead align="center">
                                        <tr class="ui-jqgrid-labels">
                                            <th class="ui-th-column ui-th-ltr" colspan="1">UTFPR</th>
                                            <th class="ui-th-column ui-th-ltr" colspan="4">CURSADA EM OUTRO CURSO/INSTITUIÇÃO</th>
                                            <th class="ui-th-column ui-th-ltr" colspan="2">MÉDIA PONDERADA</th>
                                            <th class="ui-th-column ui-th-ltr" colspan="1">DISPENSADO</th>
                                            <sec:authorize access="hasAnyRole('ALUNO', 'PROFESSOR', 'COORDENACAO')">
                                                <th class="ui-th-column ui-th-ltr" colspan="1" rowspan="2" width="65px">AÇÕES</th>
                                            </sec:authorize>
                                        </tr>
                                        <tr class="ui-jqgrid-labels">
                                            <th class="ui-th-column ui-th-ltr" width="250px">Disciplina</th>
                                            <th class="ui-th-column ui-th-ltr" width="250px">Disciplina</th>
                                            <th class="ui-th-column ui-th-ltr" title="Carga Horária">CH</th>
                                            <th class="ui-th-column ui-th-ltr">Nota</th>
                                            <th class="ui-th-column ui-th-ltr" title="% Frequência">Freq</th>
                                            <th class="ui-th-column ui-th-ltr" width="65px">Nota Final</th>
                                            <th class="ui-th-column ui-th-ltr" width="65px" title="% Frequência Final">Freq Final</th>
                                            <th class="ui-th-column ui-th-ltr">Sim/Não</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:choose>
                                        <c:when test="${requerimento.convalidacoes.size() > 0}">
                                            <c:forEach items="${requerimento.convalidacoes}" var="convalidacao">
                                                <tr class="itemConvalidacao edited" id="itemConvalidacao${convalidacao.id}">
                                                    <td class="disciplinaUtfpr">
                                                        <select name="disciplinaUtfpr${convalidacao.id}" class="form-control" ${disabledFields}>
                                                            <c:forEach items="${disciplinas}" var="disciplina">
                                                                <option value="${disciplina.id}"
                                                                    ${requerimento.motivo == 21 && disciplina.id == convalidacao.disciplinaUtfpr.id ? 'selected="selected"' : ''}>
                                                                        ${disciplina.codigo} - ${disciplina.nome}
                                                                </option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td class="disciplinaConvalidacao">
                                                        <input name="disciplinaConvalidacao${convalidacao.id}" value="${convalidacao.disciplinaConvalidacao}" ${disabledFields} type="text" class="form-control" />
                                                    </td>
                                                    <td class="cargaHoraria">
                                                        <input name="cargaHoraria${convalidacao.id}" value="${convalidacao.cargaHoraria}" ${disabledFields} type="text" class="form-control" />
                                                    </td>
                                                    <td class="nota">
                                                        <input name="nota${convalidacao.id}" value="${convalidacao.nota}" ${disabledFields} type="text" class="form-control" />
                                                    </td>
                                                    <td class="frequencia">
                                                        <input name="frequencia${convalidacao.id}" value="${convalidacao.frequencia}" ${disabledFields} type="text" class="form-control" />
                                                    </td>
                                                    <td class="notaFinal">
                                                        <input name="notaFinal${convalidacao.id}" value="${convalidacao.notaFinal}" ${disabledFields} type="text" class="form-control" />
                                                    </td>
                                                    <td class="freqFinal">
                                                        <input name="freqFinal${convalidacao.id}" value="${convalidacao.freqFinal}" ${disabledFields} type="text" class="form-control" />
                                                    </td>
                                                    <td class="dispensado">
                                                        <select class="form-control" ${disabledFields}>
                                                            <option value="true" ${convalidacao.dispensado ? 'selected="selected"' : ''}>Sim</option>
                                                            <option value="false" ${not convalidacao.dispensado ? 'selected="selected"' : ''}>Não</option>
                                                        </select>
                                                    </td>
                                                    <sec:authorize access="hasAnyRole('ALUNO', 'PROFESSOR', 'COORDENACAO')">
                                                        <td>
                                                            <div class="acoes">
                                                                <sec:authorize access="hasRole('ALUNO')">
                                                                    <div title="Adicionar" onclick="adicionarItemConvalidacao();" class="ui-pg-div ui-inline-edit" style="float: left;cursor: pointer;">
                                                                        <span class="fa fa-plus"></span>
                                                                    </div>
                                                                    <c:if test="${requerimento.convalidacoes.size() > 1}">
                                                                        <div title="Excluir" onclick="excluirItemConvalidacao(this);" class="ui-pg-div ui-inline-del" style="float: left;cursor: pointer;">
                                                                            <span class="fa fa-trash"></span>
                                                                        </div>
                                                                    </c:if>
                                                                </sec:authorize>
                                                                <sec:authorize access="hasRole('PROFESSOR')">
                                                                    <div title="Parecer" onclick="parecerItemConvalidacao(${convalidacao.id});" class="ui-pg-div ui-inline-del" style="float: left;cursor: pointer;">
                                                                        <span class="fa fa-pencil-square-o"></span>
                                                                    </div>
                                                                </sec:authorize>
                                                                <sec:authorize access="hasRole('COORDENACAO')">
                                                                    <div title="${convalidacao.professor == null ? 'Definir professor' : ''}"
                                                                         data-toggle="tooltip"
                                                                         data-placement="left"
                                                                         data-original-title="${convalidacao.professor != null ? convalidacao.professor.nome : ''}"
                                                                         id="iconItemConvalidacao${convalidacao.id}"
                                                                         onclick="definirProfessorItemConvalidacao(${convalidacao.id}, ${convalidacao.professor.id});"
                                                                         class="ui-pg-div ui-inline-del"
                                                                         style="float: left;cursor: pointer;">
                                                                        <c:choose>
                                                                            <c:when test="${convalidacao.professor != null}">
                                                                                <span class="fa-stack fa-lg">
                                                                                  <i class="fa fa-user"></i>
                                                                                  <i class="fa fa-pencil-square-o fa-stack-1x"></i>
                                                                                </span>
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                <span class="fa fa-user-plus"></span>
                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                    </div>
                                                                </sec:authorize>
                                                            </div>
                                                        </td>
                                                    </sec:authorize>
                                                </tr>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <tr class="itemConvalidacao">
                                                <td class="disciplinaUtfpr"><select name="disciplinaUtfpr" class="form-control"></select></td>
                                                <td class="disciplinaConvalidacao"><input name="disciplinaConvalidacao" type="text" class="form-control" /></td>
                                                <td class="cargaHoraria"><input name="cargaHoraria" type="text" class="form-control" /></td>
                                                <td class="nota"><input name="nota" type="text" class="form-control" /></td>
                                                <td class="frequencia"><input name="frequencia" type="text" class="form-control" /></td>
                                                <td class="notaFinal"><input name="notaFinal" type="text" class="form-control" /></td>
                                                <td class="freqFinal"><input name="freqFinal" type="text" class="form-control" /></td>
                                                <td class="dispensado">
                                                    <select class="form-control">
                                                        <option value="true">Sim</option><option value="false">Não</option>
                                                    </select>
                                                </td>
                                                <td>
                                                    <div class="acoes">
                                                        <div title="Adicionar" onclick="adicionarItemConvalidacao();" class="ui-pg-div ui-inline-edit" style="float: left;cursor: pointer;">
                                                            <span class="fa fa-plus"></span>
                                                        </div>
                                                    </div>
                                                </td>
                                            </tr>
                                        </c:otherwise>
                                    </c:choose>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <sec:authorize access="hasRole('COORDENACAO')">
                            <div aria-hidden="true" aria-labelledby="escolherProfessorModalLbl" role="dialog" tabindex="-1" id="escolherProfessorModal" class="modal fade">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                            <h4 class="modal-title">Escolher professor</h4>
                                        </div>
                                        <div class="modal-body">
                                            <p>Escolha o professor para atribuí-lo ao item da convalidação:</p>
                                            <select id="professorItemConvalidacao" name="professorItemConvalidacao" class="form-control">
                                                <c:forEach items="${professores}" var="professor">
                                                    <option value="${professor.id}">
                                                            ${professor.nome}
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="modal-footer">
                                            <button data-dismiss="modal" class="btn btn-default" type="button">Cancelar</button>
                                            <button id="btnSalvarProfessorItemConvalidacao" class="btn btn-primary" type="button">Salvar</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </sec:authorize>
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
        <sec:authorize access="not hasRole('ALUNO')">
            <div id="requerimentoObs"></div>
        </sec:authorize>
    </jsp:body>
</layout:template>