<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglib.jsp" %>

<head>
    <title>ChronoLux - Thêm sản phẩm</title>
    <meta charset="UTF-8">
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
</head>
<body>
<div class="container">
    <form:form method="post" action="${pageContext.request.contextPath}/admin/product/save"
               enctype="multipart/form-data" modelAttribute="product">
        <input type="hidden" name="page" value="${currentPage}">
        <div class="d-flex justify-content-between align-items-center my-4">
            <h1 class="h3 mb-1 mt-2 text-gray-800">Thêm sản phẩm</h1>
            <div>
                <a href="${pageContext.request.contextPath}/admin/products?page=${currentPage}" class="btn btn-warning">
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
        <div class="form-row">
            <!-- Thương hiệu -->
            <div class="form-group col-md-6">
                <label for="brandSelect">Thương hiệu *</label>
                <select id="brandSelect" class="custom-select form-control mb-3" name="brandId"
                        aria-label="Select brand" required>
                    <c:choose>
                        <c:when test="${empty brands}">
                            <option selected>Chưa có dữ liệu</option>
                        </c:when>
                        <c:otherwise>
                            <option value="" disabled selected>Chọn thương hiệu</option>
                            <c:forEach var="brand" items="${brands}">
                                <option value="${brand.id}">${brand.name}</option>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </select>
            </div>

            <!-- Dòng đồng hồ -->
            <div class="form-group col-md-6">
                <label for="productLineSelect">Dòng sản phẩm *</label>
                <select class="custom-select form-control" id="productLineSelect" name="productLineId"
                        aria-label="Select product line" required>
                    <option value="" disabled selected>Chọn dòng sản phẩm</option>
                </select>
                <div id="product-line-error" class="text-danger" style="display:none;">Dòng sản phẩm trống. Bạn không
                    thể thêm sản phẩm!
                </div>
            </div>
        </div>

        <div class="form-row">
            <div class="form-group col-md-6">
                <label for="name">Tên sản phẩm *</label>
                <form:input path="name" type="text" class="form-control" id="name" required="required"/>
            </div>
            <div class="form-group col-md-6">
                <label for="productLineSelect">Đối tượng *</label>
                <select class="custom-select form-control" id="genderSelect" name="gender"
                        aria-label="Select product line" required>
                    <option value="" disabled selected>Chọn giới tính</option>
                    <option value="Nam">Nam</option>
                    <option value="Nữ">Nữ</option>
                </select>
            </div>
            <div class="form-group col-md-6">
                <label for="price">Giá sản phẩm *</label>
                <form:input path="price" type="text" class="form-control" id="price" required="required"
                            onkeyup="validatePrice(this)"/>
                <div id="price-error" class="text-danger" style="display:none;">Vui lòng nhập số tiền hợp lệ!</div>
            </div>
            <div class="form-group col-md-6">
                <label for="stock">Số lượng hàng *</label>
                <form:input path="stock" type="text" class="form-control" id="stock" required="required"
                            onkeyup="validateStock(this)"/>
                <div id="stock-error" class="text-danger" style="display:none;">Vui lòng nhập số lượng hàng hợp lệ!
                </div>
            </div>
            <div class="form-group col-md-6">
                <label for="thickness">Độ dày (mm) *</label>
                <form:input path="thickness" type="text" class="form-control" id="thickness" required="required"
                            onkeyup="validateThickness(this)"/>
                <div id="thickness-error" class="text-danger" style="display:none;">Vui lòng nhập chỉ số hợp lệ!</div>
            </div>
            <div class="form-group col-md-6">
                <label for="faceSize">Size mặt *</label>
                <form:input path="faceSize" type="text" class="form-control" id="faceSize" required="required"/>
            </div>
            <div class="form-group col-md-6">
                <label for="strapMaterial">Chất liệu dây *</label>
                <form:input path="strapMaterial" type="text" class="form-control" id="strapMaterial"
                            required="required"/>
            </div>
            <div class="form-group col-md-6">
                <label for="glassMaterial">Chất liệu mặt kính *</label>
                <form:input path="glassMaterial" type="text" class="form-control" id="glassMaterial"
                            required="required"/>
            </div>
            <div class="form-group col-md-6">
                <label for="watchType">Loại máy *</label>
                <form:input path="watchType" type="text" class="form-control" id="watchType" required="required"/>
            </div>

            <div class="form-group col-md-6">
                <label for="waterResistant">Kháng nước (m) *</label>
                <form:input path="waterResistant" type="text" class="form-control" id="waterResistant"
                            required="required" onkeyup="validateRes(this)"/>
                <div id="res-error" class="text-danger" style="display:none;">Vui lòng nhập chỉ số hợp lệ!</div>
            </div>

            <div class="form-group col-md-6">
                <label for="img">Mô tả sản phẩm *</label>
                <form:textarea path="description" class="form-control" id="description" rows="5" required=""></form:textarea>
            </div>
            <div class="form-group col-md-6">
                <label for="img">Tải ảnh sản phẩm</label>
                <div class="custom-file">
                    <input type="file" class="custom-file-input" id="img" name="img" accept="image/*" required
                           onchange="previewImage(event, 'imgPreview')">
                    <label class="custom-file-label" for="img">Chọn ảnh</label>
                    <div class="d-flex justify-content-center mt-3"> <!-- Căn giữa -->
                        <img id="imgPreview" src="" alt="Img Preview" class="img-fluid border border-dark p-3 bg-light"
                             style="max-width: 300px; display: none;"/>
                    </div>
                </div>
            </div>

        </div>
        <%--<!-- Phần xem trước ảnh -->
            <div class="form-group">

            </div>--%>
        <div class="form-group col-md-6">
            <label class="text-danger">(*) Thông tin bắt buộc </label>
        </div>
    </form:form>
