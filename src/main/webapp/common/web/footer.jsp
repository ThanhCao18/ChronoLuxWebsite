<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/template/web/css/chatbot.css">

<!-- Footer Start -->
<div class="container-fluid text-dark pt-4" style="border-top: 2px solid;border-top-color: rgb(234,189,43)">
    <div class="row px-xl-5 pt-3">
        <div class="col-lg-4 col-md-12 pr-3 pr-xl-5v d-flex justify-content-center align-items-center">
            <a href="#" class="text-decoration-none">
                <img id="corporation-img" src="" style="margin-bottom: 10px;" alt="ChronoLux Logo">
            </a>
        </div>
        <div class="col-lg-8 col-md-12 py-1" style="max-width: 60%">
            <div class="info-part d-flex justify-content-end ">
                <div class="info-container">
                    <p class="mb-2"><i class="fa fa-map-marker-alt mr-4"
                                       style="font-size: 30px;color: rgb(234,189,43)"></i><span
                            id="corporation-address"></span></p>
                    <p class="mb-2"><i class="fa fa-envelope mr-3"
                                       style="font-size: 30px;color: rgb(234,189,43)"></i><span
                            id="corporation-email"></span></p>
                    <p class="mb-0"><i class="fa fa-phone-alt mr-3"
                                       style="font-size: 30px;color: rgb(234,189,43)"></i><span
                            id="corporation-phone"></span></p>
                    <img class="w-60 h-60 " style="max-height: 450px; max-width: 300px; padding-top: 10px"
                         src="<c:url value='/template/web/img/BCT.jpg'/>"
                         alt="Image">
                </div>
            </div>
        </div>
        <p class="info-comp text-truncate text-center mt-2" id="corporation-about" style="width: 100%"></p>
    </div>
    <div class="row border-top border-dark mx-xl-5 mt-1">
        <div class="col-md-6 px-xl-0">
            <p class="mb-md-0 text-center text-md-left text-dark">
                &copy; Copyright 2010-2024 <a class="text-dark font-weight-semi-bold" href="#">ChronoLux Company
                S.L. </a> All rights reserved.
            </p>
        </div>
        <div class="col-md-6 px-xl-0 text-center text-md-right">
            <img class="img-fluid" src="img/payments.png" alt="">
        </div>
    </div>
</div>
<!-- Footer End -->

<%--<!-- Bong bóng chat -->
<div id="chat-bubble">
    <img src="https://cdn-icons-png.flaticon.com/512/2335/2335279.png" alt="Chat Icon">
</div>

<!-- Cửa sổ chat -->
<div id="chat-box">
    <div id="chat-header">
        <span>Chat với Admin</span>
        <i id="chat-close" class="fa-solid fa-x"></i>
    </div>
    <div id="chat-messages"></div>
    <div id="chat-input">
        <div id="preview-container"></div>
        <input type="text" id="message-input" placeholder="Nhập tin nhắn...">
        <i id="send-button" class="fa-solid fa-paper-plane"></i>
    </div>
</div>--%>

<div class="modal fade" id="loginModal" tabindex="-1" aria-labelledby="loginModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="loginModalLabel">Thông báo</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        Bạn cần đăng nhập để sử dụng tính năng chat.
      </div>
      <div class="modal-footer">
        <a href="${pageContext.request.contextPath}/login" class="btn btn-primary">Đăng nhập</a>
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
      </div>

    </div>
  </div>
</div>


<%--<script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.2/sockjs.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
<script src="${pageContext.request.contextPath}/template/web/js/notification.js"></script>--%>


