<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ include file="/common/taglib.jsp" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="utf-8">
            <title>ChronoLux - Checkout</title>
            <meta content="width=device-width, initial-scale=1.0" name="viewport">
            <meta content="Free HTML Templates" name="keywords">
            <meta content="Free HTML Templates" name="description">
            <!-- Favicon -->
            <link href="<c:url value='/template/web/img/ChronoLuxIcon.svg'/>" rel="icon">

            <!-- Google Web Fonts -->
            <link rel="preconnect" href="https://fonts.gstatic.com">
            <link
                href="https://fonts.googleapis.com/css2?family=Poppins:wght@100;200;300;400;500;600;700;800;900&display=swap"
                rel="stylesheet">

            <!-- Font Awesome -->
            <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
            <script
                src="https://www.paypal.com/sdk/js?client-id=test&buyer-country=US&currency=USD&components=buttons&enable-funding=venmo,paylater,card"
                data-sdk-integration-source="developer-studio"></script>
            <!-- Libraries Stylesheet -->
            <link href="<c:url value='/template/web/lib/owlcarousel/assets/owl.carousel.min.css'/>" rel="stylesheet">

            <!-- Customized Bootstrap Stylesheet -->
            <link href=" <c:url value='/template/web/css/style.css'/>" rel="stylesheet">

            <script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.21.1/axios.min.js"></script>
            <%----------------------------%>
                <style>
                    .cart-item {
                        display: flex;
                        border: 1px solid #ddd;
                        border-radius: 8px;
                        margin-bottom: 15px;
                        padding: 10px;
                        align-items: center;
                    }

                    .cart-item-content {
                        display: flex;
                        align-items: center;
                        width: 100%;
                    }

                    .remove-btn {
                        background: none;
                        border: none;
                        font-size: 18px;
                        cursor: pointer;
                        color: #999;
                        margin-right: 10px;
                    }

                    .product-image {
                        width: 60px;
                        height: 60px;
                        border-radius: 8px;
                        margin-right: 15px;
                    }

                    .product-details {
                        flex: 1;
                    }

                    .product-name {
                        font-weight: bold;
                    }

                    .product-price {
                        color: red;
                    }

                    .current-price {
                        font-size: 18px;
                        font-weight: bold;
                        color: red;
                        margin-right: 10px;
                    }

                    .old-price {
                        font-size: 14px;
                        text-decoration: line-through;
                        color: #999;
                    }

                    .quantity-control {
                        display: flex;
                        align-items: center;
                    }

                    .decrease-btn,
                    .increase-btn {
                        background-color: #f0f0f0;
                        border: none;
                        width: 30px;
                        height: 30px;
                        font-size: 18px;
                        cursor: pointer;
                    }

                    .quantity-input {
                        width: 40px;
                        text-align: center;
                        border: 1px solid #ddd;
                        border-radius: 4px;
                        margin: 0 5px;
                    }

                    .form-group {
                        display: flex;
                        gap: 15px;
                        margin-bottom: 15px;
                        flex-wrap: wrap;
                    }

                    .form-control {
                        flex: 1;
                        min-width: 200px;
                        padding: 10px;
                        border: 1px solid #ddd;
                        border-radius: 4px;
                        height: 45px;
                    }

                    select.form-control {
                        cursor: pointer;
                    }

                    .paypal-button {
                        background-color: #ffc439;
                        /* Màu nền vàng PayPal */
                        color: #111;
                        /* Màu chữ */
                        border: none;
                        padding: 12px 24px;
                        font-size: 16px;
                        font-weight: 600;
                        width: 470px;
                        border-radius: 4px;
                        cursor: pointer;
                        box-shadow: 0 2px 3px rgba(0, 0, 0, 0.2);
                        display: inline-flex;
                        align-items: center;
                        justify-content: center;
                        transition: background-color 0.3s ease;
                    }

                    .paypal-button:hover {
                        background-color: #e0b822;
                        /* Màu nền khi hover */
                    }

                    .paypal-logo {
                        width: 20px;
                        height: 20px;
                        background-image: url('https://www.paypalobjects.com/webstatic/icon/pp258.png');
                        background-size: contain;
                        background-repeat: no-repeat;
                        margin-right: 8px;
                    }

                    .paypal-text {
                        display: inline-block;
                    }

                    .payment-methods {
                        max-width: 600px;
                        margin: 0 auto;
                        border: 1px solid #ddd;
                        border-radius: 5px;
                        padding: 20px;
                        font-family: Arial, sans-serif;
                    }

                    .payment-methods h3 {
                        margin-bottom: 20px;
                        font-size: 18px;
                    }


                    .payment-option input[type="radio"] {
                        margin-right: 10px;
                    }

                    .payment-details {
                        margin-left: 30px;
                        font-size: 14px;
                        color: #555;
                    }

                    .bank-details {
                        margin-top: 10px;
                        font-size: 12px;
                        color: #777;
                    }

                    .complete-order {
                        text-align: right;
                    }

                    .complete-order button {
                        background-color: #3498db;
                        color: white;
                        border: none;
                        padding: 10px 20px;
                        border-radius: 5px;
                        cursor: pointer;
                    }

                    .complete-order button:hover {
                        background-color: #2980b9;
                    }

                    .cart-link {
                        display: block;
                        margin-top: 20px;
                        text-align: left;
                        color: #3498db;
                        text-decoration: none;
                        font-size: 14px;
                    }

                    .cart-link:hover {
                        text-decoration: underline;
                    }
                    .modal {
                        display: none;
                        position: fixed;
                        z-index: 1000;
                        left: 0;
                        top: 0;
                        width: 100%;
                        height: 100%;
                        background-color: rgba(0, 0, 0, 0.5);
                        align-items: center;  /* ✅ Centers vertically */
                        justify-content: center;  /* ✅ Centers horizontally */
                    }

                    .modal-content {
                        background-color: white;
                        padding: 20px;
                        border-radius: 10px;
                        width: 40%;
                        max-width: 500px; /* ✅ Ensures responsiveness */
                        text-align: center;
                        position: relative;
                        box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2); /* ✅ Adds slight shadow */
                    }


                    .close {
                        position: absolute;
                        top: 10px;
                        right: 15px;
                        font-size: 20px;
                        cursor: pointer;
                    }

                    .qr-code img {
                        max-width: 100%;
                        height: auto;
                    }
                </style>
        </head>

        <body>
            <!-- Page Header Start -->
            <div class="container-fluid bg-light ">
                <div class="d-flex flex-column align-items-center justify-content-center" style="min-height: 150px">
                    <h1 class="font-weight-semi-bold text-uppercase mb-3">Thanh Toán</h1>
                </div>
            </div>
            <!-- Page Header End -->


            <!-- Checkout Start -->
            <div class="container-fluid pt-5">
                <form action="<c:url value='/checkout'/>" method="post" id="paymentForm1">
                    <div class="row px-xl-5 d-flex align-items-center justify-content-center border-dark">
                        <div class="col-lg-7">
                            <div class="mb-4 rounded-5 bg-white shadow px-4 py-4" style="border-radius: 1rem; min-width: 350px;">
                                <h4 class="font-weight-semi-bold mb-4 text-center" style="font-family: Arial">Thông Tin Nhận Hàng</h4>
                                <!-- HTML -->
                                <form class="order-form p-4">
                                    <p class="small text-muted">Vui lòng điền thông tin của bạn để chúng tôi giao hàng đến địa chỉ của bạn</p>
                                    <p class="small text-muted">Các trường có dấu (*) là bắt buộc</p>
                                    <div class="mb-3">
                                        <div class="form-check form-check-inline">
                                            <input class="form-check-input" type="radio" name="gender" id="male"
                                                value="Nam" checked required>
                                            <label class="form-check-label" for="male">Anh</label>
                                        </div>
                                        <div class="form-check form-check-inline">
                                            <input class="form-check-input" type="radio" name="gender" id="female"
                                                value="Nữ" required>
                                            <label class="form-check-label" for="female">Chị</label>
                                        </div>
                                    </div>

                                    <div class="row g-3 mb-3">
                                        <div class="col-md-6">
                                            <input type="text" name="displayName" value="${user.fullName}" class="form-control"
                                                placeholder="Họ và Tên *" required>
                                        </div>
                                        <div class="col-md-6">
                                            <input type="text" name="phone" class="form-control"
                                                placeholder="Số điện thoại *" required>
                                        </div>
                                    </div>

                                    <div class="mb-3">
                                        <input type="email" name="email" value="${user.email}" class="form-control"
                                            placeholder="Email *" required>
                                    </div>

                                    <p class="small text-muted mb-3">
                                        Nếu bạn không có Email, vui lòng nhắn tin
                                        <a href="https://www.messenger.com" target="_blank"><b>Messenger</b></a>
                                        hoặc
                                        <a href="https://zalo.me" target="_blank"><b>Chat Zalo</b></a>
                                        để đặt hàng
                                    </p>

                                   <div class="form-group">

                                       <select class="form-control" id="city" name="city" required>
                                           <option value="">Chọn Thành Phố *</option>
                                           <!-- Các tùy chọn khác -->
                                       </select>
                                       <select class="form-control" id="district" name="district" required>
                                           <option value="">Chọn Quận huyện *</option>
                                           <!-- Các tùy chọn khác -->
                                       </select>
                                       <select class="form-control" id="ward" name="ward" required>
                                           <option value="">Phường/Xã *</option>
                                           <!-- Các tùy chọn khác -->
                                       </select>
                                   </div>

                                    <div class="mb-3">
                                        <input type="text" class="form-control" name="street"
                                            placeholder="Số nhà, tên đường...*" required>
                                    </div>

                                    <div class="mb-3">
                                        <input type="text" class="form-control" name="note"
                                            placeholder="Ghi chú thêm (nếu có)...">
                                    </div>

                                    <div class="form-check mb-3">
                                        <input class="form-check-input" type="checkbox" id="otherReceiver"
                                            onchange="toggleReceiverInfo()">
                                        <label class="form-check-label" for="otherReceiver">
                                            Gọi người khác nhận hàng (nếu có)
                                        </label>
                                    </div>

                                    <div id="receiverInfo" class="mb-3" style="display: none;">
                                        <div class="mb-3">
                                            <div class="form-check form-check-inline">
                                                <input class="form-check-input" type="radio" name="receiverGender"
                                                    id="maleReceiver" value="Anh">
                                                <label class="form-check-label" for="maleReceiver">Anh</label>
                                            </div>
                                            <div class="form-check form-check-inline">
                                                <input class="form-check-input" type="radio" name="receiverGender"
                                                    id="femaleReceiver" value="Chị">
                                                <label class="form-check-label" for="femaleReceiver">Chị</label>
                                            </div>
                                        </div>

                                        <div class="row g-3">
                                            <div class="col-md-6">
                                                <input type="text" class="form-control" name="receiverName"
                                                    placeholder="Họ và Tên người nhận *">
                                            </div>
                                            <div class="col-md-6">
                                                <input type="text" name="receiverPhone" class="form-control"
                                                    placeholder="Số điện thoại người nhận * ">
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div class="payment-container my-4 rounded-5 bg-white shadow px-4 py-4 " style="border-radius: 1rem; min-width: 350px;">
                                <div class="card-header bg-dark border-0 mb-3">
                                    <h4 class="font-weight-semi-bold m-0 text-center" style="color: whitesmoke">Phương Thức Thanh Toán</h4>
                                </div>
                                <div class="payment-options d-flex flex-row" >
                                    <button type="button" class="payment-button" style="margin: 5px; font-size: small; border-radius: 0.5rem" data-value="cod" onclick="selectPaymentMethod('cod')">
                                        <img class="img-fluid" src="${pageContext.request.contextPath}/template/web/img/COD.png" style="width:100%; height: auto; max-height: 70%" alt="Cod">
                                        Thanh toán khi giao hàng
                                    </button>

                                    <button type="button" class="payment-button" style="margin: 5px; font-size: small; border-radius: 0.5rem" data-value="bank-transfer" onclick="selectPaymentMethod('bank-transfer')">
                                        <img class="img-fluid" src="${pageContext.request.contextPath}/template/web/img/BankTranfer.png" style="width:100%; height: auto; max-height: 70%" alt="BankTranfer">
                                        Chuyển khoản ngân hàng
                                    </button>

                                    <button type="button" class="payment-button" style="margin: 5px; font-size: small; border-radius: 0.5rem" data-value="VNPAY" onclick="selectPaymentMethod('VNPAY')">
                                        <img class="img-fluid" src="${pageContext.request.contextPath}/template/web/img/VNPay.png" style="width:100%; height: auto; max-height: 70%" alt="VNPay">
                                        Thanh toán VNPAY
                                    </button>

                                    <button type="button" class="payment-button" style="margin: 5px; font-size: small; border-radius: 0.5rem" data-value="PayPal" onclick="selectPaymentMethod('PayPal')">
                                        <img class="img-fluid" src="${pageContext.request.contextPath}/template/web/img/Paypal-Logo-PNG.png" style="width:100%; height: auto;max-height: 70%" alt="PayPal">
                                        Thanh toán PayPal
                                    </button>
                                </div>
                                <div id="bank-popup" class="modal">
                                    <div class="modal-content">
                                        <span class="close" onclick="closeBankPopup()"><i class="fa-solid fa-minus"></i></span>
                                        <p><strong>Nội Dung Chuyển Khoản:</strong> "SDT_Tên người dùng"</p>
                                        <div class="qr-code">
                                            <img src="<c:url value='/template/web/img/QRCODE.png'/>" alt="QR Code">
                                        </div>
                                    </div>
                                </div>
                                <div id="errorMessage" style="color:red; display:none;">Phương thức thanh toán này không áp dụng voucher</div>
                                <input type="hidden" id="selectedPaymentMethod" name="paymentMethod">
                                <form action="<c:url value='/authorize_payment'/>" method="post" id="paymentForm">
                                    <input type="hidden" name="subtotal" value="${subtotal}">
                                    <input type="hidden" name="total" value="${subtotal}">
                                    <input type="hidden" id="voucher" value="${voucher.discount}">

                                </form>

                                <script>
                                    function selectPaymentMethod(method) {
                                        document.getElementById('selectedPaymentMethod').value = method;

                                        // Keep the existing updateAction() function call
                                        updateAction();

                                        // Show bank info if "bank-transfer" is selected, hide otherwise
                                        document.getElementById('bank-info').style.display = (method === 'bank-transfer') ? 'block' : 'none';

                                        // Highlight selected button
                                        document.querySelectorAll('.payment-button').forEach(button => {
                                            button.classList.remove('selected');
                                        });
                                        document.querySelector(`[data-value="${method}"]`).classList.add('selected');
                                    }
                                </script>

                                <style>
                                    .payment-button {
                                        display: block;
                                        width: 100%;
                                        padding: 10px;
                                        margin: 5px 0;
                                        background-color: #ffffff;
                                        border: 2px solid #dfdfdf;
                                        cursor: pointer;
                                        text-align: center;
                                        font-size: 16px;
                                    }

                                    .payment-button.selected {
                                        background-color: #ffffff;
                                        border: 2px solid #020101;
                                    }
                                </style>
                            </div>
                            <div class="card border-dark">
                                <div class="card-header bg-dark border-0">
                                    <h4 class="font-weight-semi-bold m-0 text-center" style="color: whitesmoke; font-family: Arial">Đơn Hàng</h4>
                                </div>
                                <div class="card-body">
                                    <h5 class="font-weight-medium mb-3 text-center" style="font-family: Arial">Sản Phẩm</h5>
                                    <c:forEach var="item" items="${cartItems}" varStatus="status">
                                        <div class="cart-item">
                                            <div class="cart-item-content">

                                                <img src="<c:url value='/template/web/img/products/${item.productImgUrl}'/>"
                                                     alt="Product Image" class="product-image" style="object-fit: cover">
                                                <div class="product-details">
                                                    <p class="product-name">${item.productName}</p>
                                                    <p class="product-price">
                                                        <span id="price" class="current-price">${item.productPrice}</span>

                                                    </p>
                                                </div>
                                                <div class="quantity-control">
                                                    <label class="quantity-label">x ${item.quantity}</label>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>

                                    <hr class="mt-0">
                                    <div class="d-flex justify-content-between mb-3 pt-1">
                                        <h6 class="font-weight-medium" style="font-family: Arial">Tổng Giá Trị</h6>
                                        <h6 id="price"  class="font-weight-medium">${subtotal}</h6>
                                        <input type="hidden" name="subtotal" value="${subtotal}">
                                    </div>
                                    <c:if test="${not empty voucher}">
                                        <div class="d-flex justify-content-between">
                                            <h6 class="font-weight-medium" style="font-family: Arial">Giảm Giá</h6>
                                            <h6 id="price"  class="font-weight-medium">-${voucher.discount}</h6>
                                            <input type="hidden" name="voucherCode" value="${voucher.code}">
                                            <input type="hidden" name="discount" value="${voucher.discount}">
                                        </div>
                                    </c:if>
                                </div>
                                <div class="card-footer border-secondary bg-transparent">
                                    <div class="d-flex justify-content-between mt-2">
                                        <h5 class="font-weight-bold" style="font-family: Arial">Thành Tiền</h5>
                                        <h5  id="price" class="font-weight-bold" style="color: green">${totalPrice}</h5>
                                        <input type="hidden" name="total" value="${totalPrice}">
                                    </div>
                                </div>
                            </div>
                            <div class="complete-order my-5"style="width: 100%">
                                <button id="btnCompleteOrder" type="submit" onclick ="handleOnClick()" style="width: 100%; background-color: rgb(234,189,43);" ><i style="color: black; font-style: normal; font-size: 20px">Hoàn Tất Đơn Hàng</i></button>
                            </div>
                        </div>
