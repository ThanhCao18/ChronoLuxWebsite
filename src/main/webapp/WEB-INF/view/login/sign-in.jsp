<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglib.jsp" %>
<!DOCTYPE html>
<html lang="en">

<head>
	<title>ChronoLux - Sign In</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
	<link href="https://stackpath.bootstrapcdn.com/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">

	<style>
		body, html {
			font-family: 'Arial', sans-serif;
			background-color: #f4f4f4;
		}

		.ftco-section {
			min-height: 80vh;
			display: flex;
			align-items: center;
			background-image: url('<c:url value="/src/main/webapp/template/login/img/bg-1.jpg"/>');
			background-size: cover;
			background-position: center;
			background-attachment: fixed;
			color: #444;
		}

		.login-container {
			background: rgba(255, 255, 255, 0.9);
			border-radius: 10px;
			box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
			padding: 30px;
		}

		h2.heading-section {
			color: #333;
			font-weight: 700;
			letter-spacing: 1px;
			font-family: Corbel;
		}

		.btn-primary {
			background-color: #333;
			border: none;
			font-weight: bold;
			transition: all 0.3s ease;
		}

		.btn-primary:hover {
			background-color: #555;
		}


		.social-icon:hover {
			background-color: #ddd;
			color: #555;
		}


	.form-control, .btn {
			border-radius: 50px;
		}

		.toast-container .toast-header {
			background-color: #333;
			color: #fff;
		}
	</style>
</head>

<body>
<header class="header mb-0 ">
	<nav class="navbar navbar-expand-lg navbar-light mb-0">
		<div class="container">
			<a href="<c:url value='/home'/>" class="navbar-brand">
				<img src="${pageContext.request.contextPath}/template/web/img/Logo.svg" alt="ChronoLux Logo">
			</a>
		</div>
	</nav>
</header>
<section class="ftco-section pt-0">
	<div class="row justify-content-center mx-0 py-5" style="background-image: url('${pageContext.request.contextPath}/template/login/img/bg-6.jpg'); background-size: cover; background-position: center; background-repeat: no-repeat; min-height: 400px;">
		<div class="col-12 col-md-8 col-lg-6 py-5">
			<div class="login-container p-4 rounded-5 bg-white shadow col-12 col-sm-10 col-md-8 col-lg-6 mx-auto" style="border-radius: 1rem; min-width: 350px;">
				<h3 class="mb-4 text-center">Đăng Nhập</h3>
				<div class="d-flex justify-content-center mb-4">
					<a href="https://www.facebook.com/v19.0/dialog/oauth?scope=email&client_id=1529293951010030&redirect_uri=http://localhost:8080/ChronoLuxWeb/login-facebook&prompt=login"
					   class="social-icon d-flex align-items-center justify-content-center rounded-circle bg-light text-primary text-decoration-none me-3"
					   style="width: 40px; height: 40px; font-size: 1.5rem; transition: background-color 0.3s;">
						<i class="fa fa-facebook"></i>
					</a>
					<a href="https://accounts.google.com/o/oauth2/auth?scope=profile%20email&redirect_uri=http://localhost:8080/ChronoLuxWeb/login-google&response_type=code&client_id=236344479421-3djectr8kffvuu3ec4o7ceges5jv2jld.apps.googleusercontent.com&approval_prompt=force"
					   class="social-icon d-flex align-items-center justify-content-center rounded-circle bg-light text-dark text-decoration-none"
					   style="width: 40px; height: 40px; font-size: 1.5rem; transition: background-color 0.3s;">
						<i class="fa fa-google"></i>
					</a>
				</div>

				<form action="<c:url value='/j_spring_security_check'/>" id="formLogin" method="post" class="signin-form">
					<div class="form-group mb-3">
						<label class="label" for="name">Tên Người Dùng</label>
						<input type="text" class="form-control" name="j_username" placeholder="Tên Người Dùng" required>
					</div>
					<div class="form-group mb-3">
						<label class="label" for="password">Mật Khẩu</label>
						<input type="password" class="form-control" name="j_password" placeholder="Mật Khẩu" required>
					</div>
					<div id="error" class="text-danger mb-3 text-center"></div>
					<c:if test="${param.incorrectAccount != null}">
						<div class="alert alert-danger text-center">Người Dùng Không Tồn Tại</div>
					</c:if>
					<c:if test="${param.accessDenied != null}">
						<div class="alert alert-danger text-center">Truy Cập Từ Chối</div>
					</c:if>
					<div class="form-group mb-3 d-flex justify-content-center">
						<div class="g-recaptcha" data-sitekey="6LcvolUqAAAAAHsPdMaMhrNDeg_HE-FuNR4XO95n"></div>
					</div>
					<div class="form-group">
						<button type="submit" class="form-control btn btn-dark w-100">Đăng Nhập</button>
					</div>
					<div class="form-group d-flex justify-content-between mt-3">
						<div class="form-check">
							<input type="checkbox" name="remember-me" class="form-check-input" id="rememberMe">
							<label class="form-check-label" for="rememberMe">Ghi Nhớ Tài Khoản</label>
						</div>
						<a href="<c:url value='/login/forgot-password'/>" style="text-decoration: none">Quên Mật Khẩu?</a>
					</div>
				</form>
				<p class="text-center mt-3">Chưa có Tài Khoản? <a href="<c:url value='/login/register'/>" style="text-decoration: none">Đăng ký</a></p>
			</div>
		</div>
	</div>
	<div class="toast-container position-fixed bottom-0 end-0 p-3">
		<div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
			<div class="toast-header">
				<img src="<c:url value='/template/web/img/ChronoLuxIcon.svg'/>" class="rounded me-2" alt="...">
				<strong class="me-auto">Success!</strong>
				<small>11 mins ago</small>
				<button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
			</div>
			<div class="toast-body">Register successfully!</div>
		</div>
	</div>
</section>


<script src="https://www.google.com/recaptcha/api.js" async defer></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>
<script>
	<c:if test="${param.registerSuccessful != null}">
	var toastLiveExample = document.getElementById("liveToast");
	var toast = new bootstrap.Toast(toastLiveExample);
	toast.show();
	</c:if>

	window.onload = function() {
		const form = document.getElementById("formLogin");
		const error = document.getElementById("error");
		form.addEventListener("submit", function(event) {
			event.preventDefault();
			const response = grecaptcha.getResponse();
			if (response) {
				form.submit();
			} else {
				error.innerText = "Please complete the CAPTCHA";
			}
		});
	}
</script>
</body>

</html>
