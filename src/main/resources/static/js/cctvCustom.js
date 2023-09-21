 $(document).ready(function () {

    // Get the cctvDtoList length from the server-side (You can set this in your template)
    var cctvListLength = /* Set the cctvDtoList length here */;

    // Calculate the number of rows and columns dynamically based on cctvListLength
    var rows = Math.ceil(Math.sqrt(cctvListLength));
    var columns = Math.ceil(cctvListLength / rows);

    // Generate the divs dynamically based on the calculated rows and columns
    for (var i = 0; i < rows; i++) {
        var rowDiv = $("<div>").addClass("row");
        for (var j = 0; j < columns; j++) {
            var colDiv = $("<div>").addClass("col-xl-" + Math.floor(12 / columns));
            // Add your content for each div here
            // You can access the cctvDtoList elements by index (e.g., cctvDtoList[i * columns + j])
            if (i * columns + j < cctvListLength) {
                var cctvDto = cctvDtoList[i * columns + j];
                colDiv.append(`<div class="image-container">
                <img src="${cctvDto.url}" alt="${cctvDto.name}" class="img-fluid"
                 onclick="openImagePopup('${cctvDto.url}')">
                </div>`);
            }
            rowDiv.append(colDiv);
        }
        $("#dynamicRow").append(rowDiv);
    }

});