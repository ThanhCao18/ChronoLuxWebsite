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
     .card1-container {
         display: flex;
         gap: 20px; /* Khoảng cách giữa các thẻ */
     }
     .card1 {
         background-color: #fff;
         border-radius: 10px;
         box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
         width: 170px;
         height: 120px;
         text-align: center;
         padding: 20px;
         transition: transform 0.2s;
         margin-left: 20px; /* Khoảng cách xung quanh từng thẻ */
     }
     .card1:hover {
         transform: scale(1.05);
     }
     .card1 img {
         width: 100%;
         height: 100%;

         border-radius: 8px;
     }
     .card1-title {
         font-size: 15px;
         font-weight: bold;
         margin-top: 10px;
         color: #007BFF;
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
      .disabled-link {
                             pointer-events: none;
                             opacity: 0.5;
                             cursor: default;
                         }
</style>

        </head>
        <body>
        <div class="link-container py-3 pl-lg-5 pl-5 mb-3" style=" border-bottom: 2px solid; border-color: rgb(234,189,43)">
                   <a href="<c:url value='/home'/>" class="home-link">
                       <i class="fas fa-home"></i> Trang chủ
                   </a>
                   >
                   <a href="<c:url value='/shop/brand?id=${idBrand}&page=1&limit=4'/>" class="about-link">
                         ${brand.name}
                   </a>
                   >
                   <a href="<c:url value='/shop/brand/product-line?idBrand=${idBrand}&id=${productLine.id}&page=1&limit=4'/>" class="about-link">
                         ${productLine.name}
                   </a>
               </div>
            <!-- Page Header Start -->
        <div class="container-fluid bg-light mb-5 px-0">
            <div class="d-flex flex-column align-items-center justify-content-center" style="min-height: 300px">
                <img src="<c:url value='/template/web/img/product-lines/${productLine.bannerUrl}'/>" alt="Our Shop" style="max-height: 300px; max-width: 100%; object-fit: cover;">
            </div>
        </div>
<!--banner here-->
        <div class="container-fluid pt-1 pb-3 mb-4">
            <div class="row px-xl-5 pb-3 d-flex justify-content-center align-items-center flex-wrap" style="height: 100px">
                <c:forEach var="item" items="${productLine.listResult}">
                    <div class="card1-container">
                        <div class="card1" style="height: 100%" >
                            <a href="<c:url value='/shop/brand/product-line?idBrand=${idBrand}&id=${item.id}&page=1&limit=8'/>"
                               class="img position-relative overflow-hidden mb-3">
                                <img class="img" style="height: 60%; width: fit-content; max-width: 100%" src="<c:url value='/template/web/img/product-lines/${item.iconUrl}'/>" alt="">
                                <div class="card1-title" style="color: black">${item.name}</div>
                            </a>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
            <!-- Page Header End -->
            <div class="col-12 pb-1 pt-3">
               <c:if test="${not empty model.filter}">
                  <div class="row mb-4" style="margin-left: 20px;"> <!-- Dịch sang trái một chút -->
                          <div class="col-12">
                              <span class="font-weight-bold">Đã chọn:</span>

                              <!-- Tách filter thành mảng -->
                              <c:set var="filterArray" value="${fn:split(model.filter, ',')}"/>

                              <!-- Duyệt qua từng phần tử trong mảng và tạo button -->
                              <c:forEach var="filterItem" items="${filterArray}">
                               <c:if test="${priceFilters[filterItem] != null}">
                                  <button id="btn-${filterItem}" type="button"  class="btn btn-primary ml-2">
                                      ${priceFilters[filterItem]} <!-- Lấy giá trị từ Map dựa trên từng phần tử trong filter -->
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
                <span class="font-weight-bold mr-2">Phổ biến:</span>
               <c:choose>
                  <c:when test="${not empty model.filter}">
                      <c:set var="currentFilters" value="${fn:split(model.filter, ',')}" />
                      <c:forEach items="${priceFilters.entrySet()}" var="entry">
                          <c:set var="filterKey" value="${entry.key}" />
                          <c:set var="filterValue" value="${entry.value}" />

                          <!-- Kiểm tra nếu đã chọn giá rồi thì không cho chọn thêm giá nữa -->
                          <c:choose>
                              <c:when test="${(fn:contains(model.filter, 'duoi-1-trieu') or fn:contains(model.filter, 'tu-1-3-trieu') or fn:contains(model.filter, 'tu-3-6-trieu') or fn:contains(model.filter, 'tu-6-9-trieu') or fn:contains(model.filter, 'tren-9-trieu'))
                                              and (filterKey == 'duoi-1-trieu' or filterKey == 'tu-1-3-trieu' or filterKey == 'tu-3-6-trieu' or filterKey == 'tu-6-9-trieu' or filterKey == 'tren-9-trieu')}">
                                  <!-- Nếu đã chọn giá, hiện thông báo khi người dùng cố gắng chọn thêm giá -->
                                  <a href="javascript:void(0);" class="btn btn-outline-danger btn-sm rounded-pill mx-1"  style="border: 2px solid" onclick="alert('Bạn đã chọn một mức giá. Không thể chọn thêm!');">
                                      ${filterValue}
                                  </a>
                              </c:when>
                              <c:otherwise>
                                  <!-- Nếu chưa chọn giá hoặc là bộ lọc giới tính thì cho phép chọn -->
                                  <a href="<c:url value='/shop/brand/product-line?idBrand=${idBrand}&id=${model.id}&page=1&limit=${model.limit}&filter=${model.filter},${filterKey}'/>" style="border: 2px solid"
                                     class="btn btn-outline-primary btn-sm rounded-pill mx-1">${filterValue}</a>
                              </c:otherwise>
                          </c:choose>
                      </c:forEach>
                  </c:when>



                   <c:otherwise>
                       <a href="<c:url value='/shop/brand/product-line?idBrand=${idBrand}&id=${model.id}&page=1&limit=${model.limit}&filter=nam'/>" style="border: 2px solid" class="btn btn-outline-primary btn-sm rounded-pill mx-1">Nam</a>
                       <a href="<c:url value='/shop/brand/product-line?idBrand=${idBrand}&id=${model.id}&page=1&limit=${model.limit}&filter=nu'/>" style="border: 2px solid" class="btn btn-outline-primary btn-sm rounded-pill mx-1">Nữ</a>
                       <a href="<c:url value='/shop/brand/product-line?idBrand=${idBrand}&id=${model.id}&page=1&limit=${model.limit}&filter=duoi-1-trieu'/>" style="border: 2px solid" class="btn btn-outline-primary btn-sm rounded-pill mx-1">Dưới 1 triệu</a>
                       <a href="<c:url value='/shop/brand/product-line?idBrand=${idBrand}&id=${model.id}&page=1&limit=${model.limit}&filter=tu-1-3-trieu'/>" style="border: 2px solid" class="btn btn-outline-primary btn-sm rounded-pill mx-1">Từ 1 - 3 triệu</a>
                       <a href="<c:url value='/shop/brand/product-line?idBrand=${idBrand}&id=${model.id}&page=1&limit=${model.limit}&filter=tu-3-6-trieu'/>" style="border: 2px solid" class="btn btn-outline-primary btn-sm rounded-pill mx-1">Từ 3 - 6 triệu</a>
                       <a href="<c:url value='/shop/brand/product-line?idBrand=${idBrand}&id=${model.id}&page=1&limit=${model.limit}&filter=tu-6-9-trieu'/>" style="border: 2px solid" class="btn btn-outline-primary btn-sm rounded-pill mx-1">Từ 6 - 9 triệu</a>
                       <a href="<c:url value='/shop/brand/product-line?idBrand=${idBrand}&id=${model.id}&page=1&limit=${model.limit}&filter=tren-9-trieu'/>" style="border: 2px solid" class="btn btn-outline-primary btn-sm rounded-pill mx-1">Trên 9 triệu</a>
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
                            <form action="<c:url value='/shop/brand/product-line'/>" method="get" class="d-flex w-auto" style="border: 2px solid; border-color: rgb(234,189,43)">
                                <div class="input-group" style="width: 250px;"> <!-- Set width of the search bar -->
                                    <input type="text" class="form-control" placeholder="Tìm Kiếm" name="keyword"  style="border-right: 2px solid; border-right-color: rgb(234,189,43)">
                                    <input type="hidden" value="1" name="page">
                                    <input type="hidden" value="8" name="limit">
                                    <input type="hidden" value="${model.id}" name="id">
                                    <input type="hidden" value="${idBrand}" name="idBrand">
                                    <div class="input-group-append">
                                        <span class="input-group-text bg-transparent text-primary">
                                            <i class="fa fa-search"></i>
                                        </span>
                                    </div>
                                </div>
                            </form>

                            <!-- Filter -->
                            <div class="dropdown ml-3">
                                <button class="btn dropdown-toggle" type="button" id="triggerId"
                                        data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" style="border: 2px solid; border-color: rgb(234,189,43)" >
                                    Bộ Lọc
                                </button>
                                <div class="dropdown-menu dropdown-menu-right" aria-labelledby="triggerId">
                                    <a class="dropdown-item"
                                        href="<c:url value='/shop/brand/product-line?idBrand=${idBrand}&id=${model.id}&page=${model.page}&limit=${model.limit}&sortName=name&sortBy=asc&keyword=${model.keyword}&filter=${model.filter}'/>">
                                        <i class="fas fa-sort-alpha-down"></i> Theo tên: A-Z
                                    </a>
                                    <a class="dropdown-item"
                                        href="<c:url value='/shop/brand/product-line?idBrand=${idBrand}&id=${model.id}&page=${model.page}&limit=${model.limit}&sortName=price&sortBy=asc&keyword=${model.keyword}&filter=${model.filter}'/>">
                                        <i class="fas fa-arrow-up"></i> Theo giá: Tăng dần
                                    </a>
                                    <a class="dropdown-item"
                                        href="<c:url value='/shop/brand/product-line?idBrand=${idBrand}&id=${model.id}&page=${model.page}&limit=${model.limit}&sortName=price&sortBy=desc&keyword=${model.keyword}&filter=${model.filter}'/>">
                                        <i class="fas fa-arrow-down"></i> Theo giá: Giảm dần
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <form action="<c:url value='/shop/brand/product-line'/>" id="formSubmit" method="get">
                    <div class="row px-xl-5">
                        <!-- Shop Product Start -->
                        <div class="col-lg-12 col-md-12">
                            <div class="row pb-3 d-flex justify-content-center align-items-center flex-wrap">
                                <c:forEach var="product" items="${products}">
                                    <div class="product-card p-2 bg-white shadow mx-3 my-3 " style="border-radius: 1rem; max-width: 300px">
                                        <div class="card product-item border-0 mb-4">
                                                <c:if test="${product.stock <= 0}">
                                                     <span class="try-on-label">Bán Hết</span>
                                                </c:if>
                                            <img class="img-fluid p-5" style="width: fit-content;"
                                                    src="<c:url value='/template/web/img/products/${product.imgUrl}'/>" alt="">
                                            <div class="card-body text-center p-0 pt-4 pb-3">
                                                <h6 class="text-truncate mb-3">${product.name}</h6>
                                                <div class="d-flex justify-content-center">
                                                    <h6 id="price">${product.price}</h6>
                                                </div>
                                            </div>
                                            <div class="button-container p-0 my-1 mx-2 d-flex ">
                                                <a href="<c:url value='/product-detail?id=${product.id}'/>"
                                                   class="btn bg-dark text-light p-2 flex-grow-1" style="border-radius: 1rem">Chi Tiết</a>
                                            </div>
                                            <div class="button-container p-0 my-1 mx-2 d-flex ">
                                                <c:if test="${product.stock <= 0}">
                                                    <a href="<c:url value='/cart/add?productId=${product.id}&quantity=1'/>"
                                                       class="btn btn-sm text-dark p-2 flex-grow-1 disabled-link" style="border-radius: 1rem">
                                                        Đang tạm hết
                                                    </a>
                                                </c:if>
                                                <c:if test="${product.stock > 0}">
                                                    <a href="<c:url value='/cart/add?productId=${product.id}&quantity=1'/>"
                                                       class="btn bg-dark text-light p-2 flex-grow-1" style="border-radius: 1rem">Thêm vào giỏ</a>
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
                                    <input type="hidden" value="" id="id" name="id" />
                                    <input type="hidden" value="${idBrand}" id="idBrand" name="idBrand" />
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

            </div>
            <!-- Shop End -->

            <script>
                var id = ${model.id};

                var currentPage = ${ model.page };
                var limit = ${ model.limit };
                var sortBy = "${model.sortBy}";
                var sortName = "${model.sortName}";
                var keyword = "${model.keyword}";
                var filter = "${model.filter}"
                var gender = "${model.gender}"
                var totalPages = ${model.totalPage} === 0 ? 1 : ${model.totalPage};

                $('#pagination').twbsPagination({
                    totalPages: totalPages,
                    visiblePages: 10,
                    startPage: currentPage,
                    onPageClick: function (event, page) {
                        if (currentPage != page) {
                            $('#id').val(id);
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


                   $(document).ready(function () {
                       // Đoạn mã bạn muốn thực hiện khi tài liệu đã sẵn sàng
                           $("#clearAllFilter").click(function () {
                           // Xóa các giá trị bộ lọc
                           $("#filter").val("");

                           // Tải lại trang hoặc thực hiện hành động khác
                           window.location.href = "<c:url value='/shop/brand/product-line?idBrand=${idBrand}&id=${model.id}&page=1&limit=8'/>"; // Hoặc bất kỳ URL nào
                       });
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
                             window.location.href = "<c:url value='/shop/brand/product-line?idBrand=${idBrand}&id=${model.id}&page=1&limit=8&filter='/>" + newFilters; // Cập nhật URL với bộ lọc mới
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