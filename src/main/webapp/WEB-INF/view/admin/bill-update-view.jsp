<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<head>
    <title>ChronoLux - Hóa đơn</title>
    <!-- Custom fonts for this template-->
    <link href="<c:url value='/template/admin/vendor/fontawesome-free/css/all.min.css'/>" rel="stylesheet"
          type="text/css">
    <link href="<c:url value='/template/admin/css/styles.css'/>" rel="stylesheet"
          type="text/css">
    <link
            href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
            rel="stylesheet">
    <link href="<c:url value='/template/admin/css/sb-admin-2.min.css'/>" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
    <script src="<c:url value='/template/admin/js/script.js'/>"></script>
</head>
<body>
<!-- Begin Page Content -->
<div class="container-fluid">
    <div class="d-sm-flex align-items-center justify-content-start mb-3">
        <i class="h3 mb-1 mr-3 fa-solid fa-receipt" style="font-size: 28px"></i>
        <h1 class="h3 mb-1 text-gray-800">Cập nhật đơn hàng</h1>
    </div>
    <hr>
    <form id="searchForm" action="${pageContext.request.contextPath}/admin/bill/search" method="get">
        <div class="d-flex justify-content-end">
            <a href="${pageContext.request.contextPath}/admin/bills" class="btn btn-warning">
                <i class="fa fa-reply mr-1" aria-hidden="true"></i>
                Quay lại
            </a>
        </div>
    </form>
    <div class="card shadow mb-4 mt-4">
        <div class="card-body">
            <div class="table-responsive table-sm py-3">
                <table class="table table-bordered text-center align-middle" width="100%" cellspacing="0">
                    <thead class="table-secondary">
                    <tr>
                        <th>Mã hóa đơn</th>
                        <th>Thời gian đặt</th>
                        <th>Khách hàng</th>
                        <th>SĐT</th>
                        <th>Tổng đơn hàng</th>
                        <th>Thanh toán</th>
                        <th>Giao hàng</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="ub" items="${billPage.content}">
                        <tr>
                            <td>${ub.id}</td>
                            <td>
                                <fmt:formatDate value="${ub.createdDate}" pattern="dd/MM/yyyy HH:mm"/>
                            </td>
                            <td>${ub.displayName}</td>
                            <td>${ub.phone}</td>
                            <td class="currency">${ub.total}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${ub.status == 'Chưa thanh toán' and ub.deliveryStatus == 'PENDING'}">
                                        <button type="button" class="btn btn-xs btn-success btn-sm" data-toggle="modal"
                                                data-target="#confirmPaidModal${ub.id}">
                                            <i class="fa-solid fa-check mr-1"></i>
                                            Xác nhận thanh toán
                                        </button>
                                    </c:when>
                                    <c:when test="${ub.status == 'Chờ hoàn tiền' and ub.deliveryStatus == 'CANCELLED'}">
                                        <button type="button" class="btn btn-xs btn-warning btn-sm" data-toggle="modal"
                                                data-target="#confirmRefundModal${ub.id}">
                                            <i class="fa-solid fa-check mr-1"></i>
                                            Xác nhận hoàn tiền
                                        </button>
                                    </c:when>
                                    <c:when test="${ub.status == 'Đã thanh toán' and ub.deliveryStatus == 'PENDING'}">
                                        <span class="badge badge-success"
                                              style="font-size: 14px;">${ub.status}</span>
                                    </c:when>
                                    <c:when test="${ub.status == 'Thanh toán thành công'}">
                                        <span class="badge badge-success" style="font-size: 14px;">Đã thanh toán</span>
                                    </c:when>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${ub.deliveryStatus == 'PENDING'}">
                                        <button type="button" class="btn btn-info btn-sm mr-2" data-toggle="modal"
                                                data-target="#confirmReceivedModal${ub.id}">
                                            <i class="fa-solid fa-check-to-slot mr-1"></i>Khách đã nhận
                                        </button>
                                        <button type="button" class="btn btn-danger btn-sm mr-2" data-toggle="modal"
                                                data-target="#confirmCancelModal${ub.id}">
                                            <i class="fa-solid fa-xmark mr-1"></i>Khách hủy
                                        </button>
                                    </c:when>
                                    <c:when test="${ub.deliveryStatus == 'CANCELLED'}">
                                        <span class="badge badge-danger"
                                              style="font-size: 14px;">${ub.deliveryStatus.displayName}</span>
                                    </c:when>
                                </c:choose>
                            </td>
                        </tr>
                        <!-- Modal refund bill-->
                        <div class="modal fade" id="confirmRefundModal${ub.id}" tabindex="-1" role="dialog"
                             aria-labelledby="confirmModalLabel${ub.id}" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="confirmModalLabel${ub.id}">Xác nhận hoàn tiền</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        Xác nhận hoàn tiền cho đơn đơn hàng của <strong>${ub.displayName}</strong>?
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">
                                            <i class="fa-solid fa-reply mr-1"></i>
                                            Trở lại
                                        </button>
                                        <a href="${pageContext.request.contextPath}/admin/bill/view-update/confirm-refund?id=${ub.id}"
                                           class="btn btn-warning">
                                            <i class="fa-solid fa-check mr-1"></i>
                                            Xác nhận
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- Modal pay bill-->
                        <div class="modal fade" id="confirmPaidModal${ub.id}" tabindex="-1" role="dialog"
                             aria-labelledby="confirmModalLabel${ub.id}" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="confirmModalLabel${ub.id}">Xác nhận thanh toán</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        Xác nhận đơn hàng của <strong>${ub.displayName}</strong> đã thanh toán?
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">
                                            <i class="fa-solid fa-reply mr-1"></i>
                                            Trở lại
                                        </button>
                                        <a href="${pageContext.request.contextPath}/admin/bill/view-update/confirm-paid?id=${ub.id}"
                                           class="btn btn-success">
                                            <i class="fa-solid fa-check mr-1"></i>
                                            Xác nhận
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <%--Modal delivery--%>
                        <div class="modal fade" id="confirmReceivedModal${ub.id}" tabindex="-1" role="dialog"
                             aria-labelledby="confirmModalLabel${ub.id}" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="confirmModalLabel${ub.id}">Xác nhận đã giao
                                            hàng</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        Xác nhận khách hàng <strong>${ub.displayName}</strong> đã nhận được hàng?
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">
                                            <i class="fa-solid fa-reply mr-1"></i>
                                            Trở lại
                                        </button>
                                        <a href="${pageContext.request.contextPath}/admin/bill/view-update/confirm-received?id=${ub.id}"
                                           class="btn btn-info">
                                            <i class="fa-solid fa-check mr-1"></i>
                                            Xác nhận
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal fade" id="confirmCancelModal${ub.id}" tabindex="-1" role="dialog"
                             aria-labelledby="confirmModalLabel${ub.id}" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
                                <div class="modal-content">

                                    <div class="modal-header">
                                        <h5 class="modal-title" id="confirmModalLabel${ub.id}">Xác nhận hủy đơn
                                            hàng</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <form id="cancelForm${ub.id}" action="<c:url value='/admin/bill/view-update/confirm-cancel?id=${ub.id}'/>"
                                          method="post">
                                        <div class="modal-body">
                                            <p>Lý do khách hủy đơn hàng:</p>
                                            <input type="hidden" name="orderId" value="${ub.id}">
                                            <div class="form-check">
                                                <input class="form-check-input" type="radio" name="cancelReason"
                                                       id="reason1${ub.id}" value="Muốn thay đổi địa chỉ giao hàng">
                                                <label class="form-check-label" for="reason1${ub.id}">Muốn thay đổi địa
                                                    chỉ giao hàng</label>
                                            </div>
                                            <div class="form-check">
                                                <input class="form-check-input" type="radio" name="cancelReason"
                                                       id="reason2${ub.id}" value="Muốn nhập/thay đổi mã Voucher">
                                                <label class="form-check-label" for="reason2${ub.id}">Muốn nhập/thay đổi
                                                    mã Voucher</label>
                                            </div>
                                            <div class="form-check">
                                                <input class="form-check-input" type="radio" name="cancelReason"
                                                       id="reason3${ub.id}"
                                                       value="Muốn thay đổi sản phẩm trong đơn hàng(size,màu sắc,số lượng,...)">
                                                <label class="form-check-label" for="reason3${ub.id}">Muốn thay đổi sản
                                                    phẩm trong đơn hàng</label>
                                            </div>
                                            <div class="form-check">
                                                <input class="form-check-input" type="radio" name="cancelReason"
                                                       id="reason4${ub.id}" value="Tìm được giá rẻ hơn">
                                                <label class="form-check-label" for="reason4${ub.id}">Tìm được giá rẻ
                                                    hơn ở chỗ khác</label>
                                            </div>
                                            <div class="form-check">
                                                <input class="form-check-input" type="radio" name="cancelReason"
                                                       id="reason5${ub.id}" value="Thời gian giao hàng lâu">
                                                <label class="form-check-label" for="reason5${ub.id}">Thời gian giao
                                                    hàng lâu</label>
                                            </div>
                                            <div class="form-check">
                                                <input class="form-check-input" type="radio" checked name="cancelReason"
                                                       id="reason6${ub.id}" value="Đổi ý, không muốn mua nữa">
                                                <label class="form-check-label" for="reason6${ub.id}">Đổi ý, không muốn
                                                    mua nữa</label>
                                            </div>
                                            <div class="form-check">
                                                <input class="form-check-input" type="radio" name="cancelReason"
                                                       id="reason7${ub.id}" value="Khác">
                                                <label class="form-check-label" for="reason7${ub.id}">Khác</label>
                                            </div>
                                        </div>
                                    </form>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">
                                            <i class="fa-solid fa-reply mr-1"></i>
                                            Trở lại
                                        </button>
                                        <button type="submit" class="btn btn-danger"
                                                onclick="submitCancelForm('${ub.id}')">
                                            <i class="fa-solid fa-check mr-1"></i>
                                            Xác nhận
                                        </button>
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
                            <a class="page-link" href="?page=${currentPage - 1}&size=${billPage.size}"
                               aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                                <span class="sr-only">Previous</span>
                            </a>
                        </li>
                    </c:if>
                    <!-- Hiển thị các số trang -->
                    <c:forEach var="i" begin="1" end="${billPage.totalPages}">
                        <li class="page-item ${i == currentPage ? 'active' : ''}">
                            <a class="page-link" href="?page=${i}&size=${billPage.size}">${i}</a>
                        </li>
                    </c:forEach>
                    <!-- Nút Next -->
                    <c:if test="${currentPage < billPage.totalPages}">
                        <li class="page-item">
                            <a class="page-link" href="?page=${currentPage + 1}&size=${billPage.size}"
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
<script>
    function submitCancelForm(orderId) {

        const form = document.getElementById('cancelForm' + orderId);

        const selectedReason = document.querySelectorAll(`input[name="cancelReason"]:checked`);

        if (!selectedReason) {
            alert("Vui lòng chọn lý do hủy đơn hàng!");
            return;
        }

        form.submit();
    }
</script>
<script>
    document.querySelectorAll('.currency').forEach(element => {
        const value = parseFloat(element.innerText);
        element.innerText = formatToVND(value);
    });
</script>
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
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
</body>
<script>
    document.getElementById("searchInput").addEventListener("keypress", function (event) {
        if (event.key === "Enter") {
            event.preventDefault();
            document.getElementById("searchForm").submit();
        }
    });
</script>