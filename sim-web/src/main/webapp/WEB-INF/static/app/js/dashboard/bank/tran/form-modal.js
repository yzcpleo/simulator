$(function () {
    var $form = $('#modal-form');
    var $btn = $("#submit");

    $form.validate({
        rules: {
            tranCo: {
                required: true,
                rangelength: [2, 16],
                remote: {
                    url: ctx + "/validate/tran",
                    type: 'post',
                    data: {
                        'bnkCo': function () {
                            return $('#bnkCo').val()
                        },
                        'tranCo': function () {
                            return $('#tranCo').val();
                        },
                        'oldTranCo': function () {
                            return $('#old-tranCo').val();
                        }
                    }
                }
            },
            tranNm: {
                required: true,
                rangelength: [2, 16]
            },
            dzTemp: {
                required: false,
                maxlength: 512
            }
        },
        messages: {
            tranCo: {
                remote: "不可用"
            }
        },
        submitHandler: function (form, event) {
            event.preventDefault();
            $btn.button('loading');
            $(form).ajaxSubmit({
                dataType: 'json',
                success: function (response) {
                    if (response.errCode == 'success') {
                        window.location.reload();
                    } else {
                        Message.error(response.errMsg);
                        $btn.button('reset');
                    }
                },
                error: function () {
                    Message.error("服务器内部错误，请稍后再试。");
                    $btn.button('reset');
                }
            });
        },
        errorPlacement: function (error, element) {
            error.appendTo(element.parent());
        },
        errorElement: "div",
        errorClass: "error"
    });
});