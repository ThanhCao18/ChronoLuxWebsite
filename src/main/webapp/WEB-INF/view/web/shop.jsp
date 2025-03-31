<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ include file="/common/taglib.jsp" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="utf-8">
            <title>ChronoLux - Shop</title>
            <meta content="width=device-width, initial-scale=1.0" name="viewport">
            <meta content="Free HTML Templates" name="keywords">
            <meta content="Free HTML Templates" name="description">
            <!-- Favicon -->
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
            <link href="<c:url value='/template/web/img/ChronoLuxIcon.svg'/>" rel="icon">

            <!-- Google Web Fonts -->
            <link rel="preconnect" href="https://fonts.gstatic.com">
            <link
                href="https://fonts.googleapis.com/css2?family=Poppins:wght@100;200;300;400;500;600;700;800;900&display=swap"
                rel="stylesheet">

            <!-- Font Awesome -->
            <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">

            <!-- Libraries Stylesheet -->
            <link href="<c:url value='/template/web/lib/owlcarousel/assets/owl.carousel.min.css'/>" rel="stylesheet">

            <!-- Customized Bootstrap Stylesheet -->
            <link href=" <c:url value='/template/web/css/style.css'/>" rel="stylesheet">

            <%----------------------------%>
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

                <script type="text/javascript"
                    src="<c:url value='/template/paging/jquery.twbsPagination.js' />"></script>

                <style>
                    .highlight {
                        font-size: 1.5rem;
                        font-weight: bold;
                        color: #333;
                        text-align: center;
                        margin-top: 10px;
                        margin-left: 60px;
                    }

                    .highlight .fa-check-circle {
                        color: green;
                        margin: 0 5px;
                    }

                    .highlight .small-text {
                        font-size: 1rem;
                        font-weight: normal;
                    }

                    .banner-image {
                        border-radius: 10px;
                        /* Bo góc ảnh */
                        height: 100%;
                        width: 100%;
                        object-fit: cover;
                        /* Cắt ảnh để giữ tỉ lệ */
                    }

                    .carousel-inner {
                        max-width: 1200px;
                        margin: 0 auto;
                    }

                    .carousel-control-prev-icon,
                    .carousel-control-next-icon {
                        background-color: #000;
                        /* Đổi màu nền nút điều khiển */
                        border-radius: 50%;
                    }

                    .carousel-item {
                        transition: transform 0.15s ease-in-out;
                        /* Thời gian chuyển slide là 0.5 giây */
                    }

                    .disabled {
                        pointer-events: none;
                        /* Ngăn chặn sự tương tác */
                        opacity: 0.5;
                        /* Giảm độ mờ của nút */
                        cursor: not-allowed;
                        /* Hiển thị con trỏ như không cho phép */
                    }

                    .disabled-link {
                        pointer-events: none;
                        opacity: 0.5;
                        cursor: default;
                    }

                    .try-on-label {
                        position: absolute;
                        top: 10px;
                        left: 10px;
                        background-color: white;
                        font-size: 13px;
                        color: red;
                        padding: 3px 7px;
                        border-radius: 5px;
                        border: 1px solid #ff0000;
                    }
                </style>

        </head>

        <body>
            <!-- Page Header Start -->
            <div class="highlight p-4 ml-0"
                style="background-image: url('https://png.pngtree.com/png-vector/20240611/ourmid/pngtree-sleek-and-sophisticated-beautiful-grey-silk-or-satin-texture-ideal-for-png-image_12323800.png');background-position: center;background-size: cover;background-repeat: no-repeat;">
                <h1 style="font-family:Arial">Đồng Hồ Xu Hướng 2024</h1>
                <i class="fas fa-check-circle" style="color: rgb(234,189,43)"></i>
                <span class="small-text">100% Chính Hãng</span>
            </div>
            <div class="container-fluid pt-5">
                <div class="row px-xl-5 pb-3 d-flex justify-content-center align-items-center flex-wrap">
                    <c:forEach var="item" items="${brand.listResult}">
                        <div class="col-lg-2 col-md-6">
                            <div class="item d-flex flex-column justify-content-center align-items-center my-3"
                                style="border: 3px solid;border-radius: 1rem;max-width: 100%; height: 115px;">
                                <a href="<c:url value='/shop/brand?id=${item.id}&page=1&limit=8'/>"
                                    class="cat-img overflow-hidden d-flex justify-content-center ">
                                    <img class="img-fluid p-2"
                                        style="max-width: 100%; max-height: 100%; object-fit: cover;width: fit-content;"
                                        src="<c:url value='/template/web/img/brands/${item.iconUrl}'/>" alt="">
                                </a>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <!-- Page Header End -->
            <div class="col-12 pb-1">
                <c:if test="${not empty model.filter}">
                    <div class="row mb-4" style="margin-left: 20px;"> <!-- Dịch sang trái một chút -->
                        <div class="col-12">
                            <span class="font-weight-bold" style="color: black; font-family: Arial">Đã chọn:</span>

                            <!-- Tách filter thành mảng -->
                            <c:set var="filterArray" value="${fn:split(model.filter, ',')}" />

                            <!-- Duyệt qua từng phần tử trong mảng và tạo button -->
                            <c:forEach var="filterItem" items="${filterArray}">
                                <c:if test="${priceFilters[filterItem] != null}">
                                    <button id="btn-${filterItem}" type="button" class="btn btn-primary ml-2">
                                        ${priceFilters[filterItem]}
                                        <!-- Lấy giá trị từ Map dựa trên từng phần tử trong filter -->
                                        <span class="close" aria-label="Close" style="cursor: pointer;">&times;</span>
                                    </button>
                                </c:if>
                            </c:forEach>

                            <button type="button" id="clearAllFilter" class="btn btn-primary ml-2">
                                Xóa hết
                                <span class="close" aria-label="Close" style="cursor: pointer;">&times;</span>
                            </button>

                        </div>
                    </div>
                </c:if>
            </div>
            <div class="col-12 py-2" style="margin-left: 35px;">
                <span class="font-weight-bold mr-2" style=" color: black; font-family: Arial">Phổ biến:</span>
                <c:choose>
                    <c:when test="${not empty model.filter}">
                        <c:set var="currentFilters" value="${fn:split(model.filter, ',')}" />
                        <c:forEach items="${priceFilters.entrySet()}" var="entry">
                            <c:set var="filterKey" value="${entry.key}" />
                            <c:set var="filterValue" value="${entry.value}" />

                            <!-- Kiểm tra nếu đã chọn giá rồi thì không cho chọn thêm giá nữa -->
                            <c:choose>
                                <c:when
                                    test="${(fn:contains(model.filter, 'duoi-1-trieu') or fn:contains(model.filter, 'tu-1-3-trieu') or fn:contains(model.filter, 'tu-3-6-trieu') or fn:contains(model.filter, 'tu-6-9-trieu') or fn:contains(model.filter, 'tren-9-trieu'))
                                              and (filterKey == 'duoi-1-trieu' or filterKey == 'tu-1-3-trieu' or filterKey == 'tu-3-6-trieu' or filterKey == 'tu-6-9-trieu' or filterKey == 'tren-9-trieu')}">
                                    <!-- Nếu đã chọn giá, hiện thông báo khi người dùng cố gắng chọn thêm giá -->
                                    <a href="javascript:void(0);"
                                        class="btn btn-outline-danger btn-sm rounded-pill mx-1"
                                        style="border: 2px solid"
                                        onclick="alert('Bạn đã chọn một mức giá. Không thể chọn thêm!');">
                                        ${filterValue}
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <!-- Nếu chưa chọn giá hoặc là bộ lọc giới tính thì cho phép chọn -->
                                    <a href="<c:url value='/shop?page=1&limit=${model.limit}&filter=${model.filter},${filterKey}'/>"
                                        class="btn btn-outline-primary btn-sm rounded-pill mx-1"
                                        style="border: 2px solid">${filterValue}</a>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </c:when>



                    <c:otherwise>
                        <a href="<c:url value='/shop?page=1&limit=${model.limit}&filter=nam'/>"
                            class="btn btn-outline-primary btn-sm rounded-pill mx-1" style="border: 2px solid">Nam</a>
                        <a href="<c:url value='/shop?page=1&limit=${model.limit}&filter=nu'/>"
                            class="btn btn-outline-primary btn-sm rounded-pill mx-1" style="border: 2px solid">Nữ</a>
                        <a href="<c:url value='/shop?page=1&limit=${model.limit}&filter=duoi-1-trieu'/>"
                            class="btn btn-outline-primary btn-sm rounded-pill mx-1" style="border: 2px solid">Dưới 1
                            triệu</a>
                        <a href="<c:url value='/shop?page=1&limit=${model.limit}&filter=tu-1-3-trieu'/>"
                            class="btn btn-outline-primary btn-sm rounded-pill mx-1" style="border: 2px solid">Từ 1 - 3
                            triệu</a>
                        <a href="<c:url value='/shop?page=1&limit=${model.limit}&filter=tu-3-6-trieu'/>"
                            class="btn btn-outline-primary btn-sm rounded-pill mx-1" style="border: 2px solid">Từ 3 - 6
                            triệu</a>
                        <a href="<c:url value='/shop?page=1&limit=${model.limit}&filter=tu-6-9-trieu'/>"
                            class="btn btn-outline-primary btn-sm rounded-pill mx-1" style="border: 2px solid">Từ 6 - 9
                            triệu</a>
                        <a href="<c:url value='/shop?page=1&limit=${model.limit}&filter=tren-9-trieu'/>"
                            class="btn btn-outline-primary btn-sm rounded-pill mx-1" style="border: 2px solid">Trên 9
                            triệu</a>
                    </c:otherwise>
                </c:choose>
            </div>


            <!-- Shop Start -->
            <div class="container-fluid pt-5">
                <div class="col-12 pb-1">
                    <div class="row mb-4">
                        <!-- Search and Filter aligned to the right -->
                        <div class="col-12 d-flex justify-content-end">
                            <!-- Search -->
                            <form action="<c:url value='/shop'/>" method="get" class="d-flex w-auto"
                                style="border: 2px solid; border-color: rgb(234,189,43)">
                                <div class="input-group" style="width: 250px;"> <!-- Set width of the search bar -->
                                    <input type="text" class="form-control" placeholder="Tìm Kiếm" name="keyword"
                                        style="border-right: 2px solid; border-right-color: rgb(234,189,43)">
                                    <input type="hidden" value="1" name="page">
                                    <input type="hidden" value="8" name="limit">
                                    <div class="input-group-append">
                                        <span class="input-group-text bg-transparent text-primary">
                                            <i class="fa fa-search"></i>
                                        </span>
                                    </div>
                                </div>
                            </form>

                            <!-- Filter -->
                            <div class="dropdown ml-3">
                                <button class="btn dropdown-toggle" type="button" id="triggerId" data-toggle="dropdown"
                                    aria-haspopup="true" aria-expanded="false"
                                    style="border: 2px solid; border-color: rgb(234,189,43)">
                                    Bộ Lọc
                                </button>
                                <div class="dropdown-menu dropdown-menu-right" aria-labelledby="triggerId">
                                    <a class="dropdown-item"
                                        href="<c:url value='/shop?page=${model.page}&limit=${model.limit}&sortName=name&sortBy=asc&keyword=${model.keyword}'/>">
                                        <i class="fas fa-sort-alpha-down"></i> Theo tên: A-Z
                                    </a>
                                    <a class="dropdown-item"
                                        href="<c:url value='/shop?page=${model.page}&limit=${model.limit}&sortName=price&sortBy=asc&keyword=${model.keyword}'/>">
                                        <i class="fas fa-arrow-up"></i> Theo giá: Tăng dần
                                    </a>
                                    <a class="dropdown-item"
                                        href="<c:url value='/shop?page=${model.page}&limit=${model.limit}&sortName=price&sortBy=desc&keyword=${model.keyword}'/>">
                                        <i class="fas fa-arrow-down"></i> Theo giá: Giảm dần
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <form action="<c:url value='/shop'/>" id="formSubmit" method="get">
                    <div class="row px-xl-5">
                        <!-- Shop Product Start -->
                        <div class="col-lg-12 col-md-12">
                            <div class="product-list d-flex justify-content-center align-items-center flex-wrap">
                                <c:forEach var="product" items="${products}">
                                    <div class="product-card p-2 bg-white shadow mx-3 my-3 "
                                        style="border-radius: 1rem; max-width: 300px">
                                        <div class="card product-item border-0 mb-4">
                                            <c:if test="${product.stock <= 0}">
                                                <span class="try-on-label">Bán Hết</span>
                                            </c:if>
                                            <div class="image-container m-5" style="height: 333px; width:200px">
                                            <img class="img-fluid" style="width: fit-content;"
                                                src="<c:url value='/template/web/img/products/${product.imgUrl}'/>"
                                                alt="">
                                            </div>
                                            <div class="card-body text-center p-0 pt-4 pb-3">
                                                <h6 class="text-truncate mb-3">${product.name}</h6>

                                                <div class="d-flex justify-content-center">
                                                    <h6 id="price" style="color:green;">${product.price}đ</h6>
                                                </div>
                                            </div>
                                            <div class="button-container p-0 my-1 mx-2 d-flex ">
                                                <a href="<c:url value='/product-detail?id=${product.id}'/>"
                                                    class="btn bg-dark text-light p-2 flex-grow-1"
                                                    style="border-radius: 1rem">Chi Tiết</a>
                                            </div>
                                            <div class="button-container p-0 my-1 mx-2 d-flex">
                                                <c:if test="${product.stock <= 0}">
                                                    <a href="<c:url value='/cart/add?productId=${product.id}&quantity=1'/>"
                                                        class="btn btn-sm text-light p-2 flex-grow-1 disabled-link"
                                                        style="border-radius: 1rem; background-color: gray;">
                                                        Tạm Hết Hàng
                                                    </a>
                                                </c:if>
                                                <c:if test="${product.stock > 0}">
                                                    <a href="<c:url value='/cart/add?productId=${product.id}&quantity=1'/>"
                                                        class="btn bg-dark text-light p-2 flex-grow-1"
                                                        style="border-radius: 1rem">Thêm Vào Giỏ</a>
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>

                            <div class="col-12 pb-1">
                                <nav aria-label="Page navigation">
                                    <ul class="pagination justify-content-center mb-3" id="pagination"> </ul>
                                    <input type="hidden" value="" id="page" name="page" />
                                    <input type="hidden" value="" id="limit" name="limit" />

                                    <c:if test="${model.sortName != null && model.sortBy != null}">
                                        <input type="hidden" id="sortName" name="sortName" value="${model.sortName}" />
                                        <input type="hidden" id="sortBy" name="sortBy" value="${model.sortBy}" />
                                    </c:if>
                                    <c:if test="${model.keyword != null}">
                                        <input type="hidden" id="keyword" name="keyword" value="${model.keyword}" />
                                    </c:if>
                                    <c:if test="${model.filter != null}">
                                        <input type="hidden" id="filter" name="filter" value="${model.filter}" />
                                    </c:if>
                                    <c:if test="${model.gender != null}">
                                        <input type="hidden" id="gender" name="gender" value="${model.gender}" />
                                    </c:if>
                                </nav>
                            </div>
                        </div>
                    </div>
                </form>
                <!-- Shop Product End -->
            </div>
            <div id="bannerCarousel" class="carousel slide carousel-slide my-3 pb-4" data-bs-ride="carousel"
                data-bs-interval="3000">
                <div class="carousel-inner">
                    <!-- Slide 1 -->
                    <div class="carousel-item active">
                        <div class="row">
                            <div class="col-md-6">
                                <a href="#">
                                    <img src="https://www.watchstore.vn/images/products/collection/slideshow/2024/05/10/compress/banner-sale-off-orient_1715333758.webp"
                                        class="d-block w-100 banner-image" alt="Orient Sale 20%">
                                </a>
                            </div>
                            <div class="col-md-6">
                                <a href="#">
                                    <img src="https://www.watchstore.vn/images/products/collection/slideshow/2024/05/10/compress/banner-sale-off-tissot_1715333668.webp"
                                        class="d-block w-100 banner-image" alt="Bonest Gatti Sale 35%">
                                </a>
                            </div>
                        </div>
                    </div>

                    <!-- Slide 3 -->
                    <div class="carousel-item">
                        <div class="row">
                            <div class="col-md-6">
                                <a href="#">
                                    <img src="https://www.watchstore.vn/images/products/collection/slideshow/2024/05/10/compress/banner-sale-off-bonestgatti_1715333537.webp"
                                        class="d-block w-100 banner-image" alt="Orient Sale 20%">
                                </a>
                            </div>
                            <div class="col-md-6">
                                <a href="#">
                                    <img src="https://www.watchstore.vn/images/products/collection/slideshow/2024/05/10/compress/banner-sale-off-tissot_1715333668.webp"
                                        class="d-block w-100 banner-image" alt="Bonest Gatti Sale 35%">
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            </div>
            <!-- Shop End -->

            <script>
                var carouselElement = document.getElementById('bannerCarousel');
                var carousel = new bootstrap.Carousel(carouselElement, {
                    interval: 3000, // Thay đổi slide mỗi 3 giây
                    ride: 'carousel'
                });
                var currentPage = ${ model.page };
                var limit = ${ model.limit };
                var sortBy = "${model.sortBy}";
                var sortName = "${model.sortName}";
                var keyword = "${model.keyword}";
                var filter = "${model.filter}";
                var gender = "${model.gender}";
                let totalPages = ${model.totalPage};

                $('#pagination').twbsPagination({
                    totalPages: totalPages,
                    visiblePages: 10,
                    startPage: currentPage,
                    onPageClick: function (event, page) {
                        if (currentPage !== page) {
                            $('#limit').val(limit);
                            $('#page').val(page);
                            if (sortBy && sortName) {
                                $('#sortName').val(sortName);
                                $('#sortBy').val(sortBy);
                            }
                            if (keyword) {
                                $('#keyword').val(keyword);
                            }
                            if (filter) {
                                $('#filter').val(filter);
                            }
                            if (gender) {
                                $('#gender').val(gender);
                            }

                            $('#formSubmit').submit();
                        }
                    }
                });



                    // Đoạn mã bạn muốn thực hiện khi tài liệu đã sẵn sàng
                    $("#clearAllFilter").click(function () {
                        // Xóa các giá trị bộ lọc
                        $("#filter").val("");

                        // Tải lại trang hoặc thực hiện hành động khác
                        window.location.href = "<c:url value='/shop?page=1&limit=8'/>"; // Hoặc bất kỳ URL nào
                    });

                $("button[id^='btn-']").click(function () {
                    var filterToRemove = $(this).attr('id').replace('btn-', ''); // Lấy filter từ id

                    // Lấy các bộ lọc hiện tại
                    var currentFilters = $("#filter").val().split(",").filter(function (filter) {
                        return filter !== filterToRemove; // Loại bỏ bộ lọc cần xóa
                    });

                    // Cập nhật giá trị bộ lọc
                    $("#filter").val(currentFilters.join(","));

                    // Cập nhật URL và tải lại trang
                    var newFilters = currentFilters.join(",");
                    window.location.href = "<c:url value='/shop?page=1&limit=8&filter='/>" + newFilters; // Cập nhật URL với bộ lọc mới
                });
            </script>
            <script>
                document.querySelectorAll('#price').forEach(element => {
                    let price = parseInt(element.innerText.replace("đ", ""), 10);
                    element.innerText = price.toLocaleString("vi-VN") + "đ";
                });

            </script>
        </body>

        </html>