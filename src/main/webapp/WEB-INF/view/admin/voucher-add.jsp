<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglib.jsp" %>

<head>
    <title>ChronoLux - Thêm voucher</title>
    <meta charset="UTF-8">
    <!-- Custom fonts for this template-->
    <link href="<c:url value='/template/admin/vendor/fontawesome-free/css/all.min.css'/>" rel="stylesheet" type="text/css">
    <link
            href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
            rel="stylesheet">
    <link href="<c:url value='/template/admin/css/s.css'/>" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="<c:url value='/template/admin/css/sb-admin-2.min.css'/>">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">

</head>
<body>
<div class="container">
    <form:form method="post" action="${pageContext.request.contextPath}/admin/voucher/save" enctype="multipart/form-data" modelAttribute="voucher">
        <div class="d-flex justify-content-between align-items-center my-4">
            <h1 class="h3 mb-1 mt-2 text-gray-800">Thêm mã giảm giá</h1>
            <div>
                <a href="${pageContext.request.contextPath}/admin/vouchers" class="btn btn-warning">
                    <i class="fa fa-reply mr-1" aria-hidden="true"></i>
                    Quay lại
                </a>
                <button type="submit" class="btn btn-success ml-2">
                    <i class="fa fa-check mr-1" aria-hidden="true"></i>
                    Lưu
                </button>
            </div>
        </div>
        <hr/>
        <input type="hidden" name="page" value="${currentAdminPage}">
        <div class="form-group">
            <label for="code">Mã voucher *</label>
            <form:input path="code" type="text" class="form-control" id="code" required="required"/>
        </div>
        <div class="form-group">
            <label for="discount">Tiền giảm (VND) *</label>
            <form:input path="discount" type="text" class="form-control" id="discount" required="required" onkeyup="validatePrice(this)"/>
            <div id="price-error" class="text-danger" style="display:none;">Vui lòng nhập số tiền hợp lệ!</div>
        </div>

        <div class="form-group">
            <label for="beginDay">Ngày bắt đầu *</label>
            <div class="input-group date" id="beginDayContainer" data-target-input="nearest">
                <form:input path="beginDay" type="text" id="beginDay" class="form-control" required="true"/>
                <div class="input-group-append" data-target="#beginDay" data-toggle="datetimepicker">
                    <span class="input-group-text"><i class="fa fa-calendar" aria-hidden="true"></i></span>
                </div>
            </div>
        </div>

        <div class="form-group">
            <label for="endDay">Ngày kết thúc *</label>
            <div class="input-group date" id="endDayContainer" data-target-input="nearest">
                <form:input path="endDay" type="text" id="endDay" class="form-control" required="true" />
                <div class="input-group-append" data-target="#endDay" data-toggle="datetimepicker">
                    <span class="input-group-text"><i class="fa fa-calendar" aria-hidden="true"></i></span>
                </div>
            </div>
        </div>
    </form:form>
</div>
<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
<script>
    function validatePrice(input) {
        var value = input.value;

        // Biểu thức chính quy kiểm tra số dương (bao gồm số thực và số nguyên)
        var regex = /^(?!0\d)\d*(\.\d{0,2})?$/;

        // Kiểm tra nếu giá trị có phải là số hợp lệ và không âm
        if (!regex.test(value) || parseFloat(value) < 0 || isNaN(value)) {
            // Nếu không hợp lệ, thêm lớp hiển thị lỗi và hiện thông báo lỗi
            input.classList.add("is-invalid");
            document.getElementById("price-error").style.display = "block";
        } else {
            // Nếu hợp lệ, bỏ lớp lỗi và ẩn thông báo lỗi
            input.classList.remove("is-invalid");
            document.getElementById("price-error").style.display = "none";
        }
    }

    // Hàm kiểm tra hợp lệ trước khi gửi form
    function validateForm() {
        var priceInput = document.getElementById("discount");
        var value = priceInput.value;
        var regex = /^(?!0\d)\d*(\.\d{0,2})?$/;

        if (!regex.test(value) || parseFloat(value) < 0 || isNaN(value)) {
            // Nếu không hợp lệ, hiển thị lỗi và ngăn gửi form
            priceInput.classList.add("is-invalid");
            document.getElementById("price-error").style.display = "block";
            return false;
        } else {
            // Nếu hợp lệ, bỏ lỗi và ẩn thông báo
            priceInput.classList.remove("is-invalid");
            document.getElementById("price-error").style.display = "none";
            return true;
        }
    }
    document.querySelector("form").onsubmit = validateForm;
</script>

<script>
    // Khởi tạo flatpickr cho beginDay và endDay
    const beginPicker = flatpickr("#beginDay", {
        onChange: function(selectedDates, dateStr, instance) {
            // Kiểm tra nếu endDay nhỏ hơn beginDay, cập nhật lại endDay
            const endDate = endPicker.selectedDates[0];
            if (endDate && selectedDates[0] > endDate) {
                endPicker.setDate(selectedDates[0]);
            }
            // Đặt giới hạn ngày nhỏ nhất cho endDay là beginDay
            endPicker.set("minDate", selectedDates[0]);
        }
    });

    const endPicker = flatpickr("#endDay", {
        onChange: function(selectedDates, dateStr, instance) {
            // Kiểm tra nếu beginDay lớn hơn endDay, cập nhật lại beginDay
            const beginDate = beginPicker.selectedDates[0];
            if (beginDate && selectedDates[0] < beginDate) {
                beginPicker.setDate(selectedDates[0]);
            }
            // Đặt giới hạn ngày lớn nhất cho beginDay là endDay
            beginPicker.set("maxDate", selectedDates[0]);
        }
    });
</script>
</body>