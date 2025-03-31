<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
   <title><dec:title default="ChronoLux Shop"/> </title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
   <dec:head />

</head>
<body>

    <%@ include file="/common/web/header.jsp" %>

	<dec:body/>

	<%@ include file="/common/web/footer.jsp" %>

    <!-- JavaScript Libraries -->

    <!-- Nhúng plugin twbsPagination -->

    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
    <script src="<c:url value='/template/web/lib/easing/easing.min.js'/>"></script>

    <script src="<c:url value='/template/web/lib/owlcarousel/owl.carousel.min.js'/>"></script>
    <script src="<c:url value='/template/web/js/main.js'/>"></script>
    <!-- Contact Javascript File -->
    <script src="<c:url value='/template/web/mail/jqBootstrapValidation.min.js'/>"></script>

    <script src="<c:url value='/template/web/mail/contact.js'/>"></script>

    <!-- Template Javascript -->


</body>
</html>