<%--                        <div class="col-lg-5">--%>
<%--                            <div class="card border-dark">--%>
<%--                                <div class="card-header bg-dark border-0">--%>
<%--                                    <h4 class="font-weight-semi-bold m-0 text-center" style="color: whitesmoke; font-family: Arial">Đơn Hàng</h4>--%>
<%--                                </div>--%>
<%--                                <div class="card-body">--%>
<%--                                    <h5 class="font-weight-medium mb-3 text-center" style="font-family: Arial">Sản Phẩm</h5>--%>
<%--                                    <c:forEach var="item" items="${cartItems}" varStatus="status">--%>
<%--                                        <div class="cart-item">--%>
<%--                                            <div class="cart-item-content">--%>

<%--                                                <img src="https://www.watchstore.vn/images/products/2024/06/14/small/mtp-vt01l-1budf_1718332985.webp"--%>
<%--                                                    alt="Product Image" class="product-image">--%>
<%--                                                <div class="product-details">--%>
<%--                                                    <p class="product-name">${item.productName}</p>--%>
<%--                                                    <p class="product-price">--%>
<%--                                                        <span id="price" class="current-price">${item.productPrice}</span>--%>

<%--                                                    </p>--%>
<%--                                                </div>--%>
<%--                                                <div class="quantity-control">--%>
<%--                                                    <label class="quantity-label">x ${item.quantity}</label>--%>
<%--                                                </div>--%>
<%--                                            </div>--%>
<%--                                        </div>--%>
<%--                                    </c:forEach>--%>

