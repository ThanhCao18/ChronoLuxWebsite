<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.hau.util.SecurityUtil" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/template/web/css/chat.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.6.1/sockjs.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>

<script src="${pageContext.request.contextPath}/template/web/js/notification.js"></script>
<!-- Topbar -->
<nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">
    <ul class="navbar-nav ml-auto">
        <li class="nav-item dropdown no-arrow mx-1">
            <a class="nav-link dropdown-toggle" style="font-size: 20px" href="#" id="alertsDropdown" role="button"
               data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <i class="fas fa-bell fa-fw"></i>
                <span class="badge badge-danger badge-counter"></span>
            </a>
            <div class="dropdown-list dropdown-menu dropdown-menu-right shadow animated--grow-in overflow-auto"
                 aria-labelledby="alertsDropdown">
                <h4 class="dropdown-header">Thông báo</h4>
                <div id="notificationList" class="p-2">
                    <span class="dropdown-item text-center text-gray-500">Đang tải...</span>
                </div>
            </div>


        </li>
        <div class="topbar-divider d-none d-sm-block"></div>
        <li class="nav-item dropdown no-arrow">
            <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown"
               aria-haspopup="true" aria-expanded="false">
                <img class="img-profile rounded-circle mr-3"
                     src="${pageContext.request.contextPath}/template/web/img/user-logos/<%=SecurityUtil.getPrincipal().getImgUrl()%>"
                     alt="Avatar">
                <span class="mr-2 d-none d-lg-inline text-gray-600">Xin chào, <%=SecurityUtil.getPrincipal().getFullName()%></span>

            </a>
            <!-- Dropdown - User Information -->
            <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in" aria-labelledby="userDropdown">
                <a class="dropdown-item" href="${pageContext.request.contextPath}/admin/admin-profile">
                    <i class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i>
                    Thông tin cá nhân
                </a>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="<c:url value='/logout'/>">
                    <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
                    Đăng xuất
                </a>
            </div>
        </li>
    </ul>
</nav>

<!-- End of Topbar -->