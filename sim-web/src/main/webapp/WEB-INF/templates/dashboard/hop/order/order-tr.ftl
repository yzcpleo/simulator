<#assign ctx="${(rca.contextPath)!''}">

<tr id="order-${order.id}">
    <td>${order.id}</td>
    <td>${order.name!''}</td>
    <td>${order.certNo!''}</td>
    <td>${order.prodId!''}</td>
    <td>${order.amount!''}</td>
    <td><#include "order-info.ftl"></td>
    <td><#include "order-type.ftl"></td>
    <td>
        <div class="btn-group">
            <a data-toggle="modal" class="btn btn-xs btn-inverse" href="${ctx}/dashboard/hop/order/${order.id}"
               data-target="#myModal">查看</a>

            <button data-toggle="dropdown" class="btn btn-xs btn-inverse dropdown-toggle">
                <span class="ace-icon fa fa-caret-down icon-only"></span>
            </button>

            <ul class="dropdown-menu dropdown-menu-right dropdown-inverse">
                <li>
                    <a href="${ctx}/dashboard/hop/order/${order.id}/edit" data-toggle="modal" data-target="#myModal"
                       data-backdrop="static">编辑交易信息</a>
                </li>
                <li>
                    <a title="${order.id}" data-role="delete-order" data-url="${ctx}/dashboard/hop/order/${order.id}/delete">
                        删除交易
                    </a>
                </li>
            </ul>
        </div>
    </td>
</tr>