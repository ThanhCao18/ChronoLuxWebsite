<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <title>ChronoLux - Quản lý bảo hành</title>
    <!-- Custom fonts for this template-->
    <link href="<c:url value='/template/admin/vendor/fontawesome-free/css/all.min.css'/>" rel="stylesheet" type="text/css">
    <link
            href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
            rel="stylesheet">
    <link href="<c:url value='/template/admin/css/sb-admin-2.min.css'/>" rel="stylesheet" type="text/css">
    <link href="<c:url value='/template/admin/css/styles.css'/>" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
    <script src="https://cdn.ckeditor.com/4.22.1/standard/ckeditor.js"></script>
</head>
<body>
<!-- Begin Page Content -->
<div class="container-fluid">
    <div class="d-sm-flex align-items-center justify-content-start mb-3">
        <i class="h3 mb-1 mr-3 fa-solid fa-paperclip" style="font-size: 28px"></i>
        <h1 class="h3 mb-1 text-gray-800">Chính sách bảo hành</h1>
    </div>
    <hr/>
    <div class="card shadow mb-4 mt-4">
        <div class="card-body">
            <div class="d-flex justify-content-start mb-4 mt-3">
                <label class="mr-3">Thương hiệu:</label>
                <form class="form-group" action="${pageContext.request.contextPath}/admin/warranty" method="get">
                    <select class="custom-select-box" id="brandSelect" name="brandId" aria-label="Select brand">
                        <c:choose>
                            <c:when test="${empty brands}">
                                <option selected>Chưa có dữ liệu</option>
                            </c:when>
                            <c:otherwise>
                                <option value="0" <c:if test="${brandId == 0}">selected</c:if>>Tất cả</option>
                                <c:forEach var="brand" items="${brands}">
                                    <option value="${brand.id}" <c:if test="${brand.id == brandId}">selected</c:if>>
                                            ${brand.name}
                                    </option>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </select>
                    <label class="mr-3 ml-3">Bảo hành:</label>
                    <select class="custom-select-box mr-3" id="warrantySelect" name="isHasWarranty" aria-label="Select warranty filter">
                        <option value="2" <c:if test="${isHasWarranty == 2}">selected</c:if>>Tất cả</option>
                        <option value="1" <c:if test="${isHasWarranty == 1}">selected</c:if>>Đã có</option>
                        <option value="0" <c:if test="${isHasWarranty == 0}">selected</c:if>>Chưa có</option>
                    </select>
                    <button type="submit" class="btn btn-dark ml-3 d-inline-flex align-items-center">
                        <i class="fa fa-filter mr-1" aria-hidden="true"></i> Lọc
                    </button>
                    <input type="hidden" name="page" value="${currentPage}">
                    <input type="hidden" name="limit" value="6">
                </form>
            </div>
            <div class="table-responsive">
                <table class="table table-bordered" width="100%" cellspacing="0">
                    <thead>
                    <tr>
                        <th>Tên thương hiệu</th>
                        <th>Dòng đồng hồ</th>
                        <th>Chính sách bảo hành</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="productLine" items="${productLines.content}">
                        <tr>
                            <td>${productLine.brandName}</td>
                            <td>${productLine.name}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${productLine.warrantyId != 0}">
                                        <!-- Warranty exists: show green check icon -->
                                        <i class="fas fa-check-circle" style="color: green;"></i>
                                    </c:when>
                                    <c:otherwise>
                                        <!-- No warranty: show red X icon -->
                                        <i class="fas fa-times-circle" style="color: red;"></i>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${productLine.warrantyId != 0}">
                                        <a href="${pageContext.request.contextPath}/admin/warranty/createOrUpdate/?id=${productLine.id}&currentPage=${currentPage}"
                                           class="btn btn-secondary btn-sm mr-2">
                                            <i class="fas fa-eye mr-1"></i> Xem bảo hành
                                        </a>
                                        <button type="button" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#confirmModal${productLine.id}">
                                            <i class="fas fa-trash-alt mr-1"></i> Xóa
                                        </button>
                                        <%--<a href="${pageContext.request.contextPath}/admin/warranty/delete?id=${productLine.id}" class="btn btn-danger btn-sm" onclick="return confirmDelete(${productLine.id})">
                                            <i class="fas fa-trash-alt mr-1"></i> Xóa
                                        </a>--%>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="${pageContext.request.contextPath}/admin/warranty/createOrUpdate/?id=${productLine.id}&currentPage=${currentPage}"
                                           class="btn btn-success btn-sm mr-2">
                                            <i class="fas fa-plus mr-1"></i> Thêm bảo hành
                                        </a>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <!-- Modal -->
                        <div class="modal fade" id="confirmModal${productLine.id}" tabindex="-1" role="dialog" aria-labelledby="confirmModalLabel${productLine.id}" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="confirmModalLabel${productLine.id}">XÁC NHẬN XÓA</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        Bạn có chắc muốn xóa chính sách bảo hành của dòng sản phẩm <strong>${productLine.name}</strong> không?
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">
                                            <i class="fa-solid fa-reply mr-1"></i>
                                            Trở lại
                                        </button>
                                        <a href="${pageContext.request.contextPath}/admin/warranty/delete?id=${productLine.id}" class="btn btn-danger">
                                            <i class="fa-solid fa-check mr-1"></i>
                                            Xác nhận xóa
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <nav aria-label="Page navigation example" class="d-flex justify-content-center">
                <ul class="pagination">
                    <!-- Nút Previous -->
                    <c:if test="${currentPage > 1}">
                        <li class="page-item">
                            <a class="page-link" href="?page=${currentPage - 1}&size=${productLines.size}" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                                <span class="sr-only">Previous</span>
                            </a>
                        </li>
                    </c:if>
                    <!-- Hiển thị các số trang -->
                    <c:forEach var="i" begin="1" end="${productLines.totalPages}">
                        <li class="page-item ${i == currentPage ? 'active' : ''}">
                            <a class="page-link" href="?page=${i}&size=${productLines.size}">${i}</a>
                        </li>
                    </c:forEach>
                    <!-- Nút Next -->
                    <c:if test="${currentPage < productLines.totalPages}">
                        <li class="page-item">
                            <a class="page-link" href="?page=${currentPage + 1}&size=${productLines.size}" aria-label="Next">
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