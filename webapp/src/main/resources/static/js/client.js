function doGet(url, param) {
    return execute(url, "GET", param);
}

function doPost(url, body) {
    return execute(url, "POST", body);
}

function execute(url, method, param) {
    let deferred = new $.Deferred();

    $.ajax({
        url: url,
        method: method,
        data: param,
        cache: false
    }).then(
        function (data) {
            deferred.resolve(data);
        },
        function (data) {
            deferred.resolve(data);
        }
    );
    return deferred.promise();
}