<%--<script>
    // Kết nối WebSocket
    let stompClient = null;
    let userId = null;

    fetch("/ChronoLux/userId", {
        method: "GET",
        headers: {
            "Content-Type": "application/json"
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to fetch user ID");
            }
            return response.json();
        })
        .then(data => {
            userId = data;
        })
        .catch(error => {
            console.error("Error fetching user ID:", error);
        });

    function connect() {
        let socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            stompClient.subscribe('/topic/messages', function (message) {
                showMessage(JSON.parse(message.body));
            });
        });
    }

    // Xử lý hiển thị chat box
    document.getElementById("chat-bubble").addEventListener("click", function () {
        fetch("/ChronoLuxWeb/check")
            .then(response => response.json())
            .then(data => {
                if (data.authenticated) {
                    document.getElementById("chat-box").style.display = "flex";
                } else {
                    showLoginModal();
                }
            })
            .catch(error => console.error("Error checking authentication:", error));
    });

    // Đóng chat box
    document.getElementById("chat-close").addEventListener("click", function () {
        document.getElementById("chat-box").style.display = "none";
        document.getElementById("chat-bubble").style.display = "flex";
    });

    ///////
    let pastedImage = null; // Lưu ảnh đã dán

    document.getElementById("message-input").addEventListener("paste", function (event) {
        let items = (event.clipboardData || event.originalEvent.clipboardData).items;

        for (let item of items) {
            if (item.kind === 'file' && item.type.startsWith('image/')) {
                let file = item.getAsFile();
                let reader = new FileReader();

                reader.onload = function (e) {
                    pastedImage = e.target.result; // Lưu base64 của ảnh

                    let previewContainer = document.getElementById("preview-container");
                    previewContainer.innerHTML = ""; // Xóa ảnh cũ (nếu có)

                    let imgElement = document.createElement("img");
                    imgElement.src = pastedImage;

                    let removeBtn = document.createElement("button");
                    removeBtn.id = "remove-preview";
                    removeBtn.textContent = "X";
                    removeBtn.addEventListener("click", function () {
                        pastedImage = null;
                        previewContainer.innerHTML = "";
                    });

                    previewContainer.appendChild(imgElement);
                    previewContainer.appendChild(removeBtn);
                };

                reader.readAsDataURL(file);
            }
        }
    });

    // Hàm gửi tin nhắn
    function sendMessage() {
        let input = document.getElementById("message-input");
        let messageText = input.value.trim();

        let chatMessages = document.getElementById("chat-messages");

        if (!messageText && !pastedImage) return; // Không gửi nếu không có gì

        // Gửi text trước nếu có
        if (messageText) {
            let textMessage = document.createElement("div");
            textMessage.classList.add("message", "user-message");
            textMessage.textContent = messageText;

            chatMessages.appendChild(textMessage);
            chatMessages.scrollTop = chatMessages.scrollHeight;

            //send mess
            stompClient.send("/app/chat", {}, JSON.stringify({
                senderId: userId,
                recipientId: "11",
                content: messageText
            }));
        }

        // Gửi ảnh riêng nếu có
        if (pastedImage) {
            let imageMessage = document.createElement("div");
            imageMessage.classList.add("message", "user-message");

            let imgElement = document.createElement("img");
            imgElement.src = pastedImage;
            imgElement.style.maxWidth = "200px";
            imgElement.style.borderRadius = "10px";
            imgElement.style.marginTop = "5px";

            imageMessage.appendChild(imgElement);
            chatMessages.appendChild(imageMessage);
            chatMessages.scrollTop = chatMessages.scrollHeight;
        }

        // Xóa nội dung sau khi gửi
        input.value = "";
        input.style.height = "40px"; // Reset chiều cao input
        pastedImage = null;
        document.getElementById("preview-container").innerHTML = "";
    }


    // Xử lý khi nhấn nút gửi
    document.getElementById("send-button").addEventListener("click", sendMessage);

    document.getElementById("message-input").addEventListener("keydown", function (event) {
        if (event.key === "Enter") {
            if (event.shiftKey) {
                // Shift + Enter: Xuống dòng
                event.stopPropagation(); // Ngăn không gửi tin nhắn
            } else {
                // Chỉ Enter: Gửi tin nhắn
                event.preventDefault();
                sendMessage();
            }
        }
    });

    // Tự động mở rộng input khi nhập nhiều dòng
    document.getElementById("message-input").addEventListener("input", function () {
        this.style.height = "40px"; // Reset trước khi thay đổi
        this.style.height = Math.min(this.scrollHeight, 100) + "px"; // Giới hạn tối đa 100px
    })

    function showLoginModal() {
        var myModal = new bootstrap.Modal(document.getElementById("loginModal"), {});
        myModal.show();
    }

</script>--%>
<%--<div id="rasa-chat-widget" data-websocket-url="http://localhost:5005"></div>
<script src="https://unpkg.com/@rasahq/rasa-chat" type="application/javascript"></script>--%>


<%--<script src="https://cdn.jsdelivr.net/npm/rasa-webchat@1.0.1/lib/index.js"></script>

<script>
    window.onload = function () {
    WebChat.default({
        title: "ChronoLux Bot",
        subtitle: "Hỗ trợ khách hàng",
        initPayload: "/greet",
        customData: { language: "vi" },
        socketUrl: "http://localhost:5005",
        socketPath: "/socket.io/",
        showMessageDate: true,
        params: {
                storage: "session",
                avatar: {
                    bot: "<%= request.getContextPath() %>/template/web/css/gpt.png",
                    user: "https://your-user-avatar-url.png"  // Ảnh user
                }
            }
    }, null);
}

</script>--%>

<script>
    localStorage.removeItem("chat_session");
    !(function () {

    let e = document.createElement("script"),
        t = document.head || document.getElementsByTagName("head")[0];
    (e.src =
        "https://cdn.jsdelivr.net/npm/rasa-webchat/lib/index.js"),
        (e.async = !0),
        (e.onload = () => {
            window.WebChat.default(
                {
                    customData: {language: "vi"},
                    socketUrl: "http://localhost:5005/",
                    title: 'ChronoLux AI',
                    subtitle: 'Trợ lý ảo',
                    profileAvatar: "<%= request.getContextPath() %>/template/web/css/Logo1.png",
                    openLauncherImage: "<%= request.getContextPath() %>/template/web/css/Logo1.png",
                    /*closeImage: "./assets/svg/down.svg",*/
                    storage: "session",
                    showCloseButton: true,
                    embedded: false,
                    customMessageDelay: (message) => {
                        let delay = message.length * 40;
                        if (delay > 2 * 1000) delay = 2 * 1000;
                        if (delay < 800) delay = 800;
                        return delay;
                    }
                    // add other props here
                },
                null
            );
        }),
        t.insertBefore(e, t.firstChild);
})();

</script>

<a href="#" class="btn btn-primary back-to-top"><i class="fa fa-angle-double-up"></i></a>
<script>
    $(document).ready(function () {
        $.ajax({
            url: "<c:url value='/coporation'/>",
            method: 'GET',
            success: function (data) {
                // Cập nhật nội dung của các phần tử HTML với dữ liệu từ API
                $("#corporation-img").attr("src", `<c:url value='/template/web/img/corporation/'/>` + data.img);
                $("#corporation-about").text(data.about);
                $("#corporation-address").text(data.address);
                $("#corporation-email").text(data.email);
                $("#corporation-phone").text(data.phone);
                console.log("sdsdd");
            },
            error: function (xhr, status, error) {

            }
        });
    });
</script>