<%--                                    <hr class="mt-0">--%>
<%--                                    <div class="d-flex justify-content-between mb-3 pt-1">--%>
<%--                                        <h6 class="font-weight-medium" style="font-family: Arial">Tổng Giá Trị</h6>--%>
<%--                                        <h6 id ="price" class="font-weight-medium">${subtotal}</h6>--%>
<%--                                    <input type="hidden" name="subtotal" value="${subtotal}">--%>
<%--                                    </div>--%>
<%--                                    <c:if test="${not empty voucher}">--%>
<%--                                        <div class="d-flex justify-content-between">--%>
<%--                                            <h6 class="font-weight-medium" style="font-family: Arial">Giảm Giá</h6>--%>
<%--                                            <h6 id ="price" class="font-weight-medium">-${voucher.discount}</h6>--%>
<%--                                             <input type="hidden" name="voucherCode" value="${voucher.code}">--%>
<%--                                        </div>--%>
<%--                                    </c:if>--%>
<%--                                </div>--%>
<%--                                <div class="card-footer border-secondary bg-transparent">--%>
<%--                                    <div class="d-flex justify-content-between mt-2">--%>
<%--                                        <h5 class="font-weight-bold" style="font-family: Arial">Thành Tiền</h5>--%>
<%--                                        <h5 id ="price" class="font-weight-bold" style="color: green">${totalPrice}</h5>--%>
<%--                                        <input type="hidden" name="total" value="${totalPrice}">--%>
<%--                                    </div>--%>
<%--                                </div>--%>
<%--                            </div>--%>
<%--                            <div class="payment-methods my-4 mx-auto border-dark">--%>
<%--                                <div class="card-header bg-dark border-0 mb-3">--%>
<%--                                    <h4 class="font-weight-semi-bold m-0 text-center" style="color: whitesmoke">Hình Thức Thanh Toán</h4>--%>
<%--                                </div>--%>
<%--                                <div class="payment-option">--%>
<%--                                    <input type="radio" name="paymentMethod" id="cod" value="cod"--%>
<%--                                        onchange="updateAction()" checked>--%>
<%--                                    <label for="cod">Thanh toán khi giao hàng (COD)</label>--%>
<%--                                </div>--%>

