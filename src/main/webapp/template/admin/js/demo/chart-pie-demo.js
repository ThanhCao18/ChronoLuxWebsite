
function updateChart(labels, data) {
  var ctx = document.getElementById("myPieChart");
  var myPieChart = new Chart(ctx, {
    type: 'doughnut',
    data: {
      labels: labels,
      datasets: [{
        data: data,
        backgroundColor: ['#e74a3b','#f6c23e', '#1cc88a', '#4e73df', '#36b9cc'],
        hoverBackgroundColor: ['#e44a5b','#F5A142','#17a673', '#2e59d9', '#2c9faf'],
        hoverBorderColor: "rgba(234, 236, 244, 1)",
      }],
    },
    options: {
      maintainAspectRatio: false,
      tooltips: {
        backgroundColor: "rgb(255,255,255)",
        bodyFontColor: "#858796",
        borderColor: '#dddfeb',
        borderWidth: 1,
        xPadding: 15,
        yPadding: 15,
        displayColors: false,
        caretPadding: 10,
      },
      legend: {
        display: false
      },
      cutoutPercentage: 80,
    },
  });
}

async function fetchTotalQuantityPerProduct() {
  try {
    const response = await fetch("/ChronoLuxWeb/admin/total-quantity");
    const data = await response.json();

    // Chuyển đổi dữ liệu từ API sang labels và data cho biểu đồ
    const labels = Object.keys(data).slice(0, 5); // lấy 5 productName đầu tiên
    const totalQuantity = Object.values(data).slice(0, 5); // lấy 5 totalQuantity đầu tiên

    // Cập nhật biểu đồ
    updateChart(labels, totalQuantity);
  } catch (error) {
    console.error("Error fetching total quantity per product:", error);
  }
}

document.addEventListener("DOMContentLoaded", fetchTotalQuantityPerProduct);

