<#assign modal_title="用户详情" />

<@override name="modal-body">
<table class="table table-striped table-bordered table-hover no-margin-bottom no-border-top">
    <tbody>
    <tr>
        <th width="25%">ID</th>
        <td width="75%">${user.id}</td>
    </tr>
    <tr>
        <th>用户名</th>
        <td>${user.username}</td>
    </tr>
    <tr>
        <th>姓名</th>
        <td>${user.fullname}</td>
    </tr>
    <tr>
        <th>逻辑删除</th>
        <td>${(user.isDeleted==1)?string('是', '否')}</td>
    </tr>
    <tr>
        <th>创建时间</th>
        <td><#if user.createdTime??>${user.createdTime?datetime}</#if></td>
    </tr>
    <tr>
        <th>更新时间</th>
        <td><#if user.updatedTime??>${user.updatedTime?datetime}</#if></td>
    </tr>
    </tbody>
</table>
</@override>

<@override name="modal-footer">
<button class="btn btn-sm" data-dismiss="modal">
    <i class="ace-icon fa fa-times"></i>
    <@spring.message "app.button.close"/>
</button>
</@override>

<@extends name="../../../modal-layout.ftl"/>