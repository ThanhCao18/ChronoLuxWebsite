<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <title>ChronoLux - Chi tiết bảo hành</title>
    <!-- Custom fonts for this template-->
    <link href="<c:url value='/template/admin/vendor/fontawesome-free/css/all.min.css'/>" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
    <link href="<c:url value='/template/admin/css/sb-admin-2.min.css'/>" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
    <script src="https://cdn.ckeditor.com/4.22.1/standard/ckeditor.js"></script>
</head>
<body>
<div class="container mt-4">
    <form:form method="post" action="${pageContext.request.contextPath}/admin/warranty/save" modelAttribute="warranty" enctype="multipart/form-data">
        <input type="hidden" name="page" value="${currentPage}">
        <div class="d-flex justify-content-between align-items-center my-1">
            <h1 class="h3 mb-1 text-gray-800">Chính sách bảo hành</h1>
            <div>
                <a href="${pageContext.request.contextPath}/admin/warranty?page=${currentPage}" class="btn btn-warning">
                    <i class="fa fa-reply mr-1" aria-hidden="true"></i>
                    Quay lại
                </a>
                <button type="submit" class="btn btn-success ml-2">
                    <i class="fa fa-check mr-1" aria-hidden="true"></i>
                    Lưu
                </button>
            </div>
        </div>
        <hr/>
        <c:if test="${not empty warranty.id}">
            <form:hidden path="id" />
        </c:if>
        <form:hidden path="productLineId" />
        <div class="form-group">
            <label for="contentCK">Nội dung bảo hành của ${warranty.productLineName}</label>
            <form:textarea path="content" class="form-control" id="contentCK" cols="20" rows="15"></form:textarea>
        </div>
    </form:form>
</div>
<script>
    CKEDITOR.replace('contentCK', {
        height: 400,
        resize_enabled: false,
        /*on: {
            instanceReady: function () {
                // this.setData('');  // Xóa nội dung mặc định
            }
        },*/
    });
</script>
</body>