<%--                                <div class="payment-option">--%>
<%--                                    <input type="radio" name="paymentMethod" id="bank-transfer" value="bank-transfer"--%>
<%--                                        onchange="updateAction()">--%>
<%--                                    <label for="bank-transfer">Chuyển khoản qua ngân hàng</label>--%>
<%--                                </div>--%>

<%--                                <!-- Thông tin tài khoản ngân hàng -->--%>
<%--                               <div id="bank-info" style="display: none;">--%>
<%--                                   <h4>Thông tin tài khoản ngân hàng:</h4>--%>
<%--                                   <p>Ngân hàng: ABC Bank</p>--%>
<%--                                   <p>Số tài khoản: 123456789</p>--%>
<%--                                   <p>Chủ tài khoản: Nguyễn Văn A</p>--%>

<%--                               </div>--%>
<%--                                 <div class="payment-option">--%>
<%--                                    <input type="radio" name="paymentMethod" id="VNPAY" value="VNPAY"--%>
<%--                                        onchange="updateAction()">--%>
<%--                                    <label for="VNPAY">Thanh toán VNPAY</label>--%>
<%--                                </div>--%>
<%--                                <form action="<c:url value='/authorize_payment'/>" method="post" id="paymentForm">--%>
<%--                                    <input type="hidden" name="subtotal" value="${subtotal}">--%>
<%--                                    <input type="hidden" name="total" value="${subtotal}">--%>
<%--                                    <input type="hidden" id="voucher" value="${voucher.discount}">--%>
<%--                                    <!-- Trường này lưu trạng thái voucher -->--%>
<%--                                    <div class="paypal-holder mb-4" style="width: 100%">--%>
<%--                                        <button class="paypal-button" type="button" onclick="validateForm()" style="width: 100%;background-color: rgba(44,229,250,0.8)">--%>
<%--                                            <span class="paypal-logo"></span>--%>
<%--                                            <span class="paypal-text">Pay with PayPal</span>--%>
<%--                                        </button>--%>
<%--                                        <div id="errorMessage" style="color:red; display:none;">Phương thức thanh toán này không áp dụng voucher</div>--%>
<%--                                    </div>--%>
<%--                                </form>--%>

