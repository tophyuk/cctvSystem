function getAlarmGraph(type, year, month, day) {
    var data = {
        year : year,
        month : month,
        day : day
    };

   // AJAX 요청
    $.ajax({
      url: '/alarm/graphs/' + type,  // 서버의 API 엔드포인트 URL
      type: 'GET',       // HTTP 요청 메소드 (GET, POST 등)
      data : data,
      success: function(data) {
        createBarChart(type, data);
      },
      error: function(xhr, status, error) {
        // 오류 처리
        console.log(error);
      }
    });

}

function getAlarmData(type, year, month, day, hour){
    var data = {
        year : year,
        month : month,
        day : day,
        hour : hour
    };

    $.ajax({
      url: '/alarm/tables/' + month,  // 서버의 API 엔드포인트 URL
      type: 'GET',       // HTTP 요청 메소드 (GET, POST 등)
      data : data,
      success: function(data) {
        console.log(data)
        createAlarmTable(data);

      },
      error: function(xhr, status, error) {
        // 오류 처리
        console.log(error);
      }
    });
}

function getTargetGraph(type, year, month, day) {
    var data = {
        year : year,
        month : month,
        day : day
    };

     // AJAX 요청
      $.ajax({
        url: '/target/graphs/'+ type,  // 서버의 API 엔드포인트 URL
        type: 'GET',       // HTTP 요청 메소드 (GET, POST 등)
        data : data,
        success: function(data) {
            createPieChart(data);

        },
        error: function(xhr, status, error) {
          // 오류 처리
          console.log(error);
        }
      });


}




