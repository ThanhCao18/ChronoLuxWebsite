<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <title>ChronoLux - Lịch sử hóa đơn</title>
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
    <div class="d-flex justify-content-between align-items-center">
        <h2 class="text-start">
            <i class="h3 mb-1 mr-3 fa-solid fa-file-lines" style="font-size: 28px"></i>
            Lịch sử đặt hàng
        </h2>
        <a href="${pageContext.request.contextPath}/admin/accounts" class="btn btn-warning ms-auto">
            <i class="fa-solid fa-reply mr-1"></i>Quay lại
        </a>
    </div>
    <hr/>

    <div class="card shadow mb-4 mt-4">
        <div class="card-body">
            <h5>
                <strong>
                    Tài khoản đặt hàng: ${account.userName}
                </strong>
            </h5>
            <div class="table-responsive">
                <table class="table table-bordered" width="100%" cellspacing="0">
                    <thead class="table-secondary">
                    <tr>
                        <th>Mã hóa đơn</th>
                        <th>Họ & tên người đặt hàng</th>
                        <th>Ngày đặt hàng</th>
                        <th>Tên sản phẩm</th>
                        <th>Số lượng</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="h" items="${history.content}">
                        <tr>
                            <td>${h.billId}</td>
                            <td>${h.username}</td>
                            <td>${h.createdDate}</td>
                            <td>${h.productName}</td>
                            <td>${h.quantity}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <nav aria-label="Page navigation example" class="d-flex justify-content-center">
                <ul class="pagination">
                    <!-- Nút Previous -->
                    <c:if test="${currentPage > 1}">
                        <li class="page-item">
                            <a class="page-link" href="?page=${currentPage - 1}&size=${history.size}" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                                <span class="sr-only">Previous</span>
                            </a>
                        </li>
                    </c:if>
                    <!-- Hiển thị các số trang -->
                    <c:forEach var="i" begin="1" end="${history.totalPages}">
                        <li class="page-item ${i == currentPage ? 'active' : ''}">
                            <a class="page-link" href="?page=${i}&size=${history.size}">${i}</a>
                        </li>
                    </c:forEach>
                    <!-- Nút Next -->
                    <c:if test="${currentPage < history.totalPages}">
                        <li class="page-item">
                            <a class="page-link" href="?page=${currentPage + 1}&size=${history.size}" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                                <span class="sr-only">Next</span>
                            </a>
                        </li>
                    </c:if>
                </ul>
            </nav>
        </div>
    </div>
</div>
</body>