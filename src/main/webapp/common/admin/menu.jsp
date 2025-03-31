<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglib.jsp" %>

    <!-- Sidebar -->
<ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">
    <hr/>
    <!-- Sidebar - Brand -->
    <a class="sidebar-brand d-flex flex-column align-items-center justify-content-center p-5" href="<c:url value='/admin/home'/>">
        <div class="sidebar-brand-icon p-3">
            <i class="fa fa-user-tie"></i>
        </div>
        <div class="sidebar-brand-text mx-4 text-center" style="font-size: 20px">ChronoLux Admin</div>
    </a>

    <!-- Divider -->
    <hr/>
    <hr class="sidebar-divider my-0">
    <!-- Nav Item - Dashboard -->
    <li class="nav-item <%--active--%>">
        <a class="nav-link" href="<c:url value='/admin/home'/>">
            <i class="fas fa-fw fa-tachometer-alt" style="font-size: 16px"></i>
            <span style="font-size: 15px">Dashboard</span>
        </a>
    </li>

    <li class="nav-item <%--active--%>">
        <a class="nav-link" href="<c:url value='/admin/bills'/>">
            <i class="ml-1 mr-2 fa-solid fa-receipt" style="font-size: 16px"></i>
            <span style="font-size: 15px">Đơn hàng</span>
        </a>
    </li>

    <!-- Divider -->
    <hr class="sidebar-divider">
    <%--<&lt;%&ndash;div class="sidebar-heading">
        Doanh nghiệp
    </div>&ndash;%&gt;--%>
    <li class="nav-item">
        <a class="nav-link" href="<c:url value='/admin/brands'/>">
            <i class="fas fa-star" style="font-size: 16px"></i>
            <span style="font-size: 15px">Thương hiệu</span>
        </a>
    </li>
    <li class="nav-item">
        <a class="nav-link" href="<c:url value='/admin/product-lines'/>">
            <i class="fa-solid fa-sitemap" style="font-size: 16px"></i>
            <span style="font-size: 15px">Dòng sản phẩm</span>
        </a>
    </li>
    <li class="nav-item">
        <a class="nav-link" href="<c:url value='/admin/products'/>">
            <i class="fa-solid fa-boxes-stacked" style="font-size: 16px"></i>
            <span style="font-size: 15px">Sản phẩm</span>
        </a>
    </li>
    <%--<li class="nav-item">
        <a class="nav-link" href="<c:url value='/admin/comment'/>">
            <i class="fa-solid fa-message" style="font-size: 16px"></i>
            <span style="font-size: 15px">Bình luận</span>
        </a>
    </li>--%>
    <li class="nav-item">
        <a class="nav-link" href="<c:url value='/admin/vouchers'/>">
            <i class="fa-solid fa-ticket" style="font-size: 16px"></i>
            <span style="font-size: 15px">Mã giảm giá</span>
        </a>
    </li>
    <li class="nav-item">
        <a class="nav-link" href="<c:url value='/admin/warranty'/>">
            <i class="fa-solid fa-paperclip" style="font-size: 16px"></i>
            <span style="font-size: 15px">Chính sách bảo hành</span>
        </a>
    </li>
    <!-- Divider -->
    <hr class="sidebar-divider">
    <li class="nav-item">
        <a class="nav-link" href="<c:url value='/admin/corporation'/>">
            <i class="fa-solid fa-circle-info" style="font-size: 16px"></i>
            <span style="font-size: 15px">Thông tin doanh nghiệp</span>
        </a>
    </li>
    <li class="nav-item">
        <a class="nav-link" href="<c:url value='/admin/posts'/>">
            <i class="fa-solid fa-newspaper" style="font-size: 16px"></i>
            <span style="font-size: 15px">Banner</span>
        </a>
    </li>
    <hr class="sidebar-divider">
    <li class="nav-item">
        <a class="nav-link" href="<c:url value='/admin/accounts'/>">
            <i class="fa-solid fa-users" style="font-size: 16px"></i>
            <span style="font-size: 15px">Quản lý tài khoản</span>
        </a>
    </li>
    <hr class="sidebar-divider">
</ul>

<!-- End of Sidebar -->