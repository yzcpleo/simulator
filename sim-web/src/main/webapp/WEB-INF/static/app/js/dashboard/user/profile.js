$(function () {
    var $form = $('#form');
    var file_input = $form.find('input[type=file]');
    var $btn = $("#submit");

    file_input.ace_file_input({
        style: 'well',
        btn_choose: '点击这里添加图片',
        btn_change: null,
        no_icon: 'ace-icon fa fa-picture-o',
        droppable: false,
        allowExt: ["jpeg", "jpg", "png", "gif"],
        allowMime: ["image/jpeg", "image/jpg", "image/png", "image/gif"],
        maxSize: 2097152,//bytes
        thumbnail: 'fit'
    });

    file_input.on('file.error.ace', function (event, info) {
        if (info.error_count['size']) Notify.warning('超出最大上传限制。');
        if (info.error_count['ext'] || info.error_count['mime']) Notify.warning('不合法的文件类型。');
        event.preventDefault();
    });

    $form.validate({
        rules: {
            fullname: {
                required: true,
                isFullname: true
            }
        },
        errorPlacement: function (error, element) {
            error.appendTo(element.parent());
        },
        submitHandler: function (form, event) {
            event.preventDefault();
            $btn.button('loading');
            $(form).ajaxSubmit({
                dataType: 'json',
                success: function (response) {
                    if (response.errCode == 'success') {
                        Message.success("修改成功");
                        var user = response.user;
                        // 更新navbar和当前页
                        if (user.largeAvatar != '') {
                            $("#largeAvatar").attr("src", ctx + "/" + user.largeAvatar);
                            $(".nav-user-photo").attr("src", ctx + "/" + user.smallAvatar);
                        }
                        $("#navFullname").html(user.fullname);
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