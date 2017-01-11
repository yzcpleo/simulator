$(function () {
    var $form = $('#modal-form');
    var $btn = $("#submit");

    $form.validate({
        rules: {
            bnkCo: {
                required: true,
                rangelength: [2, 16],
                remote: {
                    url: ctx + "/validate/bank",
                    type: 'post',
                    data: {
                        'bnkCo': function () {
                            return $('#bnkCo').val()
                        },
                        'oldBnkCo': function () {
                            return $('#old-bnkCo').val();
                        }
                    }
                }
            },
            bnkNm: {
                required: true,
                rangelength: [2, 16]
            }
        },
        messages: {
            bnkNo: {
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