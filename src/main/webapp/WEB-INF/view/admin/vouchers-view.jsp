<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <title>ChronoLux - Quản lý voucher</title>
    <!-- Custom fonts for this template-->
    <link href="<c:url value='/template/admin/vendor/fontawesome-free/css/all.min.css'/>" rel="stylesheet"
          type="text/css">
    <link
            href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
            rel="stylesheet">
    <link href="<c:url value='/template/admin/css/sb-admin-2.min.css'/>" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
    <script src="<c:url value='/template/admin/js/script.js'/>"></script>

    <%--<script src="<c:url value='/template/admin/vendor/jquery/jquery.js'/>"></script>--%>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="<c:url value='/template/admin/vendor/bootstrap/js/bootstrap.bundle.js'/>"></script>
    <!-- jQuery -->
</head>
<body>
<!-- Begin Page Content -->
<div class="container-fluid">
    <div class="d-sm-flex align-items-center justify-content-start mb-3">
        <i class="h3 mb-1 mr-3 fa-solid fa-ticket" style="font-size: 28px"></i>
        <h1 class="h3 mb-1 text-gray-800">Mã giảm giá</h1>
    </div>
    <nav>
        <div class="nav nav-tabs" id="nav-tab" role="tablist">
            <button class="nav-link active" id="nav-admin-tab" data-bs-toggle="tab" data-bs-target="#nav-admin"
                    type="button" role="tab" aria-controls="nav-admin" aria-selected="true">Voucher khả dụng
            </button>
            <button class="nav-link" id="nav-user-tab" data-bs-toggle="tab" data-bs-target="#nav-user" type="button"
                    role="tab" aria-controls="nav-user" aria-selected="false">Voucher hết hạn
            </button>
        </div>
    </nav>
    <div class="tab-content" id="nav-tabContent">
        <%--Admin--%>
        <div class="tab-pane fade show active" id="nav-admin" role="tabpanel" aria-labelledby="nav-admin-tab">
            <div class="mt-4">
                <div class="d-flex justify-content-end">
                    <button type="button" id="openVoucherConfigModal" da class="btn btn-secondary mr-2 mt-1">
                        <i class="fa-solid fa-gear mr-1"></i> Cài đặt voucher tự động
                    </button>

                    <a href="${pageContext.request.contextPath}/admin/voucher/create" class="btn btn-success mr-2 mt-1">
                        <i class="fas fa-plus mr-1"></i> Thêm voucher
                    </a>
                </div>
                <div class="card shadow mb-4 mt-4">
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-bordered text-center align-middle" width="100%" cellspacing="0">
                                <thead class="table-primary">
                                <tr>
                                    <th>Mã</th>
                                    <th>Giảm giá</th>
                                    <th>Ngày bắt đầu</th>
                                    <th>Ngày kết thúc</th>
                                    <th>Ngày tạo</th>
                                    <th>Loại mã</th>
                                    <th>Hành động</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="v" items="${validVouchers.content}">
                                    <tr>
                                        <td>${v.code}</td>
                                        <td class="currency">${v.discount}</td>
                                        <td>${v.beginDay}</td>
                                        <td>${v.endDay}</td>
                                        <td>${v.createdDate}</td>
                                        <td>
                                            <span class="badge badge-primary"
                                                  style="font-size: 14px;">${v.voucherType.displayName}
                                            </span>
                                        </td>
                                        <td class="align-items-center justify-content-center d-flex">
                                            <button type="button" class="btn btn-danger btn-sm" data-toggle="modal"
                                                    data-target="#confirmModal${v.id}">
                                                <i class="fas fa-trash-alt mr-1"></i> Xóa
                                            </button>
                                        </td>
                                    </tr>
                                    <div class="modal fade" id="confirmModal${v.id}" tabindex="-1" role="dialog"
                                         aria-labelledby="confirmModalLabel${v.id}" aria-hidden="true">
                                        <div class="modal-dialog modal-dialog-centered" role="document">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title" id="confirmModalLabel${v.id}">XÁC NHẬN
                                                        XÓA</h5>
                                                    <button type="button" class="close" data-dismiss="modal"
                                                            aria-label="Close">
                                                        <span aria-hidden="true">&times;</span>
                                                    </button>
                                                </div>
                                                <div class="modal-body">
                                                    Bạn có chắc muốn xóa voucher <strong>${v.code}</strong>
                                                    không?
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-secondary"
                                                            data-dismiss="modal">
                                                        <i class="fa-solid fa-reply mr-1"></i>
                                                        Trở lại
                                                    </button>
                                                    <a href="${pageContext.request.contextPath}/admin/voucher/delete?id=${v.id}"
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
                    </div>
                </div>
            </div>
        </div>

        <div class="tab-pane fade" id="nav-user" role="tabpanel" aria-labelledby="nav-user-tab">
            <div class="card shadow mb-4 mt-4">
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-bordered" width="100%" cellspacing="0">
                            <thead class="table-danger">
                            <tr>
                                <th>Mã</th>
                                <th>Giảm giá</th>
                                <th>Ngày bắt đầu</th>
                                <th>Ngày kết thúc</th>
                                <th>Ngày tạo</th>
                                <th>Loại mã</th>
                                <th>Hành động</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="v" items="${expiredVouchers.content}">
                                <tr>
                                    <td>${v.code}</td>
                                    <td class="currency">${v.discount}</td>
                                    <td>${v.beginDay}</td>
                                    <td>${v.endDay}</td>
                                    <td>${v.createdDate}</td>
                                    <td>
                                            <span class="badge badge-secondary"
                                                  style="font-size: 14px;">${v.voucherType.displayName}
                                            </span>
                                        </td>
                                    <td class="align-items-center justify-content-center d-flex">
                                        <button type="button" class="btn btn-danger btn-sm" data-toggle="modal"
                                                data-target="#confirmModal${v.id}">
                                            <i class="fas fa-trash-alt mr-1"></i> Xóa
                                        </button>
                                    </td>
                                </tr>
                                <div class="modal fade" id="confirmModal${v.id}" tabindex="-1" role="dialog"
                                     aria-labelledby="confirmModalLabel${v.id}" aria-hidden="true">
                                    <div class="modal-dialog modal-dialog-centered" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="confirmModalLabel${v.id}">XÁC NHẬN
                                                    XÓA</h5>
                                                <button type="button" class="close" data-dismiss="modal"
                                                        aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                                Bạn có chắc muốn xóa voucher <strong>${v.code}</strong>
                                                không?
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary"
                                                        data-dismiss="modal">
                                                    <i class="fa-solid fa-reply mr-1"></i>
                                                    Trở lại
                                                </button>
                                                <a href="${pageContext.request.contextPath}/admin/voucher/delete?id=${v.id}"
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
                </div>
            </div>
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
<div aria-live="polite" aria-atomic="true" style="position: fixed; bottom: 1rem; right: 1rem; z-index: 1050;">
    <div class="toast" id="errorToast" role="alert" aria-live="assertive" aria-atomic="true" data-delay="3500">
        <div class="toast-header bg-danger text-white">
            <strong class="mr-auto custom-font-20">Thông báo</strong>
            <button type="button" class="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
        <div class="toast-body">
            <span id="toastErrorMessage" class="custom-font-20">Thêm thành công</span>
        </div>
    </div>
