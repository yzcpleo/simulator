$(function () {
    var $form = $('#modal-form');
    var $btn = $("#submit");

    $form.validate({
        submitHandler: function (form, event) {
            event.preventDefault();
            $btn.button('loading');
            $(form).ajaxSubmit({
                dataType: 'json',
                success: function (response) {
                    console.log(response);
                    if (response.errCode == 'success') {
                        window.location.reload();
                    } else {
                        Message.error(response.errMsg);
                    }
                    $btn.button('reset');
                },
                error: function (data, textstatus) {
                    Message.error("服务器内部错误，请稍后再试。");
                    $btn.button('reset');
                }
            });
        }
    })
});