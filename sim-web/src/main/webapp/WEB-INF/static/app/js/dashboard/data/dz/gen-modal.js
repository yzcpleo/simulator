$(function () {
    var $form = $('#modal-form');
    var $btn = $("#submit");

    $form.validate({
        rules: {
            id: {
                required: true
            },
            workDay: {
                required: true
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

    $("#workDay").datetimepicker({
        language: 'zh-CN',
        autoclose: 1,
        todayBtn: 1,
        pickerPosition: "bottom-left",
        format: 'yyyy-mm-dd',
        minView: 2
    });

    var today = new Date();

    $("#workDay").val(today.getFullYear() + "-" + p(today.getMonth() + 1) + "-" + p(today.getDate()));

    function p(num) {
        return num < 10 ? "0" + num : num;
    }
});