$(function () {
    var $form = $('#modal-form');
    var file_input = $form.find('input[type=file]');
    var $btn = $("#submit");

    file_input.ace_file_input({
        style: 'well',
        btn_choose: '点击这里添加Excel',
        btn_change: null,
        no_icon: 'ace-icon fa fa-file-o',
        droppable: false,
        allowExt: ["xlsx", "xls"],
        allowMime: ["application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "application/vnd.ms-excel", "application/x-excel"],
        maxSize: 1024 * 1024 * 10,//bytes
        thumbnail: 'fit'
    });

    file_input.on('file.error.ace', function (event, info) {
        if (info.error_count['size']) Notify.warning('超出最大上传限制。');
        if (info.error_count['ext'] || info.error_count['mime']) Message.warning('不合法的文件类型。');
        event.preventDefault();
    });

    $form.validate({
        errorPlacement: function (error, element) {
            error.appendTo(element.parent());
        },
        submitHandler: function (form, event) {
            event.preventDefault();

            if ($("#excel").val() == '') {
                Message.warning("请选择文件");
                return;
            }

            $btn.button('loading');
            $(form).ajaxSubmit({
                dataType: 'json',
                success: function (response) {
                    if (response.errCode == 'success') {
                        window.location.reload();
                    } else {
                        Message.error(response.errMsg);
                    }
                    $btn.button('reset');
                },
                error: function () {
                    Message.error("服务器内部错误，请稍后再试。");
                    $btn.button('reset');
                }
            });
        },
        errorElement: "div",
        errorClass: "error"
    });
});