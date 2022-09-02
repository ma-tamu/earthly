function doGet(url, param) {
    return execute(url, "GET", param, null);
}

function doPost(url, param, body) {
    return execute(url, "POST", param, body);
}

function execute(url, method, param, body) {
    let deferred = new $.Deferred();

    $.ajax({
        url: url,
        method: method,
        param: param,
        data: body,
        cache: false
    }).then(
        function (data) {
            deferred.resolve();
        },
        function (data) {
            deferred.rejected();
        }
    );
    return deferred;
}