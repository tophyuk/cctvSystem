function createAlarmTable (data) {
    var html = '';
    // HTML에 적용
    if(data.length == 0) {
            html += '<tr>';
            html += '<td colspan="5" style="text-align:center">데이터가 존재하지않습니다.</td>';
            html += '</tr>';
        $('#resultBody').html(html);
    } else {
        for (var i = 0; i < data.length; i++) {
            var item = data[i];
            html += '<tr>';
            html += '<td>' + item.deviceName + '</td>';
            html += '<td>' + item.targetName + '</td>';
            html += '<td>' + item.level + '</td>';
            html += '<td>' + item.detectedTime + '</td>';
            html += '</tr>';
        }
        $('#resultBody').html(html);
    }

}