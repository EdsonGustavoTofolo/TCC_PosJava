<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="Dashboard">
    <meta name="keyword" content="Dashboard, Bootstrap, Admin, Template, Theme, Responsive, Fluid, Retina">

    <title>UTFPR-PB - Requerimentos/Login</title>

    <link rel="stylesheet" type="text/css" href="<c:url value="/webjars/bootstrap/3.3.7/css/bootstrap.min.css"/> " />
    <link rel="stylesheet" type="text/css" href="<c:url value="/webjars/font-awesome/4.7.0/css/font-awesome.min.css"/> " />
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/styles.css"/> " />
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/animate.css"/> " />
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/loginStyle.css"/> " />

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>

<spring:url value="login/novoUsuario" var="actUrlNovoUsuario" />

<div class="middle-box text-center loginscreen animated fadeInDown">
    <div>
        <div>
            <h1 class="logo-name">UT+</h1>
        </div>
        <h3>Bem Vindo(a) à UTFPRPB+</h3>
        <p>Sistema para gerenciamento dos requerimentos efetuados na UTFPR no campus de Pato Braco / PR.</p>
        <p>Faça o login para ver/criar/editar seus requerimentos.</p>

        <c:if test="${error != null && !error.equals('')}">
            <div class="alert alert-danger alert-dismissable">
                <button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>
                ${error}
            </div>
        </c:if>
        <form class="m-t" role="form" action="login" method="POST" autocomplete="off">

            <div class="form-group">
                <input id="username" name="username" type="text" class="form-control" placeholder="Informe seu login" required>
            </div>
            <div class="form-group">
                <input name="password" type="password" class="form-control" placeholder="Informe a sua senha" required>
            </div>

            <button type="submit" class="btn btn-primary block full-width m-b">Login</button>

            <a data-toggle="modal" href="login.html#forgotPass"><small>Esqueceu a senha?</small></a>
            <p class="text-muted text-center"><small>Não possui conta ainda?</small></p>
            <a class="btn btn-sm btn-white btn-block" href="${actUrlNovoUsuario}">Criar uma conta</a>

            <!-- Modal -->
            <div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="forgotPass" class="modal fade">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title">Esqueceu sua senha?</h4>
                        </div>
                        <div class="modal-body">
                            <p>Informe seu e-mail abaixo para criar uma nova senha.</p>
                            <input type="email" name="email" placeholder="Email" autocomplete="off" class="form-control placeholder-no-fix">
                        </div>
                        <div class="modal-footer">
                            <button data-dismiss="modal" class="btn btn-default" type="button">Cancelar</button>
                            <button class="btn btn-primary" type="button">Enviar</button>
                        </div>
                    </div>
                </div>
            </div>
            <!-- modal -->
        </form>
        <!-- p class="m-t"> <small>Inspinia we app framework base on Bootstrap 3 &copy; 2014</small> </p -->
    </div>
</div>

<!-- js placed at the end of the document so the pages load faster -->
<script type="text/javascript" src="<c:url value="/webjars/jquery/1.11.1/jquery.min.js"/> "></script>
<script type="text/javascript" src="<c:url value="/webjars/bootstrap/3.3.7/js/bootstrap.min.js"/> "></script>
<script>
    $(document).ready(function () {
        $("#username").focus();
    });
</script>

</body>
</html>