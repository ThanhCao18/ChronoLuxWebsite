<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp" %>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">

    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Custom fonts for this template -->
    <link href="<c:url value='/vendor/fontawesome-free/css/all.min.css'/>" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="<c:url value='/css/sb-admin-2.min.css'/>" rel="stylesheet">
    <link href="<c:url value='/template/admin/css/styles.css'/>" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
    <!-- Custom styles for this page -->
    <link href="<c:url value='/vendor/datatables/dataTables.bootstrap4.min.css'/>" rel="stylesheet">
    <title><dec:title default="ChronoLux - Admin Page" /></title>

     <dec:head />



</head>

 <body id="page-top">

            <!-- Page Wrapper -->
            <div id="wrapper">

                <%@ include file="/common/admin/menu.jsp" %>

                    <!-- Content Wrapper -->
                    <div id="content-wrapper" class="d-flex flex-column">

                        <!-- Main Content -->
                        <div id="content">
                            <%@ include file="/common/admin/header.jsp" %>
                                <dec:body />
                                <%@ include file="/common/admin/footer.jsp" %>
                        </div>

                    </div>
            </div>




    <!-- Bootstrap core JavaScript-->
    <script src="<c:url value='/template/admin/vendor/jquery/jquery.min.js'/>"></script>

    <script src= "<c:url value='/template/admin/vendor/bootstrap/js/bootstrap.bundle.min.js'/>"></script>

    <!-- Core plugin JavaScript-->
    <script src="<c:url value='/template/admin/vendor/jquery-easing/jquery.easing.min.js'/>"></script>

    <!-- Custom scripts for all pages-->
    <script src="<c:url value='/template/admin/js/sb-admin-2.min.js'/>"></script>

    <!-- Page level plugins -->
    <script src="<c:url value='/template/admin/vendor/chart.js/Chart.min.js'/>"></script>

    <!-- Page level custom scripts -->
    <script src="<c:url value='/template/admin/js/demo/chart-area-demo.js'/>"></script>
    <script src="<c:url value='/template/admin/js/demo/chart-pie-demo.js'/>"></script>

                <script src="  <c:url value='/template/admin/vendor/datatables/jquery.dataTables.min.js'/>"></script>
                <script src="   <c:url value='/template/admin/vendor/datatables/dataTables.bootstrap4.min.js'/>"></script>

                <!-- Page level custom scripts -->

                <script src=" <c:url value='/template/admin/js/demo/datatables-demo.js'/>"></script>
</body>

</html>