<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<meta charset="UTF-8">
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/taglib.jsp" %>

<head>
    <title>ChronoLux - Cập nhật thương hiệu</title>
    <meta charset="UTF-8">
    <!-- Custom fonts for this template-->
    <link href="<c:url value='/template/admin/vendor/fontawesome-free/css/all.min.css'/>" rel="stylesheet" type="text/css">
    <link
            href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
            rel="stylesheet">
    <link href="<c:url value='/template/admin/css/sb-admin-2.min.css'/>" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
</head>
<body>
<div class="container">
    <form:form method="post" action="${pageContext.request.contextPath}/admin/brand/save" enctype="multipart/form-data" modelAttribute="brand" >
        <input type="hidden" name="page" value="${currentPage}">
        <div class="d-flex justify-content-between align-items-center my-4">
            <h1 class="h3 mb-1 mt-2 text-gray-800">Sửa thông tin thương hiệu</h1>
            <div>
                <a href="${pageContext.request.contextPath}/admin/brands?page=${currentPage}" class="btn btn-warning">
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
        <form:hidden path="id" />
        <div class="form-group">
            <label for="name">Tên thương hiệu</label>
            <form:input path="name" type="text" class="form-control" id="name" required="required"/>
        </div>
        <div class="form-group">
            <label for="country">Quốc gia</label>
            <form:input path="country" type="text" class="form-control" id="country" required="required"/>
        </div>
        <!-- logo -->
        <div class="form-group">
            <label for="logo">Tải logo</label>
            <div class="custom-file">
                <input type="file" class="custom-file-input" id="logo" name="logo" accept="image/*" onchange="previewImage(event, 'logoPreview')">
                <label class="custom-file-label" for="logo">
                        ${brand.iconUrl}
                </label>
            </div>
        </div>
        <div class="form-group">
            <img id="logoPreview" src="<c:url value='/template/web/img/brands/${brand.iconUrl}'/>"
                 alt="Logo Preview" class="img-fluid"
                 style="max-width: 300px;" />
        </div>

        <!-- banner -->
        <div class="form-group">
            <label for="banner">Tải banner</label>
            <div class="custom-file">
                <input type="file" class="custom-file-input" id="banner" name="banner" accept="image/*" onchange="previewImage(event, 'bannerPreview')">
                <label class="custom-file-label" for="banner">
                        ${brand.bannerUrl}
                </label>
            </div>
        </div>
        <!-- Phần xem trước ảnh -->
        <div class="form-group">
            <img id="bannerPreview" src="<c:url value='/template/web/img/brands/${brand.bannerUrl}'/>"
                 alt="Banner Preview" class="img-fluid"
                 style="max-width: 300px;" />
        </div>
    </form:form>
</div>

<script>
    function previewImage(event, previewId) {
        var input = event.target;
        var reader = new FileReader();

        reader.onload = function(){
            var dataURL = reader.result;
            var imagePreview = document.getElementById(previewId);
            imagePreview.src = dataURL;
            imagePreview.style.display = 'block';
        };
        reader.readAsDataURL(input.files[0]);
    }
</script>
</body>