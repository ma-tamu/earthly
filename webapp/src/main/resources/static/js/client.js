function doGet(url, param) {
    return execute(url, "GET", "application/json", param);
}

function doPost(url, body) {
    return execute(url, "POST", "application/json", body);
}

function doFormPost(url, body) {
    return execute(url, "POST", "application/x-www-form-urlencoded", null, body);
}

function doPost(url, contentType, csrfToken, body) {
    return execute(url, "POST", contentType, csrfToken, body);
}

function execute(url, method, contentType, csrfToken, param) {
    let deferred = new $.Deferred();

    $.ajax({
        url: url,
        method: method,
        headers: {
            'X-CSRF-TOKEN': csrfToken
        },
        data: param,
        contentType: contentType,
        cache: false,
        xhrFields: {
            withCredentials: true
        }
    }).then(
        function (data, response, status) {
            deferred.resolve(data, response, status);
        },
        function (data, response, status) {
            deferred.resolve(data, response, status);
        }
    );
    return deferred.promise();
}