<%--                                <div class="complete-order"style="width: 100%">--%>
<%--                                    <button type="submit" style="width: 100%; background-color: rgb(234,189,43);" onclick="completeOrder()"><i style="color: black; font-style: normal">Hoàn tất đơn hàng</i></button>--%>
<%--                                </div>--%>

<%--                            </div>--%>
<%--                        </div>--%>
                    </div>
                </form>
            </div>
            <!-- Checkout End -->
            <script>
                var citis = document.getElementById("city");
                var districts = document.getElementById("district");
                var wards = document.getElementById("ward");
                var Parameter = {
                    url: "https://raw.githubusercontent.com/kenzouno1/DiaGioiHanhChinhVN/master/data.json",
                    method: "GET",
                    responseType: "application/json",
                };
                var promise = axios(Parameter);
                promise.then(function (result) {
                    renderCity(result.data);
                });

                function renderCity(data) {
                    for (const x of data) {
                        citis.options[citis.options.length] = new Option(x.Name, x.Name);
                    }
                    citis.onchange = function () {
                        district.length = 1;
                        ward.length = 1;
                        if (this.value != "") {
                            const result = data.filter(n => n.Name === this.value);

                            for (const k of result[0].Districts) {
                                district.options[district.options.length] = new Option(k.Name, k.Name);
                            }
                        }
                    };
                    district.onchange = function () {
                        ward.length = 1;
                        const dataCity = data.filter((n) => n.Name === citis.value);
                        if (this.value != "") {
                            const dataWards = dataCity[0].Districts.filter(n => n.Name === this.value)[0].Wards;

                            for (const w of dataWards) {
                                wards.options[wards.options.length] = new Option(w.Name, w.Name);
                            }
                        }
                    };
                }
                function toggleReceiverInfo() {
                    var receiverInfo = document.getElementById("receiverInfo");
                    var checkbox = document.getElementById("otherReceiver");
                    if (checkbox.checked) {
                        receiverInfo.style.display = "Flex";
                    } else {
                        receiverInfo.style.display = "none";
                    }
                }
                function validateForm() {
                    var voucherExists = document.getElementById("voucher").value;
                    var errorMessage = document.getElementById("errorMessage");

                    if (voucherExists !== null && voucherExists !== "") {
                        // Nếu voucher tồn tại, hiển thị thông báo lỗi và không submit form
                        errorMessage.style.display = "block";
                    } else {
                        // Nếu không có voucher, submit form
                        document.getElementById("paymentForm").submit();
                        errorMessage.style.display = "none";
                    }
                }

                function updateAction() {
                    var form = document.getElementById("paymentForm1");
                    var selectedPaymentMethod = document.getElementById("selectedPaymentMethod").value;
                    var bankInfo = document.getElementById('bank-popup');
                    var formPayPal = document.getElementById("paymentForm");
                    var errorMessage = document.getElementById("errorMessage");

                    // Hiển thị hoặc ẩn thông tin tài khoản ngân hàng
                    if (selectedPaymentMethod === 'bank-transfer') {
                        bankInfo.style.display = 'Flex'; // Hiển thị thông tin tài khoản
                        form.action = "<c:url value='/checkout'/>";
                        form.method = "post";
                        errorMessage.style.display = "none";
                        document.querySelectorAll('.payment-button').forEach(button => {
                            button.classList.remove('selected');
                        });
                        document.querySelector(`[data-value="bank-transfer"]`).classList.add('selected');
                    } else {
                        bankInfo.style.display = 'none'; // Ẩn thông tin tài khoản
                        form.action = "<c:url value='/checkout'/>";
                        form.method = "post";
                        errorMessage.style.display = "none";
                        document.querySelectorAll('.payment-button').forEach(button => {
                            button.classList.remove('selected');
                        });
                        document.querySelector(`[data-value="bank-transfer"]`).classList.add('selected');
                    }


                    if (selectedPaymentMethod === 'PayPal') {

                        document.querySelectorAll('.payment-button').forEach(button => {
                            button.classList.remove('selected');
                        });
                        document.querySelector(`[data-value="PayPal"]`).classList.add('selected');
                    }

                    if (selectedPaymentMethod === 'VNPAY') {
                        form.action = "<c:url value='/payment/create-payment'/>";
                        form.method = "get";
                        errorMessage.style.display = "none";
                        document.querySelectorAll('.payment-button').forEach(button => {
                            button.classList.remove('selected');
                        });
                        document.querySelector(`[data-value="VNPAY"]`).classList.add('selected');
                    }
                    if (selectedPaymentMethod === 'cod') {
                        errorMessage.style.display = "none";
                        document.querySelectorAll('.payment-button').forEach(button => {
                            button.classList.remove('selected');
                        });
                        document.querySelector(`[data-value="cod"]`).classList.add('selected');
                    }
                }

                function closeBankPopup() {
                    document.getElementById('bank-popup').style.display = 'none';

                    // Highlight selected button
                    document.querySelectorAll('.payment-button').forEach(button => {
                        button.classList.remove('selected');
                    });
                    document.querySelector(`[data-value="bank-transfer"]`).classList.add('selected');
                }
                function handleOnClick(){
                    var selectedPaymentMethod = document.getElementById("selectedPaymentMethod").value;
                    var form = document.getElementById("paymentForm1");
                    if (selectedPaymentMethod === 'PayPal') {
                        // Bỏ validate required cho tất cả input và select
                        document.querySelectorAll("input[required], select[required]").forEach(element => {
                            element.removeAttribute("required");
                        });
                        document.getElementById("btnCompleteOrder").setAttribute("type", "button");

                        validateForm(); // Gọi hàm kiểm tra form nếu cần
                    }else{
                        document.getElementById("btnCompleteOrder").setAttribute("type", "submit");
                    }

                }
            </script>
            <script>
                    document.querySelectorAll('#price').forEach(element => {
                            let price = parseInt(element.innerText.replace("đ", ""), 10);
                            element.innerText = price.toLocaleString("vi-VN") + "đ";
                        });

            </script>
        </body>

        </html>