</div>
<div aria-live="polite" aria-atomic="true" style="position: fixed; bottom: 1rem; right: 1rem; z-index: 1050;">
    <div class="toast" id="configSuccessToast" role="alert" aria-live="assertive" aria-atomic="true" data-delay="3500">
        <div class="toast-header bg-success text-white">
            <strong class="mr-auto custom-font-20">Thông báo</strong>
            <button type="button" class="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
        <div class="toast-body">
            <span id="configToastMessage" class="custom-font-20">Thêm thành công</span>
        </div>
    </div>
</div>
<div class="modal fade" id="voucherConfigModal" tabindex="-1" role="dialog"
     aria-labelledby="voucherConfigLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">
                    <i class="fa-solid fa-gear mr-1"></i> CÀI ĐẶT VOUCHER
                </h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form id="voucherConfigForm">
                    <div class="form-group">
                        <label for="prefix">Tiền tố</label>
                        <input type="text" class="form-control" id="prefix" name="prefix">
                    </div>
                    <div class="form-group">
                        <label for="discountDefault">Số tiền giảm mặc định</label>
                        <input type="number" class="form-control" id="discountDefault" name="discountDefault">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">
                    <i class="fa-solid fa-reply mr-1"></i> Hủy
                </button>
                <button type="button" class="btn btn-primary" id="saveVoucherConfig">
                    <i class="fa-solid fa-check mr-1"></i> Lưu cài đặt
                </button>
            </div>
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
    document.addEventListener("DOMContentLoaded", function () {
        const errorMessage = "${errorMessage}";
        if (errorMessage) {
            const toastElement = document.getElementById("errorToast");
            const toast = new bootstrap.Toast(toastElement);
            document.getElementById("toastErrorMessage").innerText = errorMessage;
            toast.show();
        }
    });
</script>
<script>
    $(document).ready(function () {
        let id = null;
        $("#openVoucherConfigModal").click(function () {
            $.ajax({
                url: "${pageContext.request.contextPath}/admin/default-voucher",
                type: "GET",
                dataType: "json",
                success: function (data) {
                    id = data.id;
                    $("#prefix").val(data.prefix);
                    $("#discountDefault").val(data.discountDefault);
                    $("#voucherConfigModal").modal("show");
                },
                error: function () {
                    alert("Không thể tải dữ liệu voucher config!");
                }
            });
        });

        $(".close, .btn-secondary").click(function () {
            $("#voucherConfigModal").modal("hide");
        });

        $("#saveVoucherConfig").click(function () {
            var configData = {
                id: id,
                prefix: $("#prefix").val(),
                discountDefault: $("#discountDefault").val()
            };

            console.log("data: " + configData);

            $.ajax({
                url: "${pageContext.request.contextPath}/admin/default-voucher",
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify(configData),
                success: function (response) {
                    $("#configToastMessage").text("Cập nhật voucher mặc định thành công!");
                    $("#configSuccessToast").toast("show");
                    $("#voucherConfigModal").modal("hide");
                },
                error: function (e) {
                    console.log("ERROR: ", e);
                    alert("Lỗi khi lưu dữ liệu!");
                }
            });
        });
    });
</script>
<script>
    document.querySelectorAll('.currency').forEach(element => {
        const value = parseFloat(element.innerText);
        element.innerText = formatToVND(value);
    });
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>

</body>
