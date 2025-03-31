
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>ChronoLux - Forgot Password</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

</head>
<body>
<div class="col-lg-3 d-none d-lg-block">
  <a href="<c:url value='/home'/>" class="text-decoration-none">
    <img src="${pageContext.request.contextPath}/template/web/img/Logo.svg" alt="ChronoLux Logo">
  </a>
</div>
<div class="mainpage py-3 py-md-5 d-flex justify-content-center align-items-center "style="background-image: url('${pageContext.request.contextPath}/template/login/img/bg-6.jpg'); background-size: cover; background-position: center; background-repeat: no-repeat; min-height: 650px;">
  <div class="container">
    <div class="row justify-content-md-center">
      <div class="col-12 col-md-11 col-lg-8 col-xl-7 col-xxl-6">
        <div class="bg-white p-4 p-md-5 rounded shadow-sm">
          <div class="row gy-3 mb-5">
            <div class="col-12">
              <div class="text-center">
              </div>
            </div>
            <div class="col-12">
              <h2 class="fs-6 fw-normal text-center text-secondary m-0 px-md-5">Vui Lòng Nhập Email Đã Đăng Ký</h2>
            </div>
          </div>
          <form action="<c:url value='/login/forgot-password'/>" method="post">
            <div class="row gy-3 gy-md-4 overflow-hidden">
                <c:if test="${not empty error }">
                    <p class="text-danger"> ${error} </p>
                 </c:if>
                 <c:if test="${not empty message }">
                    <p class="text-warning"> ${message} </p>
                 </c:if>
              <div class="col-12">
                <label for="email" class="form-label">Email <span class="text-danger">*</span></label>
                <div class="input-group">
                  <span class="input-group-text">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-envelope" viewBox="0 0 16 16">
                      <path d="M0 4a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v8a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V4Zm2-1a1 1 0 0 0-1 1v.217l7 4.2 7-4.2V4a1 1 0 0 0-1-1H2Zm13 2.383-4.708 2.825L15 11.105V5.383Zm-.034 6.876-5.64-3.471L8 9.583l-1.326-.795-5.64 3.47A1 1 0 0 0 2 13h12a1 1 0 0 0 .966-.741ZM1 11.105l4.708-2.897L1 5.383v5.722Z" />
                    </svg>
                  </span>
                  <input type="email" class="form-control" name="email" id="email" required>
                </div>
              </div>
              <div class="col-12">
                <div class="d-grid">
                  <button class="btn btn-dark btn-lg" type="submit">Gửi Email</button>
                </div>
              </div>
            </div>
          </form>
          <div class="row">
            <div class="col-12">
              <hr class="mt-5 mb-4 border-secondary-subtle">
              <div class="d-flex gap-4 justify-content-center">
                <a href="<c:url value='/login'/>" class="link-secondary text-decoration-none"><i style="font-style: normal; color: #2e59d9">Đăng Nhập</i> </a>
                <a href="<c:url value='/login/register'/>" class="link-secondary text-decoration-none"><i style="font-style: normal; color: #2e59d9">Đăng Ký</i> </a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
</html>