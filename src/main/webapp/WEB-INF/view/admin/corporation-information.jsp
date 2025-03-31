<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <title>ChronoLux - Thông tin doanh nghiệp</title>
    <!-- Custom fonts for this template-->
    <link href="<c:url value='/template/admin/vendor/fontawesome-free/css/all.min.css'/>" rel="stylesheet" type="text/css">
    <link
            href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
            rel="stylesheet">
    <link href="<c:url value='/template/admin/css/sb-admin-2.min.css'/>" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
</head>
<body>
<div class="container mt-4">
    <form:form method="post" action="${pageContext.request.contextPath}/admin/corporation/save" modelAttribute="corporation" enctype="multipart/form-data">
        <div class="d-flex justify-content-between align-items-center my-1">
            <h1 class="h3 mb-1 text-gray-800">Doanh nghiệp của bạn</h1>
            <div>
                <button type="submit" class="btn btn-success mr-2">
                    <i class="fa-solid fa-check mr-1"></i>
                    Lưu thông tin
                </button>
            </div>
        </div>
        <hr/>
        <c:if test="${not empty corporation.id}">
            <form:hidden path="id" />
        </c:if>
        <div class="form-group">
            <label for="email">Email</label>
            <form:input path="email" type="email" class="form-control" id="email" required=""/>
            <div id="email-error" class="invalid-feedback" style="display: none; color: red;">
                Email không hợp lệ. Vui lòng điền email hợp lệ.
            </div>
        </div>
        <div class="form-group">
            <label for="phone">Số điện thoại </label>
            <form:input path="phone" type="text" class="form-control" id="phone" required=""/>
            <div id="phone-error" class="invalid-feedback" style="display: none; color: red;">
                Số điện thoại không hợp lệ. Vui lòng nhập số điện thoại hợp lệ.
            </div>
        </div>
        <div class="form-group">
            <label for="address">Địa chỉ</label>
            <form:input path="address" type="text" class="form-control" id="address" required=""/>
        </div>
        <div class="form-group">
            <label for="about">Thông tin về doanh nghiệp</label>
            <form:textarea path="about" class="form-control" id="about" rows="5" required=""></form:textarea>
        </div>
        <!-- Trường upload ảnh -->
        <div class="form-group">
            <label for="img_file">Tải lên ảnh</label>
            <div class="custom-file">
                <input type="file" class="custom-file-input" id="img_file" name="img_file" accept="image/*" onchange="previewImage(event)">
                <label class="custom-file-label" for="img_file">
                        ${corporation.img != null ? corporation.img : 'Chọn logo doanh nghiệp'}
                </label>
            </div>
        </div>
        <!-- Phần xem trước ảnh -->
        <div class="form-group">
            <img id="imagePreview" src="<c:url value='/template/web/img/corporation/${corporation.img}'/>" alt="" class="img-fluid" style="max-width: 300px;" />
        </div>
    </form:form>
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
    document.getElementById("phone").addEventListener("input", function() {
        var phoneInput = document.getElementById("phone");
        var phoneError = document.getElementById("phone-error");
        var phonePattern = /^0\d{9}$/;
        if (phonePattern.test(phoneInput.value)) {
            phoneInput.classList.remove("is-invalid");
            phoneError.style.display = "none";
        } else {
            phoneInput.classList.add("is-invalid");
            phoneError.style.display = "block";
        }
    });
</script>
<script>
    document.getElementById("phone").addEventListener("input", function() {
        var phoneInput = document.getElementById("phone");
        var phoneError = document.getElementById("phone-error");
        var phonePattern = /^0\d{9}$/;
        if (phonePattern.test(phoneInput.value)) {
            phoneInput.classList.remove("is-invalid");
            phoneError.style.display = "none";
        } else {
            phoneInput.classList.add("is-invalid");
            phoneError.style.display = "block";
        }
    });
</script>
<script>
    document.getElementById("email").addEventListener("input", function() {
        var emailInput = document.getElementById("email");
        var emailError = document.getElementById("email-error");

        // Biểu thức chính quy kiểm tra email hợp lệ (RFC 5321/5322)
        var emailPattern = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        if (emailPattern.test(emailInput.value)) {
            emailInput.classList.remove("is-invalid");
            emailError.style.display = "none";
        } else {
            emailInput.classList.add("is-invalid");
            emailError.style.display = "block";
        }
    });
</script>

<script>
    function previewImage(event) {
        var input = event.target;
        var reader = new FileReader();
        reader.onload = function(){
            var dataURL = reader.result;
            var imagePreview = document.getElementById('imagePreview');
            imagePreview.src = dataURL;
            imagePreview.style.display = 'block'; // Show image after loading
        };
        reader.readAsDataURL(input.files[0]);
    }
</script>

</body>