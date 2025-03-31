<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglib.jsp" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <title>ChronoLux - Review Payment</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">

       <link href="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js" rel="stylesheet">


                  <!-- Font Awesome -->
                           <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
                    <!-- Thêm Bootstrap JS (tùy chọn) -->
                    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
                <!-- Customized Bootstrap Stylesheet -->
                <link href=" <c:url value='/template/web/cssviewprofile/style.css'/>" rel="stylesheet">
                <link href=" <c:url value='/template/web/css/style.css'/>" rel="stylesheet">
    <style>
        .review-container {
            border: 1px solid #dee2e6;
            border-radius: 10px;
            padding: 30px;
            background-color: #f8f9fa;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        .section-title1 {
            font-weight: bold;
            color: #343a40;
            border-bottom: 2px solid #007bff;
            padding-bottom: 5px;
            margin-bottom: 15px;
            font-size: 1.2em;
        }
        .details-row {
            display: flex;
            justify-content: space-between;
            margin-bottom: 10px;
        }
        .details-label {
            font-weight: bold;
            color: #495057;
        }
        .text-success {
            color: green;
        }
        .items-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
        }
        .items-table th, .items-table td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        .items-table th {
            background-color: #f2f2f2;
            font-weight: bold;
        }
        .pay-now-btn {
            background-color: #007bff;
            border-color: #007bff;
            color: #fff;
            font-size: 1.2em;
            padding: 10px 20px;
            border-radius: 5px;
        }
        .pay-now-btn:hover {
            background-color: #0056b3;
        }
    </style>
</head>

<body>
    <div class="container mt-5">
        <div class="text-center mb-4">
            <h2 class="text-danger">Please Review Before Paying</h2>
        </div>
        <form action="<c:url value='/execute_payment'/>" method="post">

            <div class="review-container">
                <!-- Transaction Details -->
                <div class="section-title1">Transaction Details</div>
                <div class="details-row">
                    <span class="details-label">Description:</span>
                    <span>${transaction.description}</span>
                </div>

                <!-- Items Table -->
                <div class="section-title1 mt-4">Items</div>
                <table class="items-table">
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Quantity</th>
                            <th>Price</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="item" items="${transaction.itemList.items}">
                            <tr>
                                <td>${item.name}</td>
                                <td>${item.quantity}</td>
                                <td>$${item.price}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <div class="details-row mt-3">
                    <span class="details-label">Subtotal:</span>
                    <span>$${transaction.amount.details.subtotal}</span>
                </div>

                <div class="details-row">
                    <span class="details-label">Total:</span>
                    <span class="text-success">$${transaction.amount.total}</span>
                </div>

                <!-- Payer Information -->
                <div class="section-title1 mt-4">Payer Information</div>
                <div class="details-row">
                    <span class="details-label">First Name:</span>
                    <span>${payer.firstName}</span>
                </div>
                <div class="details-row">
                    <span class="details-label">Last Name:</span>
                    <span>${payer.lastName}</span>
                </div>
                <div class="details-row">
                    <span class="details-label">Email:</span>
                    <span>${payer.email}</span>
                </div>
                <div class="details-row">
                    <span class="details-label">Phone:</span>
                    <span>${payer.phone}</span>
                </div>

                <!-- Shipping Address -->
                <div class="section-title1 mt-4">Shipping Address</div>
                <div class="details-row">
                    <span class="details-label">Recipient Name:</span>
                    <span>${shippingAddress.recipientName}</span>
                </div>
                <div class="details-row">
                    <span class="details-label">Line 1:</span>
                    <span>${shippingAddress.line1}</span>
                </div>
                <div class="details-row">
                    <span class="details-label">City:</span>
                    <span>${shippingAddress.city}</span>
                </div>
                <div class="details-row">
                    <span class="details-label">State:</span>
                    <span>${shippingAddress.state}</span>
                </div>
                <div class="details-row">
                    <span class="details-label">Country Code:</span>
                    <span>${shippingAddress.countryCode}</span>
                </div>
                <div class="details-row">
                    <span class="details-label">Postal Code:</span>
                    <span>${shippingAddress.postalCode}</span>
                </div>

                <input type="hidden" name="PayerID" value="${payerId}">
                <input type="hidden" name="paymentId" value="${paymentId}">

                <!-- Pay Now Button -->
                <div class="text-center mt-4">
                    <button type="submit" class="btn pay-now-btn">Pay Now</button>
                </div>
            </div>
        </form>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>

</html>