</div>

<!-- Img review-->
<script>
    function previewImage(event, previewId) {
        var input = event.target;
        var reader = new FileReader();

        reader.onload = function () {
            var dataURL = reader.result;
            var imagePreview = document.getElementById(previewId);
            imagePreview.src = dataURL;
            imagePreview.style.display = 'block'; // Hiển thị ảnh sau khi tải
        };

        reader.readAsDataURL(input.files[0]);
    }
</script>

<%--automatic change prodline--%>
<script>
    $(document).ready(function () {
        // Khi người dùng thay đổi lựa chọn brand
        $('#brandSelect').change(function () {
            var brandId = $(this).val();

            // Gửi yêu cầu AJAX để lấy danh sách product lines dựa trên brandId
            $.ajax({
                url: '${pageContext.request.contextPath}/admin/product-line/getProductLines',  // URL xử lý yêu cầu
                type: 'GET',
                data: {brandId: brandId},
                success: function (response) {
                    // Làm trống thẻ select productline trước khi cập nhật
                    $('#productLineSelect').empty();
                    // Kiểm tra nếu không có dữ liệu trả về
                    if (response.length === 0) {
                        $('#productLineSelect').append('<option value="" disabled>Chưa có dữ liệu</option>');
                        $('#product-line-error').show();
                    } else {
                        $.each(response, function (key, value) {
                            $('#productLineSelect').append('<option value="' + value.id + '">' + value.name + '</option>');
                        });
                        $('#product-line-error').hide();
                    }
                }
            });
        });
        $('#brandSelect').trigger('change');
    });
</script>

<!-- Validate price -->
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
        var priceInput = document.getElementById("price");
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
    function validateStock(input) {
        const stockValue = input.value;
        const errorElement = document.getElementById("stock-error");

        // Kiểm tra xem stockValue có phải là số tự nhiên lớn hơn hoặc bằng 0
        if (!/^\d+$/.test(stockValue) || parseInt(stockValue) < 0) {
            errorElement.style.display = "block";
            input.classList.add("is-invalid");
        } else {
            errorElement.style.display = "none";
            input.classList.remove("is-invalid");
        }
    }

    function validateRes(input) {
        const res_val = input.value;
        const errorElement = document.getElementById("res-error");
        if (!/^\d+$/.test(res_val) || parseInt(res_val) < 0) {
            errorElement.style.display = "block";
            input.classList.add("is-invalid");
        } else {
            errorElement.style.display = "none";
            input.classList.remove("is-invalid");
        }
    }

    function validateThickness(input) {
        const thick_val = input.value;
        const errorElement = document.getElementById("thickness-error");
        if (!/^\d+$/.test(thick_val) || parseInt(thick_val) < 0) {
            errorElement.style.display = "block";
            input.classList.add("is-invalid");
        } else {
            errorElement.style.display = "none";
            input.classList.remove("is-invalid");
        }
    }

    function validateForm() {
        var stockInput = document.getElementById("stock");
        var resInput = document.getElementById("waterResistant");
        var thickInput = document.getElementById("thickness");

        var stockValue = stockInput.value;
        var resValue = resInput.value;
        var thickValue = thickInput.value;

        var isValid = true;

        // Kiểm tra số lượng tồn kho
        if (!/^\d+$/.test(stockValue) || parseInt(stockValue) < 0) {
            stockInput.classList.add("is-invalid");
            document.getElementById("stock-error").style.display = "block";
            isValid = false;
        } else {
            stockInput.classList.remove("is-invalid");
            document.getElementById("stock-error").style.display = "none";
        }

        // Kiểm tra chỉ số chống nước
        if (!/^\d+$/.test(resValue) || parseInt(resValue) < 0) {
            resInput.classList.add("is-invalid");
            document.getElementById("res-error").style.display = "block";
            isValid = false;
        } else {
            resInput.classList.remove("is-invalid");
            document.getElementById("res-error").style.display = "none";
        }

        // Kiểm tra độ dày
        if (!/^\d+$/.test(thickValue) || parseInt(thickValue) < 0) {
            thickInput.classList.add("is-invalid");
            document.getElementById("thickness-error").style.display = "block";
            isValid = false;
        } else {
            thickInput.classList.remove("is-invalid");
            document.getElementById("thickness-error").style.display = "none";
        }

        return isValid;
    }

    // Đính kèm sự kiện onsubmit
    document.querySelector("form").onsubmit = validateForm;
</script>

<!-- Validate product line -->
<%--<script>
    // Kiểm tra xem có dữ liệu trong select hay không
    function validateProductLine() {
        var select = document.getElementById("productLineSelect");
        var selectedValue = select.value;

        // Nếu không có giá trị nào được chọn (dòng sản phẩm trống)
        if (selectedValue === "" || select.options.length <= 1) {  // Kiểm tra nếu không có option nào ngoài default
            select.classList.add("is-invalid");
            document.getElementById("product-line-error").style.display = "block";
        } else {
            select.classList.remove("is-invalid");
            document.getElementById("product-line-error").style.display = "none";
        }
    }

    // Gọi hàm validate khi người dùng chọn dòng sản phẩm hoặc khi form submit
    document.getElementById("productLineSelect").onchange = validateProductLine;

    function validateForm() {
        // Gọi kiểm tra cho các trường khác nếu cần
        validateProductLine();
        // Các điều kiện kiểm tra khác cho form tại đây (nếu cần)

        var isProductLineValid = document.getElementById("product-line-error").style.display === "none";
        return isProductLineValid;
    }

    // Gán sự kiện validate khi submit form
    document.querySelector("form").onsubmit = function(event) {
        if (!validateForm()) {
            event.preventDefault(); // Ngăn form gửi nếu có lỗi
        }
    };
</script>--%>
</body>