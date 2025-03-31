<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<head>
    <title>ChronoLux - Chi tiết đơn hàng</title>
    <link href="<c:url value='/template/admin/vendor/fontawesome-free/css/all.min.css'/>" rel="stylesheet"
          type="text/css">
    <link
            href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
            rel="stylesheet">
    <link href="<c:url value='/template/admin/css/sb-admin-2.min.css'/>" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
    <script src="<c:url value='/template/admin/js/script.js'/>"></script>
        <link href="<c:url value='/template/admin/css/styles.css'/>" rel="stylesheet"
          type="text/css">
</head>
<body>
<div class="container">
    <div class="d-flex justify-content-between align-items-center">
        <h2 class="text-start">
            <i class="fa-solid fa-file-invoice me-2" style="font-size: 26px"></i>
            Chi tiết đơn hàng
        </h2>
        <a href="${pageContext.request.contextPath}/admin/bills?page=${currentPage}" class="btn btn-warning ms-auto">
            <i class="fa-solid fa-reply mr-1"></i>Quay lại
        </a>
    </div>
     <input type="hidden" name="page" value="${currentPage}">
    <hr>
    <!-- Thông tin hóa đơn -->
    <c:if test="${billDTO.deliveryStatus == 'CANCELLED'}">
        <p class="text-danger font-weight-bold">HÓA ĐƠN ĐÃ BỊ HỦY</p>
        <p><strong>Lý do hủy:</strong> ${billDTO.cancelReason}</p>
    </c:if>
    <div class="card shadow-sm mb-4 ${billDTO.deliveryStatus == 'CANCELLED' ? 'dim-content' : ''}">
        <div class="card-header bg-primary text-white text-center" style="font-size: 26px; text-transform: uppercase">
            Thông tin đơn hàng
        </div>
        <div class="card-body <c:if test='${billDTO.deliveryStatus == "CANCELLED"}'>opacity: 50</c:if>'">
            <h4><strong>Mã đơn hàng: </strong>${billDTO.id} </h4>
            <!-- Thông tin người nhận -->
            <h4><strong>Thông tin khách hàng</strong></h4>
            <div class="row mb-2">
                <div class="col-md-6"><strong>Người đặt:</strong> ${billDTO.displayName}</div>
                <div class="col-md-6">
                    <strong>Ngày đặt:</strong> <fmt:formatDate value="${billDTO.createdDate}"
                                                               pattern="dd/MM/yyyy - HH:mm"/>
                </div>
            </div>
            <div class="row mb-2">
                <div class="col-md-6"><strong>Người nhận:</strong> ${billDTO.receiverName}</div>
                <div class="col-md-6"><strong>Hình thức thanh toán:</strong> ${billDTO.paymentMethod}</div>
            </div>
            <div class="row mb-2">
                <div class="col-md-6"><strong>Email:</strong> ${billDTO.email}</div>
                <div class="col-md-6"><strong>Điện thoại:</strong> ${billDTO.phone}</div>
            </div>
            <div class=""><strong>Địa
                chỉ:</strong> ${billDTO.street}, ${billDTO.ward}, ${billDTO.district}, ${billDTO.city}</div>
            <h4 class="mt-4"><strong>Danh sách sản phẩm</strong></h4>
            <table class="table table-sm mt-1">
                <thead>
                <tr>
                    <th scope="col">Hình ảnh sản phẩm</th>
                    <th scope="col" style="text-align: right;">Tên sản phẩm</th>
                    <th scope="col" style="text-align: right;">Số lượng</th>
                    <th scope="col" style="text-align: right;">Đơn giá</th>
                    <th scope="col" style="text-align: right;">Tổng giá</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="item" items="${billDTO.productBillSnapshotDTOS}">
                    <tr>
                        <td><img src="${pageContext.request.contextPath}/template/web/img/products/${item.image}"
                                 alt="${item.name}" width="80"></td>
                        <td style="text-align: right;">${item.name}</td>
                        <td style="text-align: right;">${item.quantity}</td>
                        <td style="text-align: right;"><span class="currency">${item.price}</span></td>
                        <td style="text-align: right;"><span class="currency">${item.total}</span></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <hr style="height: 3px; background-color: #333; border: none;">
            <div class="row">
                <div class="col-md-6"><strong>Tạm tính:</strong></div>
                <div class="col-md-6 text-right"><span class="currency">${billDTO.subtotal}</span></div>
            </div>
            <div class="row">
                <div class="col-md-6"><strong>Voucher:</strong></div>
                <div class="col-md-6 text-right"><span>${billDTO.voucherCode}</span></div>
            </div>
            <div class="row">
                <div class="col-md-6"><strong>Giảm giá:</strong></div>
                <div class="col-md-6 text-right"><span class="currency">${billDTO.discount}</span></div>
            </div>
            <div class="row">
                <div class="col-md-6" style="font-size: 20px;"><strong>Tổng tiền phải trả:</strong></div>
                <div class="col-md-6 text-right font-weight-bold"><span class="currency"
                                                                        style="font-size: 20px;">${billDTO.total}</span>
                </div>
            </div>
        </div>
    </div>

</div>

<script>
    document.querySelectorAll('.currency').forEach(element => {
        const value = parseFloat(element.innerText);
        element.innerText = formatToVND(value);
    });
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
