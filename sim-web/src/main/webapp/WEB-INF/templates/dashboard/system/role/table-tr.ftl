<#assign ctx="${(rca.contextPath)!''}">

<tr id="role-${role.id}">
    <td>${role.code}</td>
    <td>${role.name}</td>
    <td><#include "delete.ftl"></td>
    <td><@c.relative_date datetime=role.createdTime/></td>
    <td>
        <div class="btn-group">
            <a data-toggle="modal" class="btn btn-xs btn-inverse" href="${ctx}/dashboard/system/role/${role.id}"
               data-target="#myModal">查看</a>

            <button data-toggle="dropdown" class="btn btn-xs btn-inverse dropdown-toggle">
                <span class="ace-icon fa fa-caret-down icon-only"></span>
            </button>

            <ul class="dropdown-menu dropdown-menu-right dropdown-inverse">
                <li>
                    <a href="${ctx}/dashboard/system/role/${role.id}/edit" data-toggle="modal" data-target="#myModal"
                       data-backdrop="static">编辑</a>
                </li>
                <li>
                    <a href="${ctx}/dashboard/system/role/${role.id}/menus" data-toggle="modal" data-target="#myModal"
                       data-backdrop="static">设置权限</a>
                </li>
            </ul>
        </div>
    </td>
</tr>