<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <title>ChronoLux - Quản lý sản phẩm</title>
    <!-- Custom fonts for this template-->
    <link href="<c:url value='/template/admin/vendor/fontawesome-free/css/all.min.css'/>" rel="stylesheet"
          type="text/css">
    <link
            href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
            rel="stylesheet">
    <link href="<c:url value='/template/admin/css/sb-admin-2.min.css'/>" rel="stylesheet" type="text/css">
    <link href="<c:url value='/template/admin/css/styles.css'/>" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="<c:url value='/template/admin/js/script.js'/>"></script>
</head>
<body>
<!-- Begin Page Content -->
<div class="container-fluid">
    <div class="d-sm-flex align-items-center justify-content-start mb-3">
        <i class="h3 mb-1 mr-3 fa-solid fa-boxes-stacked" style="font-size: 28px"></i>
        <h1 class="h3 mb-1 text-gray-800">Đồng hồ</h1>
    </div>
    <hr/>
    <form id="searchForm" action="${pageContext.request.contextPath}/admin/product/search" method="get">
        <div class="row">
            <div class="col-md-4">
                <div class="input-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text"><i class="fas fa-search"></i></span>
                    </div>
                    <input id="searchInput" type="text" class="form-control" name="keyword"
                           placeholder="Tìm kiếm đồng hồ..." value="${keyword}">
                </div>
            </div>
            <div class="col-md-2">
                <button class="btn btn-primary w-40" type="submit">
                    <i class="fas fa-search mr-1"></i> Tìm kiếm
                </button>
            </div>
            <div class="col-md-6 text-right">
                <a href="${pageContext.request.contextPath}/admin/product/create?currentPage=${currentPage}"
                   class="btn btn-success mr-2 mt-1">
                    <i class="fas fa-plus mr-1"></i> Thêm đồng hồ
                </a>
            </div>
        </div>
    </form>
    <div class="card shadow mb-4 mt-4">
        <div class="card-body">
            <div class="d-flex justify-content-start mb-4 mt-3">
                <label class="mr-3">Thương hiệu:</label>
                <form class="form-group" action="${pageContext.request.contextPath}/admin/products" method="get">
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
                    <label class="mr-3 ml-5">Dòng đồng hồ:</label>
                    <select class="custom-select-box" id="productLineSelect" name="productLineId"
                            data-product-line-id="${productLineId}" aria-label="Select product line" required>
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
                        <th>Ảnh sản phẩm</th>
                        <th>Tên đồng hồ</th>
                        <th>Tên thương hiệu</th>
                        <th>Dòng đồng hồ</th>
                        <th>Tồn kho</th>
                        <th>Giá sản phẩm</th>
                        <th>Đánh giá</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="product" items="${productPage.content}">
                        <tr>
                            <td><img src="<c:url value='/template/web/img/products/${product.imgUrl}'/>" alt="Logo"
                                     style="max-width:80px;"/></td>
                            <td>${product.name}</td>
                            <td>${product.brandName}</td>
                            <td>${product.productLineName}</td>
                            <td>${product.stock}</td>
                            <td class="currency">${product.price}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${product.commentCount == 0}">
                                        Không có đánh giá
                                    </c:when>
                                <c:otherwise>
                                    <a href="${pageContext.request.contextPath}/admin/comment/view?id=${product.id}&currentPage=${currentPage}"
                                       class="btn btn-outline-dark btn-sm mr-2 position-relative">
                                        <i class="fas fa-message fa-fw mr-1"></i> Xem đánh giá
                                        <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger"
                                              style="color: white; font-size: 10px";>
                                                  ${product.commentCount}
                                       </span>
                                    </a>
                                </c:otherwise>
                                </c:choose>
                            </td>
                           <td>
                               <a href="${pageContext.request.contextPath}/admin/product/update?id=${product.id}&currentPage=${currentPage}"
                                    class="btn btn-info btn-sm mr-2">
                                    <i class="fas fa-pencil-alt mr-1"></i> Sửa
                               </a>
                               <button type="button" class="btn btn-danger btn-sm" data-toggle="modal"
                                        data-target="#confirmModal${product.id}">
                                    <i class="fas fa-trash-alt mr-1"></i> Xóa
                               </button>
                           </td>
                        </tr>
                        <!-- Modal -->
                        <div class="modal fade" id="confirmModal${product.id}" tabindex="-1" role="dialog"
                             aria-labelledby="confirmModalLabel${product.id}" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="confirmModalLabel${product.id}">XÁC NHẬN XÓA</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        Bạn có chắc muốn xóa sản phẩm <strong>${product.name}</strong> không?
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">
                                            <i class="fa-solid fa-reply mr-1"></i>
                                            Trở lại
                                        </button>
                                        <a href="${pageContext.request.contextPath}/admin/product/delete?id=${product.id}"
                                           class="btn btn-danger">
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
                            <a class="page-link" href="?page=${currentPage - 1}&size=${productPage.size}"
                               aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                                <span class="sr-only">Previous</span>
                            </a>
                        </li>
                    </c:if>
                    <!-- Hiển thị các số trang -->
                    <c:forEach var="i" begin="1" end="${productPage.totalPages}">
                        <li class="page-item ${i == currentPage ? 'active' : ''}">
                            <a class="page-link" href="?page=${i}&size=${productPage.size}">${i}</a>
                        </li>
                    </c:forEach>
                    <!-- Nút Next -->
                    <c:if test="${currentPage < productPage.totalPages}">
                        <li class="page-item">
                            <a class="page-link" href="?page=${currentPage + 1}&size=${productPage.size}"
                               aria-label="Next">
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
<script>
    $(document).ready(function () {
        // Khi người dùng thay đổi lựa chọn brand
        $('#brandSelect').change(function () {
            var brandId = $(this).val(); // Lấy giá trị của brand được chọn
            var productLineId = $('#productLineSelect').data('product-line-id'); // Lấy productLineId từ thuộc tính data

            // Gửi yêu cầu AJAX để lấy danh sách product lines dựa trên brandId
            $.ajax({
                url: '${pageContext.request.contextPath}/admin/product-line/getProductLines',
                type: 'GET',
                data: {brandId: brandId},
                success: function (response) {
                    // Làm trống thẻ select productline trước khi cập nhật
                    $('#productLineSelect').empty();

                    if (response.length === 0) {
                        // Nếu không có dòng sản phẩm nào
                        $('#productLineSelect').append('<option value="" disabled selected>Chưa có dữ liệu</option>');
                        $('#productLineSelect').val('');
                    } else {
                        // Nếu brandId là "Tất cả" (giá trị 0)
                        if (brandId == 0) {
                            $('#productLineSelect').append('<option value="0">Tất cả</option>');
                        }

                        // Thêm product lines vào thẻ select nếu có dữ liệu
                        $.each(response, function (key, value) {
                            $('#productLineSelect').append('<option value="' + value.id + '">' + value.name + '</option>');
                        });
                    }

                    // Giữ lại lựa chọn trước đó nếu tồn tại
                    if (productLineId) {
                        $('#productLineSelect').val(productLineId); // Gán lại productLineId trước đó
                    }
                }
            });
        });
        // Trigger event change để tải danh sách product lines ban đầu
        if ($('#brandSelect').val()) {
            $('#brandSelect').trigger('change');
        }
    });
</script>
<script>
    document.querySelectorAll('.currency').forEach(element => {
        const value = parseFloat(element.innerText);
        element.innerText = formatToVND(value);
    });
</script>
</body>