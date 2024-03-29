function doGet(url, param) {
    return execute(url, "GET", "application/json", param);
}

function doPost(url, body) {
    return execute(url, "POST", "application/json", body);
}

function doFormPost(url, body) {
    return execute(url, "POST", "application/x-www-form-urlencoded", body);
}

function doPost(url, contentType, body) {
    return execute(url, "POST", contentType, body);
}

function execute(url, method, contentType, param) {
    let deferred = new $.Deferred();

    $.ajax({
        url: url,
        method: method,
        data: param,
        contentType: contentType,
        cache: false
    }).then(
        function (data, response) {
            deferred.resolve(data, response);
        },
        function (data, response) {
            deferred.resolve(data, response);
        }
    );
    return deferred.promise();
}