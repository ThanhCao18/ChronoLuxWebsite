<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ page import="com.hau.util.SecurityUtil" %>
     <%@ page import="org.springframework.security.core.Authentication" %>
      <%@ page import="org.springframework.security.core.GrantedAuthority" %>
       <%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
           <%@ page import="com.hau.dto.CustomerO2Auth" %>
               <%@ page import="com.hau.dto.MyUser" %>
                <%@ include file="/common/taglib.jsp" %>

<!-- Topbar Start -->
<div class="container-fluid">
    <div class="row align-items-center py-0 px-xl-5">
        <div class="col-lg-3 d-none d-lg-block">
            <a href="<c:url value='/home'/>" class="text-decoration-none">
                <img src="${pageContext.request.contextPath}/template/web/img/Logo.svg" alt="ChronoLux Logo">
            </a>
        </div>
        <div class="col-lg-6 col-6 text-left">

        </div>
        <div class="col-lg-3 col-6 text-right">

            <a href="<c:url value='/cart?alert=true'/>" class="btn border">
                <i class="fas fa-shopping-cart text-primary"></i>
                <span class="badge" id = "cart">0</span>
            </a>
        </div>
    </div>
</div>
<!-- Topbar End -->

<!-- Navbar Start -->
<style>
    /* Custom styles for centering, font size, and spacing */
    .navbar-nav-center {
        font-size: 1.1rem; /* Adjust font size as needed */
    }
    .navbar-nav-center .nav-link {
        margin: 0 20px; /* Increase spacing between items */
    }
</style>

<div class="container-fluid mx-0" style="border-bottom: 2px solid; border-bottom-color: rgb(234,189,43)">
    <div class="col-lg-12">
        <nav class="navbar navbar-expand-lg bg-light navbar-light py-3 py-lg-0 px-0">
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse d-flex justify-content-between" id="navbarCollapse">
                <!-- Left Spacer (empty div with equal flex) -->
                <div class="d-none d-lg-flex flex-grow-1"></div>

                <!-- Centered Navbar Links -->
                <div class="navbar-nav mx-auto d-flex justify-content-center">
                    <a href="<c:url value='/home'/>" class="nav-item nav-link ">Trang Chủ</a>
                    <a href="<c:url value='/shop?page=1&limit=12'/>" class="nav-item nav-link">Cửa Hàng</a>
                    <a href="<c:url value='/shop?page=1&limit=12&filter=nam'/>" class="nav-item nav-link">Cho Nam</a>
                    <a href="<c:url value='/shop?page=1&limit=12&filter=nu'/>" class="nav-item nav-link">Cho Nữ</a>
                </div>

                <!-- Right-aligned Navbar Links (Login/Register) with equal flex -->
                <div class="navbar-nav d-flex align-items-center flex-grow-1 justify-content-end">
                    <security:authorize access="isAnonymous()">
                        <a href="<c:url value='/login'/>" class="nav-item nav-link">Đăng Nhập</a>
                        <a href="<c:url value='/login/register'/>" class="nav-item nav-link">Đăng Ký</a>
                    </security:authorize>

                    <security:authorize access="isAuthenticated()">
                        <div class="nav-item dropdown">
                            <%
                                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                                String fullName = "";

                                if (principal instanceof CustomerO2Auth) {
                                    fullName = SecurityUtil.getPrincipalO2Auth().getFullName();
                                } else if (principal instanceof MyUser) {
                                    fullName = SecurityUtil.getPrincipal().getFullName();
                                }
                            %>
                            <a href="#" class="nav-link" id="userDropdown" role="button"
                               data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                Chào Mừng, <%=fullName%>
                            </a>

                            <div class="dropdown-menu dropdown-menu-right"
                                 aria-labelledby="userDropdown">
                                <a class="dropdown-item" href="<c:url value='/user-profile'/>">Thông Tin Tài Khoản</a>
                                <a class="dropdown-item" href="<c:url value='/user-order'/>">Theo Dõi Đơn Hàng</a>
                                <a class="dropdown-item" href="<c:url value='/logout'/>">Đăng Xuất</a>
                            </div>
                        </div>
                    </security:authorize>
                </div>
            </div>
            <style>
                .navbar-nav .nav-link {
                    padding-left: 0;
                    padding-right: 0;
                    margin-left: 5px;
                    margin-right: 5px;
                }
                .navbar-collapse > .flex-grow-1 {
                    flex-basis: 0;
                    flex-grow: 1;
                }
            </style>
        </nav>
    </div>
</div>

 <script>
     $(document).ready(function() {
         // Gọi AJAX khi trang được tải
         $.ajax({
             url: "<c:url value='/cart/total'/>" , // URL của endpoint
             type: 'GET',
             success: function(total) {

                 $('#cart').text(total.toFixed(0)); // Làm tròn và hiển thị
             },
             error: function(jqXHR, textStatus, errorThrown) {
                 console.error("Error fetching cart total: ", textStatus, errorThrown);
             }
         });
     });
 </script>

