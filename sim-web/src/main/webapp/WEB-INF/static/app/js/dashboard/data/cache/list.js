$(function () {
    updateState("data/cache");
    var $table = $('#cache-table');

    $table.on('click', 'a[data-role=clear-cache]', function () {
        var $trigger = $(this);
        var url = $trigger.data('url');
        var title = $trigger.attr("title");

        $.messager.confirm("提示", "确定" + title + "吗?", function () {
            $.get(url).success(function () {
                $trigger.parents("tr").remove();
                if ($table.find("tbody").find("tr").length == 0) {
                    $table.find("tbody").append("<tr> <td colspan='20'> <div class='empty'>暂无查询记录</div> </td> </tr>");
                }
                Message.success("操作成功");
            }).error(function () {
                Message.error("网络错误，请稍后重试");
            })
        });
    });

    $("#clear-module").click(function () {
        var href = $(this).attr("href");
        $.messager.confirm("提示", "确定清空吗?", function () {
            $.get(href).success(function () {
                Message.success("操作成功");
                $table.find("tbody").empty().append("<tr> <td colspan='20'> <div class='empty'>暂无查询记录</div> </td> </tr>");
            }).error(function () {
                Message.error("网络错误，请稍后重试");
            })
        });

        return false;
    });
});