function graphExcelDownload(type, year, month, day) {
    var data = {
        year : year,
        month : month,
        day : day
    };

    $.ajax({
      url: "/alarm/graphs/excel/" + type, // API 엔드포인트 URL
      type: 'GET',
      data: data,
      xhrFields: {
          responseType: 'blob'
      },
      success: function(response, status, xhr) {
        var filename = getFileNameFromResponse(xhr);
        var blob = new Blob([response], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
        var url = URL.createObjectURL(blob);
        var link = document.createElement('a');
        link.href = url;
        link.setAttribute('download', filename);
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
      },
      error: function(xhr, status, error) {
          console.log('Error:', error);
      }
    });
}



function dataExcelDownload(type, year, month, day, hour) {
    var data = {
        year : year,
        month : month,
        day : day,
        hour: hour
    };


    $.ajax({
      url: "/alarm/tables/excel/"+type, // API 엔드포인트 URL
      type: 'GET',
      data: data,
      xhrFields: {
          responseType: 'blob'
      },
      success: function(response, status, xhr) {
        var filename = getFileNameFromResponse(xhr);
        var blob = new Blob([response], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
        var url = URL.createObjectURL(blob);
        var link = document.createElement('a');
        link.href = url;
        link.setAttribute('download', filename);
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
      },
      error: function(xhr, status, error) {
          console.log('Error:', error);
      }
    });
}


function getFileNameFromResponse(xhr) {
  var contentDisposition = xhr.getResponseHeader('Content-Disposition');
  var filename = '';
  if (contentDisposition && contentDisposition.indexOf('attachment') !== -1) {
    var filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
    var matches = filenameRegex.exec(contentDisposition);
    if (matches != null && matches[1]) {
      filename = matches[1].replace(/['"]/g, '');
    }
  }
  return filename;
}