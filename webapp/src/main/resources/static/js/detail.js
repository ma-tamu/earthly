const toKebab = str => {
    return str.split('').map((letter, idx) => {
        return letter.toUpperCase() === letter
            ? `${idx !== 0 ? '-' : ''}${letter.toLowerCase()}`
            : letter;
    }).join('');
}

function cancel() {
    const form = $('form > .edit');
    form.find('input').each((i, e) => {
        let v = e.dataset.before;
        if (v === undefined) {
            return true;
        }
        if (e.type === 'checkbox') {
            e.checked = JSON.parse(v);
            e.disabled = false;
        } else {
            e.value = v;
            e.disabled = false;
        }
    });
    form.find('select').each((i, e) => {
        let before = e.dataset.before;
        if (before === undefined) {
            return true;
        }

        if (e.multiple) {
            const multipleSelect = $('#' + e.id);
            multipleSelect.select2().val(null).trigger('change');
            multipleSelect.select2().val(multipleSelect.data('before').split(',')).trigger('change');
        } else {
            Array.from(e.options).forEach(opt => {
                if (opt.value === before) {
                    opt.selected = true;
                    return true;
                }
            });
        }
        e.disabled = false;
    });
}

$('#edit').on('hidden.bs.modal', function (e) {
    cancel();
});

function postSearch(formName, contentId) {
    const form = $('form[name=' + formName + ']');
    const body = form.serialize();
    const url = form.attr('action');
    const deferred = doGet(url, body);
    deferred.done((data, response, status) => {
        $('#' + contentId).html(data);
    });
    deferred.fail((data, response) => {
        console.error(data);
        window.location.href = "/earthly/error";
    });
}


function showRemoveCard(checkboxName, formName) {
    $('form[name!=' + formName + ']').find('input,button').each((i, e) => {
        e.disabled = true;
    });
    $('.btn.edit').prop('disabled', true);
    $('.btn.delete').prop('disabled', true);
    $('button.btn-primary[class*="add-"]').prop('disabled', true);
    $('button.btn-danger[class*="remove"]').prop('disabled', true);
    const form = $('form[name=' + formName + ']');
    form.find('.card-footer.row-unassigned').show();
    form.find('input[name=' + checkboxName + ']').show()
    $('input[name=isRemoveMode]').val(true);
}


function hideRemoveCard(checkboxName, formName) {
    $('form[name!=' + formName + ']').find('input,button').each((i, e) => {
        e.disabled = false;
    });
    $('.btn.edit').prop('disabled', false);
    $('.btn.delete').prop('disabled', false);
    $('button.btn-primary[class*="add-"]').prop('disabled', false);
    $('button.btn-danger[class*="remove"]').prop('disabled', false);
    const form = $('form[name=' + formName + ']');
    form.find('.card-footer.row-unassigned').hide();
    form.find('input[name=' + checkboxName + ']').hide()
    $('input[name=isRemoveMode]').val(false);

}

function activeSubmitButton() {
    let isDisabled = false;
    $('.modal.show .modal-content > form').find('input[type=text], select').each((i, e)=>{
        if(e.type === 'text' && e.value === '') {
            isDisabled = true;
        }
        if(e.type === 'select-one' && e.selectedIndex === 0) {
            isDisabled = true;
        }
    });
    $('.modal.show .modal-content > form button[type=submit]').prop('disabled', isDisabled);
}
