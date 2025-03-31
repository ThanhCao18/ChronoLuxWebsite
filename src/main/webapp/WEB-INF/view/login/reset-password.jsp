<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>ChronoLux- Reset Password</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
<div class="row align-items-center py-0 px-xl-5 ">
    <div class="col-lg-3 d-none d-lg-block">
        <a href="<c:url value='/home'/>" class="text-decoration-none">
            <img src="${pageContext.request.contextPath}/template/web/img/Logo.svg" alt="ChronoLux Logo">
        </a>
    </div>
    <section class="ftco-section pt-0 ">
        <div class="row justify-content-center mx-0 py-5 d-flex justify-content-center align-items-center" style="background-image: url('${pageContext.request.contextPath}/template/login/img/bg-6.jpg'); background-size: cover; background-position: center; background-repeat: no-repeat; min-height: 650px;">
            <div class="login-container p-4 rounded-5 bg-white shadow col-12 col-sm-10 col-md-8 col-lg-6 " style="border-radius: 1rem; max-width: 30%">
                <p class="text-center">
                <h1 class="textholder py-3" style="text-align: center;color: black;font-size: 2rem; font-family: Arial ;font-weight: bold;border-radius: 10px;">
                    Đặt Lại Mật Khẩu
                </h1>
                </p>

                <form action="<c:url value='/login/reset_password'/>"  method="post" >
                    <input type="hidden" name="token" value="${token}" />
                    <div class="container-input p-3">
                        <div>
                            <c:if test="${not empty message}">
                                <p class="text-warning"> ${message} </p>
                            </c:if>
                            <p>
                                <input type="password" name="password" id="password" class="form-control"
                                       placeholder="Nhập Mật Khẩu Mới" required autofocus />
                            </p>
                            <p>
                                <input type="password" id="confirmPassword" class="form-control" placeholder="Nhập Lại Mật Khẩu Mới" oninput="checkPasswordMatch(this);"
                                       required  />
                            </p>
                            <p class="text-center">
                                <input type="submit" value="Thay Đổi Mật Khẩu" class="btn btn-dark my-3" style="width: 100% " />
                            </p>
                        </div>
                        <div class="row">
                            <div class="col-12">
                                <div class="d-flex gap-4 justify-content-center">
                                    <a href="<c:url value='/login'/>" class="link-secondary text-decoration-none"><i style="font-style: normal; color: #2e59d9">Đăng Nhập</i> </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </section>
<script>
    function checkPasswordMatch(fieldConfirmPassword){
        if(fieldConfirmPassword.value != $('#password').val()){
            fieldConfirmPassword.setCustomValidity("Mật Khẩu Không Trùng");
        }
        else{
            fieldConfirmPassword.setCustomValidity("");
        }
    }
</script>
</body>
</html>