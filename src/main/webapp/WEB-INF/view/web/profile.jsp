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
                /* Tùy chỉnh container */
                .custom-file-container {
                    position: relative;
                    display: inline-block;
                    font-family: Arial, sans-serif;
                }

                /* Ẩn input file gốc */
                input[type="file"] {
                    display: none;
                }

                /* Tùy chỉnh nhãn (label) cho input file */
                .upload-label {
                    display: inline-block;
                    padding: 10px 90px;
                    margin-left: 5px;
                    background-color: #E5BE52;
                    color: white;
                    font-size: 14px;
                    font-weight: bold;
                    border: none;
                    border-radius: 5px;
                    cursor: pointer;
                    transition: background-color 0.3s ease;
                }

                /* Hiệu ứng hover */
                .upload-label:hover {
                    background-color: #C7972A;
                }

                /* Hiệu ứng focus (khi chọn bằng bàn phím) */
                .upload-label:focus {
                    outline: 2px solid #0056b3;
                    outline-offset: 2px;
                }


            </style>
        </head>

        <body>
            <div class="container rounded bg-white mt-5 mb-5">
                <form method="post" action="<c:url value='/user-profile'/>" enctype="multipart/form-data">
                <div class="row">
                    <div class="col-md-3 border-right">
                        <c:if test="${user.password != null}">
                            <div class="d-flex flex-column align-items-center text-center p-3 py-5">
                                <img class="rounded-circle mt-5" style="max-width: 150px; max-height: 150px; width: fit-content; height: fit-content;"
                                     id = "img_display"
                                     src="<c:url value='/template/web/img/user-logos/${user.imgUrl}'/>"><span
                                    class="font-weight-bold">${user.getFullName()}</span><span
                                    class="text-black-50">${user.getEmail()}</span><span> </span></div>
                            <div>
                                <div class="custom-file-container">
                                    <label for="img_chosen" class="upload-label">Chọn tệp</label>
                                    <input type="file" name="img" id="img_chosen" accept="image/*" onchange="displayImg(this)">
                                </div>

                            </div>
                        </c:if>
                        <c:if test="${user.password == null}">
                            <div class="d-flex flex-column align-items-center text-center p-3 py-5">
                                <img class="rounded-circle mt-5" width="150px" src="${user.imgUrl}"><span
                                    class="font-weight-bold">${user.getFullName()}</span><span
                                    class="text-black-50">${user.getEmail()}</span><span> </span></div>
                        </c:if>

                    </div>
                    <div class="col-md-9 border-right">
                        <div class="p-3 py-5">
                            <div class="d-flex justify-content-between align-items-center mb-3">
                                <h4 class="text-right">Thông Tin Tài Khoản</h4>
                            </div>

                                <div class="row mt-2">
                                    <c:if test="${user.password != null}">
                                        <input type="hidden" name="id" class="form-control" value="${user.id}">
                                    </c:if>

                                    <div class="col-md-12">
                                        <label class="labels">Họ Và Tên</label>
                                        <input type="text" name="fullName" class="form-control" placeholder="Họ và Tên"
                                               value="${user.getFullName()}" required>
                                    </div>
                                </div>
                                <div class="row mt-2">
                                <security:authorize access="hasRole('ROLE_USER')">
                                        <div class="col-md-12"><label class="labels">Tên Đăng Nhập</label><input type="text"
                                            name="userName" class="form-control" placeholder="enter your phone number"
                                            value="${user.getUserName()}" required readonly></div>
                                </security:authorize>

                                    <div class="col-md-12"><label class="labels">Email</label><input type="text"
                                            name="email" class="form-control" placeholder="enter your email"
                                            value="${user.getEmail()}" required></div>
                                </div>

                                    <input type="hidden" name="password" class="form-control"

                                           value="${user.getPassword()}" required>

                                <c:if test="${param.success != null}">
                                    <div class="alert alert-success " role="alert" style="text-align: center;">
                                        Cập Nhập Thành Công!
                                    </div>
                                </c:if>
                                <c:if test="${param.error != null}">
                                    <div class="alert alert-danger " role="alert" style="text-align: center;">
                                        Cập Nhập Không Thành Công!
                                    </div>
                                </c:if>
                                <c:if test="${user.password != null}">
                                    <div class="mt-5 text-center"><button class="btn btn-primary profile-button"
                                            type="submit">Lưu Thông Tin</button></div>
                                </c:if>

                                 <input type="hidden" name="imgUrl" class="form-control" value="${user.imgUrl}">
                        </div>
                    </div>
                </div>
                </form>
            </div>
            </div>
            </div>
<script>
    document.querySelectorAll('#price').forEach(element => {
        let price = parseFloat(element.innerText.replace("đ", "").trim()); // Chuyển từ chuỗi sang số
        element.innerText = price.toLocaleString("vi-VN", { style: "currency", currency: "VND" }); // Format tiền tệ VNĐ
    });
</script>

<script>
    function displayImg(fileInput) {
        const file = fileInput.files[0];
        const imgPreview = $('#img_display');
        const imgChosen = $('#img_chosen');

        if(file){
            const reader = new FileReader();
            reader.onload = function (e){
                imgPreview.attr('src',e.target.result);
                imgPreview.css('display','block');
            }
            reader.readAsDataURL(file);
        }
        else{
            imgPreview.css('display','none');
        }

    }
</script>
        </body>

        </html>