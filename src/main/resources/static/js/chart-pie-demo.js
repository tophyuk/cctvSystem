// Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily = '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = '#292b2c';

// Pie Chart Example

function createPieChart(data) {

    // 데이터 가공하기
    var labels = []; // 라벨 배열
    var counts = []; // 데이터 배열

    for(var i=0; i<data.length; i++) {
        labels.push(data[i].name);
        counts.push(data[i].count);
    }

    var ctx = document.getElementById("myPieChart");
    var myPieChart;

    if(myPieChart) {
        myPieChart.data.labels = labels;
        myPieChart.data.datasets[0].data = counts;
        myPieChart.update(); // 새로운 데이터로 차트를 업데이트합니다.
    } else {
        myPieChart = new Chart(ctx, {
          type: 'pie',
          data: {
            labels: labels,
            datasets: [{
              data: counts,
              backgroundColor: ['#007bff', '#dc3545', '#ffc107', '#28a745'],
            }],
          },
        });
    }



}
