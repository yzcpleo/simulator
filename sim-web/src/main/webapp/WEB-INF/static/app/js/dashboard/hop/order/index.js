$(function () {
    updateState("hop/order");

    var showNotify = function () {
        Message.success("删除交易成功");
    };

    var $table = $('#order-table');
    $table.on('click', 'a[data-role=delete-order]', function () {
        var $trigger = $(this);
        var url = $trigger.data('url');
        var title = $trigger.attr("title");
        $.messager.confirm("警告", "确定删除" + title + "吗?", function () {
            $.get(url, function () {
                $trigger.parents('tr').remove();
            })
                .success(showNotify)
        });
    });

    $table.on('click', 'a[data-role=status-order]', function () {
        var $this = $(this);
        var url = $this.data('url');
        $.get(url, function (html) {
            var $tr = $(html);
            $('#' + $tr.attr('id')).replaceWith($tr);
        })
    });
});