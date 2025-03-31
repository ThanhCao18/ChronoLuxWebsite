<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<meta charset="UTF-8">
<%@ include file="/common/taglib.jsp" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <title>ChronoLux - Quản lý đánh giá</title>
    <!-- Custom fonts for this template-->
    <link href="<c:url value='/template/admin/vendor/fontawesome-free/css/all.min.css'/>" rel="stylesheet"
          type="text/css">
    <link
            href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
            rel="stylesheet">
    <link href="<c:url value='/template/admin/css/sb-admin-2.min.css'/>" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/template/web/css/styles.css">
</head>
<body>
<!-- Begin Page Content -->
<div class="container-fluid">
    <div class="d-flex justify-content-between align-items-center my-4">
        <div class="d-flex align-items-center gap-2">
            <i class="fas fa-message" style="font-size: 28px"></i>
            <h1 class="h3 mb-0 text-gray-800 ml-3">Đánh giá sản phẩm ${productName}</h1>
        </div>
        <input type="hidden" name="page" value="${currentPage}">
        <input type="hidden" name="productId" value="${productId}">
        <div>
            <a href="${pageContext.request.contextPath}/admin/products?page=${currentPage}"
               class="btn btn-warning">
                <i class="fa fa-reply me-1" aria-hidden="true"></i>
                Quay lại
            </a>
        </div>
    </div>
    <hr/>
    <div class="row">
        <div class="col-md-3 border-right">
            <h6 class="mb-4">Tổng số đánh giá: ${totalComments}</h6>
            <div class="topbar-divider d-none d-sm-block"></div>
            <h6>Số điểm đánh giá trung bình: <fmt:formatNumber value="${avgRating}" type="number" minFractionDigits="1" maxFractionDigits="1"/></h6>
        </div>
        <div class="col-md-7">
            <div class="container comment-section">
                <c:forEach var="item" items="${commentList}" varStatus="status">
                    <div class="media mb-4 position-relative">
                        <div class="card shadow-sm media-body overflow-auto px-3 py-3 position-relative"
                             style="max-height: 500px">
                            <div class="d-flex align-items-center mb-2 mt-3">
                                <c:choose>
                                    <c:when test="${not empty item.imgUrl}">
                                        <c:choose>
                                            <c:when test="${item.imgUrl.startsWith('http')}">
                                                <img src="${item.imgUrl}" alt="Ảnh Facebook"
                                                     class="rounded-circle me-2 user-avatar">
                                            </c:when>
                                            <c:otherwise>
                                                <img src="<c:url value='/template/web/img/user-logos/${item.imgUrl}'/>"
                                                     alt="Ảnh người dùng" class="rounded-circle me-2 user-avatar">
                                            </c:otherwise>
                                        </c:choose>
                                    </c:when>
                                    <c:otherwise>
                                        <img src="<c:url value='/template/web/img/user-logos/user.png'/>"
                                             alt="Ảnh mặc định" class="rounded-circle me-2 user-avatar">
                                    </c:otherwise>
                                </c:choose>
                                <div>
                                    <h6 class="mb-1 ml-2">${item.name}
                                        <small class="text-muted"> -
                                            <fmt:formatDate value="${item.createdDate}" pattern="dd/MM/yyyy HH:mm"/>
                                        </small>
                                    </h6>
                                    <div class="text-warning mb-2 ml-2">
                                        <c:forEach begin="1" end="${item.rating}">
                                            <i class="fas fa-star"></i>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>

                            <!-- Nội dung bình luận -->
                            <p>${item.review}</p>


                            <!-- Ảnh đánh giá -->
                            <c:if test="${not empty item.imgReviewUrl}">
                                <div class="text-center">
                                    <img class="review-image mb-2"
                                         src="<c:url value='/comment/image/${item.id}'/>"
                                         alt="Ảnh đánh giá"
                                         onclick="openImageModal(this.src)">
                                </div>
                            </c:if>
                            <div id="imageModal" class="modal" onclick="closeImageModal()">
                                <span class="close">&times;</span>
                                <img class="modal-content" id="modalImage">
                            </div>
                            <!-- Số lượt thích -->
                            <div>
                                <strong>Lượt thích:</strong>
                                <span id="like-count-${item.id}" class="ms-2">${item.likeCount}</span>
                            </div>

                            <hr>

                            <!-- Nút Like -->
                            <div>
                                <c:set var="liked" value="${isLike[status.index]}"/>
                                <button class="like-btn ${liked ? 'liked' : ''}"
                                        onclick="likeComment(this, ${item.id})"
                                        data-first-click="${liked}">
                                    <i class="${liked ? 'fas fa-thumbs-up' : 'fa-regular fa-thumbs-up'}"></i>
                                    <span>${liked ? 'Đã thích' : 'Thích'}</span>
                                </button>
                            </div>

                            <!-- Nút xóa -->
                            <div class="position-absolute delete-button">
                                <button type="button" class="btn-circle btn-danger" data-toggle="modal"
                                        data-target="#confirmModal${item.id}">
                                    <i class="fas fa-trash-alt"></i>
                                </button>
                            </div>
                            <!-- Modal xác nhận xóa -->
                            <div class="modal fade" id="confirmModal${item.id}" tabindex="-1" role="dialog"
                                 aria-labelledby="confirmModalLabel${item.id}" aria-hidden="true">
                                <div class="modal-dialog modal-dialog-centered" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="confirmModalLabel${item.id}">XÁC NHẬN XÓA</h5>
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>
                                        <div class="modal-body">
                                            Bạn có chắc muốn xóa đánh giá này không?
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-dismiss="modal">
                                                <i class="fa-solid fa-reply mr-1"></i> Trở lại
                                            </button>
                                            <a href="${pageContext.request.contextPath}/admin/comment/delete?id=${item.id}&productId=${productId}&currentPage=${currentPage}"
                                               class="btn btn-danger">
                                                <i class="fa-solid fa-check mr-1"></i> Xác nhận xóa
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>

<script>
    function openImageModal(src) {
        let modal = document.getElementById("imageModal");
        let modalImg = document.getElementById("modalImage");

        modal.style.display = "flex"; // Hiển thị modal
        modalImg.src = src; // Gán ảnh vào modal
    }

    function closeImageModal() {
        document.getElementById("imageModal").style.display = "none";
    }

</script>
<script>
    document.addEventListener("DOMContentLoaded", function () {
        const successMessage = "${successMessage}";
        if (successMessage) {
            const toastElement = document.getElementById("successToast");
            const toast = new bootstrap.Toast(toastElement);
            document.getElementById("toastMessage").innerText = successMessage;
            toast.show();
        }
    });
</script>
<script>
    function likeComment(button, commentId) {
        let isLiked = button.classList.contains("liked");

        if (isLiked) {
            button.classList.remove("liked");
            button.innerHTML = '<i class="fa-regular fa-thumbs-up like-icon"></i> <span>Thích</span>';
        } else {
            button.classList.add("liked");
            button.innerHTML = '<i class="fas fa-thumbs-up like-icon"></i> <span>Đã thích</span>';
        }

        // Gọi API like/unlike
        fetch('/ChronoLuxWeb/comment/like/' + commentId, {
            method: 'PUT'
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    document.getElementById("like-count-" + commentId).textContent = data.likeCount;
                }
            })
            .catch(error => console.error('Error:', error));
    }

</script>
</body>