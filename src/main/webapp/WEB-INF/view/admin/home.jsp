<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<meta charset="UTF-8">
    <%@ include file="/common/taglib.jsp" %>

<head>
<title>ChronoLux - Trang chủ admin</title>
 <!-- Custom fonts for this template-->
    <link href="<c:url value='/template/admin/vendor/fontawesome-free/css/all.min.css'/>" rel="stylesheet" type="text/css">
    <link
        href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
        rel="stylesheet">
   <link href="<c:url value='/template/admin/css/sb-admin-2.min.css'/>" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
<!-- Begin Page Content -->
<div class="container-fluid">

    <!-- Page Heading -->
    <div class="d-sm-flex align-items-start mb-4">
        <i class="fas fa-fw fa-tachometer-alt h3 mt-1 mr-3" style="font-size: 26px"></i>
        <h1 class="h3 mb-2 text-gray-800">Dashboard</h1>
        <%--<a href="#" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm"><i
                class="fas fa-download fa-sm text-white-50"></i> Generate Report</a>--%>
    </div>
    <hr/>

    <!-- Content Row -->
    <div class="row">

        <!-- Earnings (Monthly) Card Example -->
        <div class="col-xl-3 col-md-6 mb-4">
            <div class="card border-left-success shadow h-100 py-2">
                <div class="card-body">
                    <div class="row no-gutters align-items-center">
                        <div class="col mr-2">
                            <div class="text-xs font-weight-bold text-success text-uppercase mb-1">
                                Đơn hàng đã bán</div>
                            <div class="h5 mb-0 font-weight-bold text-gray-800">${totalBoughtBill}</div>
                        </div>
                        <div class="col-auto">
                            <i class="fa-solid fa-bag-shopping fa-2x text-gray-300"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Earnings (Monthly) Card Example -->
        <div class="col-xl-3 col-md-6 mb-4">
            <div class="card border-left-danger shadow h-100 py-2">
                <div class="card-body">
                    <div class="row no-gutters align-items-center">
                        <div class="col mr-2">
                            <div class="text-xs font-weight-bold text-danger text-uppercase mb-1">
                                Đơn hàng bị hủy</div>
                            <div class="h5 mb-0 font-weight-bold text-gray-800">${totalCancelledBill}</div>
                        </div>
                        <div class="col-auto">
                            <i class="fa-solid fa-bag-shopping fa-2x text-gray-300"></i>

                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Thu nhap -->
        <div class="col-xl-3 col-md-6 mb-4">
            <div class="card border-left-primary shadow h-100 py-2">
                <div class="card-body">
                    <div class="row no-gutters align-items-center">
                        <div class="col mr-2">
                            <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
                                Thu nhập tháng ${currentMonth}</div>
                            <div class="h5 mb-0 font-weight-bold text-gray-800">${totalOfSuccessfulBillsInMonth}</div>
                        </div>
                        <div class="col-auto">
                            <i class="fas fa-dollar-sign fa-2x text-gray-300"></i> <%--tien--%>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-xl-3 col-md-6 mb-4">
            <div class="card border-left-dark shadow h-100 py-2">
                <div class="card-body">
                    <div class="row no-gutters align-items-center">
                        <div class="col mr-2">
                            <div class="text-xs font-weight-bold text-dark text-uppercase mb-1">
                                Tổng thu nhập</div>
                            <div class="h5 mb-0 font-weight-bold text-gray-800">${totalOfPaidBills}</div>
                        </div>
                        <div class="col-auto">
                            <i class="fa-solid fa-money-bill fa-2x text-gray-300"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Content Row -->

    <div class="row">

        <!-- Area Chart -->
        <div class="col-xl-8 col-lg-7">
            <div class="card shadow mb-4">
                <!-- Card Header - Dropdown -->
                <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                    <h6 class="m-0 font-weight-bold text-primary">Tổng quan thu nhập năm ${currentYear}</h6>
                    <div class="dropdown no-arrow">
                        <a class="dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown"
                            aria-haspopup="true" aria-expanded="false">
                            <i class="fas fa-ellipsis-v fa-sm fa-fw text-gray-400"></i>
                        </a>
                    </div>
                </div>
                <!-- Card Body -->
                <div class="card-body">
                    <div class="chart-area">
                        <canvas id="myAreaChart"></canvas>
                    </div>
                </div>
            </div>
        </div>

        <!-- Pie Chart -->
        <div class="col-xl-4 col-lg-5">
            <div class="card shadow mb-4">
                <!-- Card Header - Dropdown -->
                <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                    <h6 class="m-0 font-weight-bold text-primary">Top 5 sản phẩm bán chạy</h6>
                    <div class="dropdown no-arrow">
                        <a class="dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown"
                            aria-haspopup="true" aria-expanded="false">
                            <i class="fas fa-ellipsis-v fa-sm fa-fw text-gray-400"></i>
                        </a>
                    </div>
                </div>
                <!-- Card Body -->
                <div class="card-body">
                    <div class="chart-pie pt-4 pb-2">
                        <canvas id="myPieChart"></canvas>
                    </div>
                    <div class="mt-4 text-center small">
                        <c:forEach var="entry" items="${top5}" varStatus="status">
                            <c:if test="${status.index < 5}">
                                <span class="mr-2">
                                    <i class="fas fa-circle
                                        <c:choose>
                                            <c:when test="${status.index == 0}">text-danger</c:when>
                                            <c:when test="${status.index == 1}">text-warning</c:when>
                                            <c:when test="${status.index == 2}">text-success</c:when>
                                            <c:when test="${status.index == 3}">text-primary</c:when>
                                            <c:when test="${status.index == 4}">text-info</c:when>
                                        </c:choose>"></i>
                                    ${entry.key} (${entry.value})
                                </span>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="<c:url value='/template/admin/js/demo/chart-area-demo.js'/>"></script>
<script src="<c:url value='/template/admin/js/demo/chart-pie-demo.js'/>"></script>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        updateChartDataForYear(currentYear);
        fetchTotalQuantityPerProduct();
    });
</script>
<!-- /.container-fluid -->
</body>
