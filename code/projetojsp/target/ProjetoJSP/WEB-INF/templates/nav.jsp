<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<spring:url value="/requerimento/" var="urlRequerimento" />
<spring:url value="/requerimento/list" var="urlRequerimentoList" />
<spring:url value="/logout" var="urlLogout" />
<spring:url value="/usuario/conta/" var="urlContaUsuario" />

<nav class="navbar navbar-default">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="<c:url value="/"/> ">UTFPR-PB</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <sec:authorize access="isAuthenticated()">
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <li class="active">
                        <a href="${urlRequerimento}">Requerimentos <span class="sr-only">(current)</span></a>
                    </li>
                    <li>
                        <a href="${urlRequerimentoList}">Lista</a>
                    </li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li><img alt="image" class="img-circle" src="<c:url value="/resources/images/user.jpg"/> " height="24"/></li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                           aria-haspopup="true" aria-expanded="false">
                            <sec:authentication property="principal.nome" />
                            <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="${urlContaUsuario}">Conta</a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="${urlLogout}">Sair</a></li>
                        </ul>
                    </li>
                </ul>
            </div><!-- /.navbar-collapse -->
        </sec:authorize>
    </div><!-- /.container-fluid -->
</nav>