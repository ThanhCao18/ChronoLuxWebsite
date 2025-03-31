document.addEventListener("DOMContentLoaded", function () {
    fetch('/ChronoLuxWeb/admin/notifications')
        .then(response => response.json())
        .then(data => loadNotifications(data))
        .catch(error => console.error("Lỗi khi lấy thông báo:", error));
});

function formatTimeAgo(dateString) {
    let date = new Date(dateString);
    let now = new Date();
    let diffMs = now - date;
    let diffSec = Math.floor(diffMs / 1000);
    let diffMin = Math.floor(diffSec / 60);
    let diffHour = Math.floor(diffMin / 60);
    let diffDay = Math.floor(diffHour / 24);

    if (diffSec < 60) return "Vừa xong";
    if (diffMin < 60) return `${diffMin} phút trước`;
    if (diffHour < 24) return `${diffHour} giờ trước`;
    return `${diffDay} ngày trước`;
}

function loadNotifications(notifications) {
    var contextPath = "${pageContext.request.contextPath}";
    var currentPage = "${param.currentPage != null ? param.currentPage : '1'}";

    let badge = document.querySelector('.badge-counter');
    let notificationList = document.getElementById('notificationList');

    if (!badge || !notificationList) return;

    notificationList.innerHTML = "";

    if (notifications.length > 0) {
        let unreadCount = notifications.filter(noti => noti.status === "Unread").length;
        badge.innerText = unreadCount > 0 ? unreadCount : "";
        badge.style.display = unreadCount > 0 ? "inline-block" : "none";

        notifications.forEach(notification => {
            let notiClass = notification.status === "Unread" ? "font-weight-bold" : "";
            let timeAgo = formatTimeAgo(notification.createdDate); // Xử lý thời gian
            let newNoti = `
                <a class="dropdown-item d-flex align-items-center" href="/ChronoLuxWeb/admin/comment/view?id=${notification.productId}&currentPage=1">
                    <div class="mr-3">
                        <img src="${notification.avatarUrl}" class="rounded-circle" width="40px" height="40px" alt="Avatar">
                    </div>
                    <div>
                        <div class="text-gray-500">${notification.title}</div>
                        <div class="small text-gray-400">${timeAgo}</div> <!-- Thêm thời gian -->
                    </div>
                </a>
            `;
            notificationList.insertAdjacentHTML('beforeend', newNoti);
        });
    } else {
        badge.innerText = "";
        badge.style.display = "none";
        notificationList.innerHTML = '<span class="dropdown-item text-center text-gray-500">Chưa có thông báo</span>';
    }
}


document.addEventListener("DOMContentLoaded", function () {
    let alertsDropdown = document.getElementById('alertsDropdown');
    if (alertsDropdown) {
        alertsDropdown.addEventListener('click', function () {
            fetch('/ChronoLuxWeb/admin/notifications/mark-notifications-read', {method: 'POST'})
                .then(() => fetch('/ChronoLuxWeb/admin/notifications'))
                .then(response => response.json())
                .then(data => {
                    loadNotifications(data);
                    let badge = document.querySelector('.badge-counter');
                    if (badge) {
                        badge.innerText = "";
                        badge.style.display = "none";
                    }
                })
                .catch(error => console.error("Lỗi khi đánh dấu thông báo đã đọc:", error));
        });
    }
});

var socket = new SockJS('/ChronoLuxWeb/ws');
var stompClient = Stomp.over(socket);

stompClient.connect({}, function (frame) {
    console.log('Connected to WebSocket');
    stompClient.subscribe('/topic/adminNotifications', function (message) {
        let newNotification = JSON.parse(message.body);
        let notificationList = document.getElementById('notificationList');
        if (!notificationList) return;

        let emptyMessage = notificationList.querySelector('.text-center.text-gray-500');
        if (emptyMessage) emptyMessage.remove();

        let timeAgo = formatTimeAgo(newNotification.createdDate);
        let newNoti = `
        <a class="dropdown-item d-flex align-items-center" href="#">
            <div class="mr-3">
                <img src="${newNotification.avatarUrl}" class="rounded-circle" width="40px" height="40px" alt="Avatar">
            </div>
            <div>
                <div class="text-gray-500">${newNotification.title}</div>
                <div class="small text-gray-400">${timeAgo}</div> <!-- Hiển thị thời gian -->
            </div>
        </a>
    `;
        notificationList.insertAdjacentHTML('afterbegin', newNoti);

        let badge = document.querySelector('.badge-counter');
        if (badge) {
            let currentUnread = parseInt(badge.innerText) || 0;
            badge.innerText = currentUnread + 1;
            badge.style.display = "inline-block";
        }
    });

});
