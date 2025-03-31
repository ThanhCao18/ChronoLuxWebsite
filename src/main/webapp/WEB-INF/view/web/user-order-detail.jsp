<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglib.jsp" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <title>ChronoLux - Profile</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="Free HTML Templates" name="keywords">
    <meta content="Free HTML Templates" name="description">

    <link href="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js" rel="stylesheet">


    <!-- Font Awesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">



    <!-- Customized Bootstrap Stylesheet -->
    <link href=" <c:url value='/template/web/cssviewprofile/style.css'/>" rel="stylesheet">
    <link href=" <c:url value='/template/web/css/style.css'/>" rel="stylesheet">
    <%----------------------------%>
    <style>



        .order-container {
            width: 100%;
            margin: 20px auto;
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            background: #fff;
        }

        .order-header {
            font-size: 18px;
            font-weight: bold;
            padding: 15px;
            background: #f8f9fa;
            border-bottom: 2px solid #ddd;
        }



        .order-table th, .order-table td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        .custom-container {
            margin-left: 100px;
            margin-right: 100px;
            padding-left: 10px;
            padding-right: 10px;
        }


    </style>
</head>

<body>
<div class=" custom-container">
    <div class="d-flex justify-content-end align-items-end p-3">
        <a href="${pageContext.request.contextPath}/user-order" class="btn btn-warning ms-auto">
            <i class="fa-solid fa-reply mr-1"></i>Quay lại
        </a>
    </div>
    <!-- Thông tin hóa đơn -->
    <div class="card shadow-sm mb-4">
        <div class="card-header bg-primary text-white text-center" style="font-size: 26px; text-transform: uppercase">Thông tin hóa đơn</div>

        <div class="card-body">
            <h4><strong>Mã hóa đơn: </strong>${billDTO.id} </h4>
            <!-- Thông tin người nhận -->
            <h4><strong>Thông tin khách hàng</strong></h4>
            <div class="row mb-2">
                <div class="col-md-6"><strong>Người đặt:</strong> ${billDTO.displayName}</div>
                <div class="col-md-6">
                    <strong>Ngày đặt:</strong> <fmt:formatDate value="${billDTO.createdDate}" pattern="dd/MM/yyyy - HH:mm"/>
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
            <div class=""><strong>Địa chỉ:</strong> ${billDTO.street}, ${billDTO.ward}, ${billDTO.district}, ${billDTO.city}</div>
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
                        <td><img src="${pageContext.request.contextPath}/template/web/img/products/${item.image}" alt="${item.name}" width="80"></td>
                        <td style="text-align: right;">${item.name}</td>
                        <td style="text-align: right;">${item.quantity}</td>
                        <td style="text-align: right;"><span id ="price" class="currency">${item.price}</span></td>
                        <td style="text-align: right;"><span id ="price" class="currency">${item.total}</span></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <hr style="height: 3px; background-color: #333; border: none;">
            <div class="row">
                <div class="col-md-6"><strong>Tạm tính:</strong></div>
                <div class="col-md-6 text-right"><span id ="price" class="currency">${billDTO.subtotal}</span></div>
            </div>
            <div class="row">
                <div class="col-md-6"><strong>Voucher:</strong></div>
                <div class="col-md-6 text-right"><span>${billDTO.voucherCode}</span></div>
            </div>
            <div class="row">
                <div class="col-md-6"><strong>Giảm giá:</strong></div>
                <div class="col-md-6 text-right"><span id ="price" class="currency">${billDTO.discount}</span></div>
            </div>
            <div class="row">
                <div class="col-md-6" style="font-size: 20px;"><strong>Tổng tiền phải trả:</strong></div>
                <div class="col-md-6 text-right font-weight-bold" ><span id ="price" class="currency" style="font-size: 20px;">${billDTO.total}</span></div>
            </div>
        </div>
    </div>

</div>



<script>
    document.querySelectorAll('#price').forEach(element => {
        let price = parseFloat(element.innerText.replace("đ", "").trim()); // Chuyển từ chuỗi sang số
        element.innerText = price.toLocaleString("vi-VN", { style: "currency", currency: "VND" }); // Format tiền tệ VNĐ
    });
</script>




</body>

</html>