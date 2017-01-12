$(function () {
    updateState("data/dz");

    $(document).on('click', 'a[data-role=dzFile-push]', function () {
        var $trigger = $(this);
        var url = $trigger.data('url');
        var title = $trigger.attr("title");

        $.messager.confirm("提示", "确定" + title + "吗?", function () {
            $.get(url).success(function () {
                Message.success("操作成功");
            }).error(function () {
                Message.error("网络错误，请稍后重试");
            })
        });
    });
});