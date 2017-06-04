<%@ tag pageEncoding="utf-8"%>

<%@ attribute name="cssEspecificos" fragment="true" required="false"%>
<%@ attribute name="scriptsEspecificos" fragment="true" required="false"%>

<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="Dashboard">
    <meta name="keyword" content="Dashboard, Bootstrap, Admin, Template, Theme, Responsive, Fluid, Retina">

    <title>TESTANDU</title>

    <link rel="stylesheet" type="text/css" href="<c:url value="/webjars/bootstrap/3.3.7/css/bootstrap.min.css"/> " />
    <link rel="stylesheet" type="text/css" href="<c:url value="/webjars/font-awesome/4.7.0/css/font-awesome.min.css"/> " />
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/styles.css"/> " />
    <jsp:invoke fragment="cssEspecificos"></jsp:invoke>
</head>
<body>

<jsp:include page="/WEB-INF/templates/nav.jsp"/>

<div class="container-fluid">
    <jsp:doBody />
</div>

<footer class="footer">
    <div class="container">
      <span class="text-muted">
        Made by @edsontofolo
      </span>
    </div>
</footer>

<script type="text/javascript" src="<c:url value="/webjars/jquery/1.11.1/jquery.min.js"/> "></script>
<script type="text/javascript" src="<c:url value="/webjars/bootstrap/3.3.7/js/bootstrap.min.js"/> "></script>
<script type="text/javascript" src="<c:url value="/webjars/Bootstrap-3-Typeahead/3.1.1/bootstrap3-typeahead.min.js"/> "></script>
<jsp:invoke fragment="scriptsEspecificos"></jsp:invoke>

</body>

</html>