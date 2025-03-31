<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglib.jsp" %>

<head>
    <title>ChronoLux - Cập nhật dòng sản phẩm</title>
    <meta charset="UTF-8">
    <!-- Custom fonts for this template-->
    <link href="<c:url value='/template/admin/vendor/fontawesome-free/css/all.min.css'/>" rel="stylesheet" type="text/css">
    <link
            href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
            rel="stylesheet">
    <link href="<c:url value='/template/admin/css/sb-admin-2.min.css'/>" rel="stylesheet" type="text/css">
    <link href="<c:url value='/template/admin/css/styles.css'/>" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
</head>
<body>
<div class="container">
    <form:form method="post" action="${pageContext.request.contextPath}/admin/product-line/save" enctype="multipart/form-data" modelAttribute="productLine" >
        <input type="hidden" name="page" value="${currentPage}">
        <div class="d-flex justify-content-between align-items-center my-4">
            <h1 class="h3 mb-1 mt-2 text-gray-800">Sửa dòng thương hiệu</h1>
            <div>
                <a href="${pageContext.request.contextPath}/admin/product-lines?page=${currentPage}" class="btn btn-warning">
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
        <div class="form-group">
            <label class="mr-3 mb-2">Thương hiệu:</label>
            <select class="custom-select-box form-control mb-3" name="brandId" aria-label="Select brand" required>
                <c:choose>
                    <c:when test="${empty brands}">
                        <option selected>Chưa có dữ liệu</option>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="brand" items="${brands}">
                            <option value="${brand.id}" <c:if test="${brand.id == productLine.brandId}">selected</c:if>>
                                    ${brand.name}
                            </option>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </select>
        </div>
        <form:hidden path="id" />
        <div class="form-group">
            <label for="name">Tên dòng</label>
            <form:input path="name" type="text" class="form-control" id="name" required="required"/>
        </div>
        <!-- logo -->
        <div class="form-group">
            <label for="logo">Tải logo</label>
            <div class="custom-file">
                <input type="file" class="custom-file-input" id="logo" name="logo" accept="image/*" onchange="previewImage(event, 'logoPreview')">
                <label class="custom-file-label" for="logo">${productLine.iconUrl}</label>
            </div>
        </div>
        <!-- Phần xem trước ảnh -->
        <div class="form-group">
            <img id="logoPreview" src="<c:url value='/template/web/img/product-lines/${productLine.iconUrl}'/>"
                 alt="Logo Preview"
                 class="img-fluid" style="max-width: 300px;" />
        </div>

        <!-- banner -->
        <div class="form-group">
            <label for="banner">Tải banner</label>
            <div class="custom-file">
                <input type="file" class="custom-file-input" id="banner" name="banner" accept="image/*" onchange="previewImage(event, 'bannerPreview')">
                <label class="custom-file-label" for="banner">
                        ${productLine.bannerUrl}
                </label>
            </div>
        </div>
        <!-- Phần xem trước ảnh -->
        <div class="form-group">
            <img id="bannerPreview" src="<c:url value='/template/web/img/product-lines/${productLine.bannerUrl}'/>"
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
            imagePreview.style.display = 'block'; // Hiển thị ảnh sau khi tải
        };

        reader.readAsDataURL(input.files[0]);
    }
</script>
</body>