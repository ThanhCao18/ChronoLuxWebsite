<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

        .custom-table, .custom-table th, .custom-table td {
            border: 1px solid black !important; /* Làm đậm viền */
        }


    </style>
</head>

<body>
<div class=" custom-container">

    <div class="">
        <div class="">

        </div>
        <div class="">
            <div class="order-container">
                <div class="order-header">DANH SÁCH ĐƠN HÀNG MỚI NHẤT</div>

                <c:choose>
                    <c:when test="${not empty bill}">
                        <div class="table-responsive">
                            <table class="table table-bordered text-center align-middle custom-table" width="100%"
                                   cellspacing="0">

                                <thead class="table-secondary">
                                <tr>
                                    <th>Mã</th>
                                    <th>Thời gian đặt</th>
                                    <th>Khách hàng</th>
                                    <th>SĐT</th>
                                        <%--<th>Hình thức thanh toán</th>--%>
                                    <th>Trạng thái thanh toán</th>
                                    <th>Trạng thái giao hàng</th>
                                    <th>Tổng đơn hàng</th>
                                    <th>Chi tiết hóa đơn</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="ub" items="${bill}">
                                    <tr>
                                        <td>${ub.id}</td>
                                        <td>
                                            <fmt:formatDate value="${ub.createdDate}" pattern="dd/MM/yyyy HH:mm"/>
                                        </td>
                                        <td>${ub.displayName}</td>
                                        <td>${ub.phone}</td>
                                            <%--<td>${ub.paymentMethod}</td>--%>
                                        <td>
                                            <c:choose>
                                                <c:when test="${ub.status == 'Chưa thanh toán'}">
                                                        <span class="badge badge-warning"
                                                              style="font-size: 14px;">Chưa thanh toán</span>
                                                    <%--<button type="button" class="btn btn-xs btn-warning btn-sm" data-toggle="modal"
                                                            data-target="#confirmModal${ub.id}">
                                                        <i class="fa-solid fa-check mr-1"></i>
                                                        Xác nhận thanh toán
                                                    </button>--%>
                                                </c:when>
                                                <c:when test="${ub.status == 'Chờ hoàn tiền'}">
                                                    <span class="badge badge-warning" style="font-size: 14px;">Đang chờ hoàn tiền</span>
                                                </c:when>
                                                <c:when test="${ub.status == 'Đã thanh toán'}">
                                                    <span class="badge badge-success" style="font-size: 14px;">Đã thanh toán</span>
                                                </c:when>
                                                <c:when test="${ub.status == 'Đã hoàn tiền'}">
                                                    <span class="badge badge-info"
                                                          style="font-size: 14px;">Đã hoàn tiền</span>
                                                </c:when>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${ub.deliveryStatus == 'PENDING'}">
                                        <span class="badge badge-warning"
                                              style="font-size: 14px;">${ub.deliveryStatus.displayName}</span>
                                                </c:when>
                                                <c:when test="${ub.deliveryStatus == 'DELIVERED'}">
                                        <span class="badge badge-success"
                                              style="font-size: 14px;">${ub.deliveryStatus.displayName}</span>
                                                </c:when>
                                                <c:when test="${ub.deliveryStatus == 'CANCELLED'}">
                                        <span class="badge badge-danger"
                                              style="font-size: 14px;">${ub.deliveryStatus.displayName}</span>
                                                </c:when>
                                            </c:choose>
                                        </td>
                                        <td class="currency" id="price">${ub.total}</td>
                                        <td>
                                            <c:if test="${ub.deliveryStatus != 'DELIVERED' and ub.deliveryStatus != 'CANCELLED'}">
                                                <!-- Nút mở Modal -->
                                                <button type="button" class="btn btn-primary btn-sm mr-2"
                                                        data-toggle="modal" data-target="#confirmModal${ub.id}">
                                                    <i class="fa-solid fa-xmark mr-1"></i> Yêu cầu hủy đơn
                                                </button>
                                            </c:if>

                                            <a href="${pageContext.request.contextPath}/user-order/detail/${ub.id}"
                                               class="btn btn-secondary btn-sm mr-2"
                                            >
                                                <i class="fa-solid fa-eye mr-1"></i>
                                                Xem
                                            </a>
                                        </td>
                                    </tr>

                                    <!-- Modal -->
                                    <div class="modal fade" id="confirmModal${ub.id}" tabindex="-1" role="dialog"
                                         aria-labelledby="confirmModalLabel${ub.id}" aria-hidden="true">
                                        <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
                                            <div class="modal-content">

                                                <div class="modal-header">
                                                    <h5 class="modal-title" id="confirmModalLabel${ub.id}">Xác nhận hủy
                                                        đơn hàng</h5>
                                                    <button type="button" class="close" data-dismiss="modal"
                                                            aria-label="Close">
                                                        <span aria-hidden="true">&times;</span>
                                                    </button>
                                                </div>
                                                <form id="cancelForm${ub.id}"
                                                      action="<c:url value='/user-order/cancel/${ub.id}'/>"
                                                      method="post">
                                                    <div class="modal-body">
                                                        <p>Vui lòng chọn lý do hủy đơn hàng:</p>
                                                        <input type="hidden" name="orderId" value="${ub.id}">
                                                        <div class="form-check">
                                                            <input class="form-check-input" type="radio"
                                                                   name="cancelReason" id="reason1${ub.id}"
                                                                   value="Muốn thay đổi địa chỉ giao hàng">
                                                            <label class="form-check-label" for="reason1${ub.id}">Muốn
                                                                thay đổi địa chỉ giao hàng</label>
                                                        </div>
                                                        <div class="form-check">
                                                            <input class="form-check-input" type="radio"
                                                                   name="cancelReason" id="reason2${ub.id}"
                                                                   value="Muốn nhập/thay đổi mã Voucher">
                                                            <label class="form-check-label" for="reason2${ub.id}">Muốn
                                                                nhập/thay đổi mã Voucher</label>
                                                        </div>
                                                        <div class="form-check">
                                                            <input class="form-check-input" type="radio"
                                                                   name="cancelReason" id="reason3${ub.id}"
                                                                   value="Muốn thay đổi sản phẩm trong đơn hàng(size,màu sắc,số lượng,...)">
                                                            <label class="form-check-label" for="reason3${ub.id}">Muốn
                                                                thay đổi sản phẩm trong đơn hàng</label>
                                                        </div>
                                                        <div class="form-check">
                                                            <input class="form-check-input" type="radio"
                                                                   name="cancelReason" id="reason4${ub.id}"
                                                                   value="Tìm được giá rẻ hơn">
                                                            <label class="form-check-label" for="reason4${ub.id}">Tìm
                                                                được giá rẻ hơn ở chỗ khác</label>
                                                        </div>
                                                        <div class="form-check">
                                                            <input class="form-check-input" type="radio"
                                                                   name="cancelReason" id="reason5${ub.id}"
                                                                   value="Thời gian giao hàng lâu">
                                                            <label class="form-check-label" for="reason5${ub.id}">Thời
                                                                gian giao hàng lâu</label>
                                                        </div>
                                                        <div class="form-check">
                                                            <input class="form-check-input" type="radio" checked
                                                                   name="cancelReason" id="reason6${ub.id}"
                                                                   value="Đổi ý, không muốn mua nữa">
                                                            <label class="form-check-label" for="reason6${ub.id}">Đổi ý,
                                                                không muốn mua nữa</label>
                                                        </div>
                                                        <div class="form-check">
                                                            <input class="form-check-input" type="radio"
                                                                   name="cancelReason" id="reason7${ub.id}"
                                                                   value="Khác">
                                                            <label class="form-check-label"
                                                                   for="reason7${ub.id}">Khác</label>
                                                        </div>
                                                    </div>
                                                </form>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-secondary"
                                                            data-dismiss="modal">
                                                        <i class="fa-solid fa-reply mr-1"></i>
                                                        Trở lại
                                                    </button>
                                                    <button type="submit" class="btn btn-warning"
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
                    </c:when>
                    <c:otherwise>
                        <p style="padding: 15px; font-size: 16px; color: #888;">Bạn chưa có đơn hàng nào.</p>
                    </c:otherwise>
                </c:choose>

            </div>


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
    document.querySelectorAll('#price').forEach(element => {
        let price = parseFloat(element.innerText.replace("đ", "").trim()); // Chuyển từ chuỗi sang số
        element.innerText = price.toLocaleString("vi-VN", {style: "currency", currency: "VND"}); // Format tiền tệ VNĐ
    });
</script>


</body>

</html>