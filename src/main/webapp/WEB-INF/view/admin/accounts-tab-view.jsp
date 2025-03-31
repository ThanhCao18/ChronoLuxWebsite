<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<head>
    <title>ChronoLux - Quản lý tài khoản người dùng</title>
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
    <div class="d-sm-flex align-items-start mb-4">
        <i class="fa-solid fa-users mr-3" style="font-size: 26px"></i>
        <h1 class="h3 mb-2 text-gray-800">Quản lý tài khoản người dùng</h1>
        <%--<a href="#" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm"><i
                class="fas fa-download fa-sm text-white-50"></i> Generate Report</a>--%>
    </div>
    <hr/>
    <%--<nav>
        <div class="nav nav-tabs" id="nav-tab" role="tablist">
            <button class="nav-link active" id="nav-admin-tab" data-bs-toggle="tab" data-bs-target="#nav-admin" type="button" role="tab" aria-controls="nav-admin" aria-selected="true">Tài khoản Admin</button>
            <button class="nav-link" id="nav-user-tab" data-bs-toggle="tab" data-bs-target="#nav-user" type="button" role="tab" aria-controls="nav-user" aria-selected="false">Tài khoản Người dùng</button>
        </div>
    </nav>--%>
    <%--<div class="tab-content" id="nav-tabContent">
        &lt;%&ndash;Admin&ndash;%&gt;
        &lt;%&ndash;<div class="tab-pane fade show active" id="nav-admin" role="tabpanel" aria-labelledby="nav-admin-tab">
            <div class="mt-4">
                <div class="d-flex justify-content-end">
                    <a href="${pageContext.request.contextPath}/admin/account/create" class="btn btn-success mr-2 mt-1">
                        <i class="fas fa-plus mr-1"></i> Tạo tài khoản Admin
                    </a>
                </div>
                <div class="card shadow mb-4 mt-4">
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-bordered" width="100%" cellspacing="0">
                                <thead>
                                <tr>
                                    <th>Ảnh đại diện</th>
                                    <th>Tên tài khoản</th>
                                    <th>Tên người dùng</th>
                                    <th>Email</th>
                                    <th>Ngày tạo</th>
                                    <th>Tạo bởi</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="account" items="${adminAccountPage.content}">
                                    <tr>
                                        <td>
                                            <img src="<c:url value="/template/web/img/user-logos/${account.imgUrl}"/>" alt="Logo" style="max-width:50px; border-radius: 50%;"/>
                                        </td>
                                        <td>${account.userName}</td>
                                        <td>${account.fullName}</td>
                                        <td>${account.email}</td>
                                        <td>${account.createdDate}</td>
                                        <td>${account.createdBy}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        &lt;%&ndash;<nav aria-label="Page navigation example" class="d-flex justify-content-center">
                            <ul class="pagination">
                                <c:if test="${currentAdminPage > 1}">
                                    <li class="page-item">
                                        <a class="page-link" href="?page=${currentAdminPage - 1}&size=${adminAccountPage.size}" aria-label="Previous">
                                            <span aria-hidden="true">&laquo;</span>
                                        </a>
                                    </li>
                                </c:if>
                                <c:forEach var="i" begin="1" end="${adminAccountPage.totalPages}">
                                    <li class="page-item ${i == currentAdminPage ? 'active' : ''}">
                                        <a class="page-link" href="?page=${i}&size=${adminAccountPage.size}">${i}</a>
                                    </li>
                                </c:forEach>
                                <c:if test="${currentAdminPage < adminAccountPage.totalPages}">
                                    <li class="page-item">
                                        <a class="page-link" href="?page=${currentAdminPage + 1}&size=${adminAccountPage.size}" aria-label="Next">
                                            <span aria-hidden="true">&raquo;</span>
                                        </a>
                                    </li>
                                </c:if>
                            </ul>
                        </nav>&ndash;%&gt;
                    </div>
                </div>
            </div>
        </div>&ndash;%&gt;

        &lt;%&ndash;User&ndash;%&gt;
        <div class="tab-pane fade" id="nav-user" role="tabpanel" aria-labelledby="nav-user-tab">
            <div class="card shadow mb-4 mt-4">
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-bordered" width="100%" cellspacing="0">
                            <thead>
                            <tr>
                                <th>Ảnh đại diện</th>
                                <th>Tên tài khoản</th>
                                <th>Tên người dùng</th>
                                <th>Email</th>
                                <th>Ngày tạo</th>
                                <th>Hành động</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="account" items="${userAccountPage.content}">
                                <tr>
                                    <td>
                                        <c:choose>
                                            <c:when test="${fn:startsWith(account.imgUrl, 'http')}">
                                                <img src="${account.imgUrl}" alt="Logo" style="max-width:50px; border-radius: 50%;"/>
                                            </c:when>
                                            <c:otherwise>
                                                <img src="<c:url value='/template/web/img/user-logos/${account.imgUrl}'/>" alt="Logo" style="max-width:50px; border-radius: 50%;"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>${account.userName}</td>
                                    <td>${account.fullName}</td>
                                    <td>${account.email}</td>
                                    <td>${account.createdDate}</td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/admin/account/view/?id=${account.id}" class="btn btn-info btn-sm mr-2">
                                            <i class="fas fa-eye mr-1"></i> Lịch sử đặt hàng
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    &lt;%&ndash;<nav aria-label="Page navigation example" class="d-flex justify-content-center">
                        <ul class="pagination">
                            <c:if test="${currentUserPage > 1}">
                                <li class="page-item">
                                    <a class="page-link" href="?page=${currentUserPage - 1}&size=${userAccountPage.size}" aria-label="Previous">
                                        <span aria-hidden="true">&laquo;</span>
                                    </a>
                                </li>
                            </c:if>
                            <c:forEach var="i" begin="1" end="${userAccountPage.totalPages}">
                                <li class="page-item ${i == currentUserPage ? 'active' : ''}">
                                    <a class="page-link" href="?page=${i}&size=${userAccountPage.size}">${i}</a>
                                </li>
                            </c:forEach>
                            <c:if test="${currentUserPage < userAccountPage.totalPages}">
                                <li class="page-item">
                                    <a class="page-link" href="?page=${currentUserPage + 1}&size=${userAccountPage.size}" aria-label="Next">
                                        <span aria-hidden="true">&raquo;</span>
                                    </a>
                                </li>
                            </c:if>
                        </ul>
                    </nav>&ndash;%&gt;
                </div>
            </div>
        </div>
    </div>--%>
    <div class="card shadow mb-4 mt-4">
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-bordered" width="100%" cellspacing="0">
                            <thead>
                            <tr>
                                <th>Ảnh đại diện</th>
                                <th>Tên tài khoản</th>
                                <th>Tên người dùng</th>
                                <th>Email</th>
                                <th>Ngày tạo</th>
                                <th>Hành động</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="account" items="${userAccountPage.content}">
                                <tr>
                                    <td>
                                        <c:choose>
                                            <c:when test="${fn:startsWith(account.imgUrl, 'http')}">
                                                <img src="${account.imgUrl}" alt="Logo" style="max-width:50px; border-radius: 50%;"/>
                                            </c:when>
                                            <c:otherwise>
                                                <img src="<c:url value='/template/web/img/user-logos/${account.imgUrl}'/>" alt="Logo" style="max-width:50px; border-radius: 50%;"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>${account.userName}</td>
                                    <td>${account.fullName}</td>
                                    <td>${account.email}</td>
                                    <td>${account.createdDate}</td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/admin/account/view/?id=${account.id}" class="btn btn-info btn-sm mr-2">
                                            <i class="fas fa-eye mr-1"></i> Lịch sử đặt hàng
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <%--<nav aria-label="Page navigation example" class="d-flex justify-content-center">
                        <ul class="pagination">
                            <c:if test="${currentUserPage > 1}">
                                <li class="page-item">
                                    <a class="page-link" href="?page=${currentUserPage - 1}&size=${userAccountPage.size}" aria-label="Previous">
                                        <span aria-hidden="true">&laquo;</span>
                                    </a>
                                </li>
                            </c:if>
                            <c:forEach var="i" begin="1" end="${userAccountPage.totalPages}">
                                <li class="page-item ${i == currentUserPage ? 'active' : ''}">
                                    <a class="page-link" href="?page=${i}&size=${userAccountPage.size}">${i}</a>
                                </li>
                            </c:forEach>
                            <c:if test="${currentUserPage < userAccountPage.totalPages}">
                                <li class="page-item">
                                    <a class="page-link" href="?page=${currentUserPage + 1}&size=${userAccountPage.size}" aria-label="Next">
                                        <span aria-hidden="true">&raquo;</span>
                                    </a>
                                </li>
                            </c:if>
                        </ul>
                    </nav>--%>
                </div>
            </div>
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
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
