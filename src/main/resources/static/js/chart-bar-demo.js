// Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily = '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = '#292b2c';


// Bar Chart Example
function createBarChart(type, data) {

    console.log(data);

    if(type=="year") {
        // 데이터 가공하기
        var labels = []; // 라벨 배열
        var counts = []; // 데이터 배열

        // 데이터 초기화
        for (var i = 1; i <= 12; i++) {
            labels.push(i+"월"); // 일별 라벨 추가
        }

        // 데이터 채우기
        for (var j = 0; j < data.length; j++) {
            var item = data[j];
            var month = item.month;
            var count = item.count;
            counts[month - 1] = count; // 해당 날짜의 데이터 채우기
        }

        var ctx = document.getElementById("myBarChart");

        // 그래프 객체 확인 및 삭제
        if (window.myLineChart) {
            window.myLineChart.destroy();
        }

        window.myLineChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [{
                label: "경보 알림 수",
                backgroundColor: "rgba(2,117,216,1)",
                borderColor: "rgba(2,117,216,1)",
                data: counts,
                }],
            },
            options: {
                scales: {
                  xAxes: [{
                    time: {
                      unit: 'month'
                    },
                    gridLines: {
                      display: false
                    },
                    ticks: {
                      maxTicksLimit: 12
                    }
                  }],
                  yAxes: [{
                    ticks: {
                      min: 0,
                      max: 30,
                      //maxTicksLimit: 5
                    },
                    gridLines: {
                      display: true
                    }
                  }],
                },
                legend: {
                  display: false
                }
            }
        });
    } else if(type=="month") {
        // 데이터 가공하기
        var labels = []; // 라벨 배열
        var counts = []; // 데이터 배열

        // 데이터 초기화
        for (var i = 1; i <= 31; i++) {
            labels.push(i+"일"); // 일별 라벨 추가
        }

        // 데이터 채우기
        for (var j = 0; j < data.length; j++) {
            var item = data[j];
            var day = item.day;
            var count = item.count;
            counts[day - 1] = count; // 해당 날짜의 데이터 채우기
        }

        var ctx = document.getElementById("myBarChart");

        // 그래프 객체 확인 및 삭제
        if (window.myLineChart) {
            window.myLineChart.destroy();
        }


        window.myLineChart = new Chart(ctx, {
          type: 'bar',
          data: {
            labels: labels,
            datasets: [{
              label: "경보 알림 수",
              backgroundColor: "rgba(2,117,216,1)",
              borderColor: "rgba(2,117,216,1)",
              data: counts,
            }],
          },
          options: {
            scales: {
              xAxes: [{
                time: {
                  unit: 'day'
                },
                gridLines: {
                  display: false
                },
                ticks: {
                  maxTicksLimit: 31
                }
              }],
              yAxes: [{
                ticks: {
                  min: 0,
                  max: 30,
                  //maxTicksLimit: 30
                },
                gridLines: {
                  display: true
                }
              }],
            },
            legend: {
              display: false
            }
          }
        });

    } else if(type == "day") {
        // 데이터 가공하기
        var labels = []; // 라벨 배열
        var counts = []; // 데이터 배열

        // 데이터 초기화
        for (var i = 0; i < 24; i++) {
            labels.push(i+"시"); // 일별 라벨 추가
        }

        // 데이터 채우기
        for (var j = 0; j < data.length; j++) {
            var item = data[j];
            var hour = item.hour;
            var count = item.count;
            counts[hour] = count; // 해당 날짜의 데이터 채우기
        }

        var ctx = document.getElementById("myBarChart");

        // 그래프 객체 확인 및 삭제
        if (window.myLineChart) {
            window.myLineChart.destroy();
        }

        window.myLineChart = new Chart(ctx, {
          type: 'bar',
          data: {
            labels: labels,
            datasets: [{
              label: "경보 알림 수",
              backgroundColor: "rgba(2,117,216,1)",
              borderColor: "rgba(2,117,216,1)",
              data: counts,
            }],
          },
          options: {
            scales: {
              xAxes: [{
                time: {
                  unit: 'hour'
                },
                gridLines: {
                  display: false
                },
                ticks: {
                  maxTicksLimit: 24
                }
              }],
              yAxes: [{
                ticks: {
                  min: 0,
                  max: 30,
                  //maxTicksLimit: 50
                },
                gridLines: {
                  display: true
                }
              }],
            },
            legend: {
              display: false
            }
          }
        });
    }
}

