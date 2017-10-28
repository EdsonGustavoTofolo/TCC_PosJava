<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="frm" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="Dashboard">
    <meta name="keyword" content="Dashboard, Bootstrap, Admin, Template, Theme, Responsive, Fluid, Retina">

    <title>UTFPR-PB - Requerimentos/Novo Usuário</title>

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

<div class="middle-box text-center loginscreen animated fadeInDown">
    <div>
        <div>
            <h1 class="logo-name">UT+</h1>
        </div>
        <!-- h3>UTFPRPB/REQ+</h3 -->
        <p>Crie uma conta para poder acessar seus requerimentos.</p>
        <spring:url value="/usuario/criarNovoUsuario/" var="actUrl" />
        <form id="frm" class="m-t" role="form" action="${actUrl}" method="post" autocomplete="off">
            <div class="form-group">
                <input id="username" name="username" type="text" class="form-control" placeholder="Informe o login" required>
            </div>
            <div class="form-group">
                <input id="nome" name="nome" type="text" class="form-control" placeholder="Informe seu Nome" required>
            </div>
            <div class="form-group">
                <input id="email" name="email" type="email" class="form-control" placeholder="Informe um E-mail" required>
            </div>
            <div class="form-group">
                <input id="senha" name="senha" type="password" class="form-control" placeholder="Informe uma Senha" required>
            </div>
            <button type="submit" class="btn btn-primary block full-width m-b">Cadastrar</button>

            <p class="text-muted text-center"><small>Já tem uma conta?</small></p>
            <a class="btn btn-sm btn-white btn-block" href="<c:url value="/login"/>">Login</a>
        </form>
        <!-- p class="m-t"> <small>Inspinia we app framework base on Bootstrap 3 &copy; 2014</small> </p -->
    </div>
</div>

<spring:url value="/usuario/validarUsername/" var="urlValidarUsername" />
<!-- js placed at the end of the document so the pages load faster -->
<script type="text/javascript" src="<c:url value="/webjars/jquery/1.11.1/jquery.min.js"/> "></script>
<script type="text/javascript" src="<c:url value="/webjars/bootstrap/3.3.7/js/bootstrap.min.js"/> "></script>
<script type="text/javascript" src="<c:url value="/webjars/jquery-maskedinput/1.4.0/jquery.maskedinput.min.js"/> "></script>
<script type="text/javascript" src="<c:url value="/webjars/jquery-validation/1.13.0/jquery.validate.min.js"/> "></script>
<script type="text/javascript" src="<c:url value="/resources/js/pt_br_messages_jquery.js"/> "></script>
<script>
    $(document).ready(function () {
        $("#username").focus();
    });

    $("#frm").validate({
        rules: {
            username: {
                required: true,
                number: true,
                remote: {
                    url: '${urlValidarUsername}',
                    type: "post"
                },
            },
            nome: {
                required: true
            },
            email: {
                required: true,
                email: true
            },
            senha: {
                required: true
            }
        },
        messages: {
            username: {
                remote: "Login informado em uso!"
            }
        }
    });
</script>

</body>
</html>