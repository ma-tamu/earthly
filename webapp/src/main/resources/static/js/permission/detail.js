$('.search-assigned-role').on('click', () => postSearch('searchAssignedRole', 'assignedRoleContent'));
$('.search-unassigned-role').on('click', () => postSearch('searchUnassignedRole', 'unassignedRoleContent'));

$('.btn.btn-primary.grant').on('click', () => {
    const form = $('form[name=grantUser]');
    const body = form.serialize();
    const url = form.attr('action');
    const token = form.find('input[name=_csrf]').val();
    let deferred = doPost(url, "application/x-www-form-urlencoded", token, body);
    deferred.done(function (data, response) {
        $("body").append(data);
        $('.search-not-grant-user').trigger('click');
        $('.search-granted-user').trigger('click');
        $('.btn.grant').prop('disabled', true);
    });
    deferred.fail(function (data, response) {
        console.error(data);
        window.location.href = "/earthly/error";
    });
});

$('.remove-role').on('click', () => {
    showRemoveCard('roleId', 'removeRole');
});

$('.remove-unassign-role-cancel').on('click', () => {
    hideRemoveCard('roleId', 'removeRole');
});

$('.btn.btn-primary.remove-unassign-role').on('click', () => {
    const form = $('form[name=removeRole]');
    const body = form.serialize();
    const url = form.attr('action');
    const token = form.find('input[name=_csrf]').val();
    let deferred = doPost(url, "application/x-www-form-urlencoded", token, body);
    deferred.done(function (data, response) {
        $("body").append(data);
        const searchForm = $('form[name=searchAssignedRole]');
        searchForm.find('input').each((i, e) => {
            e.disabled = false;
        });
        $('.search-assigned-role').trigger('click');
        searchForm.find('input').each((i, e) => {
            e.disabled = true;
        });
    })
});

$('#assignRole').on('show.bs.modal', () => {
    $('.btn.btn-primary.assign-role').prop('disabled', true);
});

function activeAssignableButton() {
    $('.btn.assign-role').prop('disabled', true);
    $('input[name=roleId].role').each((i, e) => {
        if (e.checked) {
            $('.btn.assign-role').prop('disabled', false);
        }
    });
}

$('.btn.btn-primary.assign-role').on('click', () => {
    const form = $('form[name=assignRole]');
    const body = form.serialize();
    const url = form.attr('action');
    const token = form.find('input[name=_csrf]').val();
    let deferred = doPost(url, "application/x-www-form-urlencoded", token, body);
    deferred.done(function (data, response) {
        $("body").append(data);
        $('.search-unassigned-role').trigger('click');
        $('.search-assigned-role').trigger('click');
        $('.btn.assign-role').prop('disabled', true);
    });
    deferred.fail(function (data, response) {
        console.error(data);
        window.location.href = "/earthly/error";
    });
});

$('.btn.btn-primary.remove-role').on('click', () => {
    const form = $('form[name=removeRole]');
    const body = form.serialize();
    const url = form.attr('action');
    const token = form.find('input[name=_csrf]').val();
    let deferred = doPost(url, "application/x-www-form-urlencoded", token, body);
    deferred.done(function (data, response) {
        $("body").append(data);
        const searchForm = $('form[name=searchAssignedRole]');
        searchForm.find('input').each((i, e) => {
            e.disabled = false;
        });
        $('.search-assigned-role').trigger('click');
        searchForm.find('input').each((i, e) => {
            e.disabled = true;
        });
    })
});

function activeRoleUnassignableButton() {
    $('.btn.remove-unassign-role').prop('disabled', true);
    $('input[name=roleId].unassign-role').each((i, e) => {
        if (e.checked) {
            $('.btn.remove-unassign-role').prop('disabled', false);
        }
    });
}
