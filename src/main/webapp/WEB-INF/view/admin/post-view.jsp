<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<meta charset="UTF-8">
<%@ include file="/common/taglib.jsp" %>
<!DOCTYPE html>
            <html lang="vi">
<head>
    <title>ChronoLux - Quản lý banner</title>
    <!-- Custom fonts for this template-->
    <link href="<c:url value='/template/admin/vendor/fontawesome-free/css/all.min.css'/>" rel="stylesheet" type="text/css">
    <link
            href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
            rel="stylesheet">
    <link href="<c:url value='/template/admin/css/sb-admin-2.min.css'/>" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
</head>
<body>
<!-- Begin Page Content -->
<div class="container-fluid">
    <div class="d-sm-flex align-items-start justify-content-start mb-3">
        <i class="fa-solid fa-newspaper h3 mb-1 mr-2" style="font-size: 28px"></i>
        <h1 class="h3 mb-1 text-gray-800">Banner</h1>
    </div>
    <hr/>
    <div class="d-flex justify-content-end">
        <a href="${pageContext.request.contextPath}/admin/post/create" class="btn btn-success mr-2 mt-1 mb-4">
            <i class="fas fa-plus mr-1"></i> Thêm banner
        </a>
    </div>
    <!-- Loop through posts list -->
    <c:forEach var="a" items="${posts}">
        <div class="card mb-4 shadow-sm" style="border-radius: 15px; position: relative;">
            <div class="card-body">
                <div class="d-flex justify-content-between">
                    <h5 class="card-title">${a.caption}</h5>
                    <div>
                        <!-- Edit button (pencil icon) -->
                        <a href="${pageContext.request.contextPath}/admin/post/update/?id=${a.id}" class="btn btn-info mr-2">
                            <i class="fa-solid fa-pencil"></i>
                        </a>
                        <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#confirmModal${a.id}">
                            <i class="fas fa-trash-alt"></i>
                        </button>
                        <%--<a href="${pageContext.request.contextPath}/admin/post/delete?id=${a.id}" class="btn btn-danger btn-sm" onclick="return confirmDelete(${a.id});">
                            <i class="fas fa-times"></i>
                        </a>--%>
                    </div>
                </div>
                <!-- Content -->
                <p class="card-text">${a.content}</p>

                <!-- Image (Small size) -->
                <img src="<c:url value='/template/web/img/posts/${a.img}'/>" alt="Post Image" class="img-fluid"
                     style="border-radius: 10px; max-width: 150px; height: auto;" />
            </div>
        </div>
        <!-- Modal -->
        <div class="modal fade" id="confirmModal${a.id}" tabindex="-1" role="dialog" aria-labelledby="confirmModalLabel${a.id}" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="confirmModalLabel${a.id}">XÁC NHẬN XÓA</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        Bạn có chắc muốn xóa bài viết <strong>${a.caption}</strong> không?
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">
                            <i class="fa-solid fa-reply mr-1"></i>
                            Trở lại
                        </button>
                        <a href="${pageContext.request.contextPath}/admin/post/delete?id=${a.id}" class="btn btn-danger">
                            <i class="fa-solid fa-check mr-1"></i> Xác nhận xóa
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>
</div>
<div aria-live="polite" aria-atomic="true" style="position: fixed; bottom: 1rem; right: 1rem; z-index: 1050;">
    <div class="toast" id="successToast" role="alert" aria-live="assertive" aria-atomic="true" data-delay="3500">
        <div class="toast-header bg-success text-white">
            <strong class="mr-auto custom-font-20">Thông báo</strong>
            <button type="button" class="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
        <div class="toast-body">
            <span id="toastMessage" class="custom-font-20">Thêm thành công</span>
        </div>
    </div>
</div>
<script>
    document.addEventListener("DOMContentLoaded", function () {
        const successMessage = "${successMessage}";
        if (successMessage) {
            const toastElement = document.getElementById("successToast");
            const toast = new bootstrap.Toast(toastElement);
            document.getElementById("toastMessage").innerText = successMessage;
            toast.show();
        }
    });
</script>
</body>