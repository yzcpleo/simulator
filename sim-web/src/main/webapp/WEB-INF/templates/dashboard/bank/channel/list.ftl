<#assign ctx="${(rca.contextPath)!''}">
<#assign bnkNm = RequestParameters.bnkNm!'' />

<div class="page-header">
    <h1>
        银行通道列表
        <small class="pull-right">
            <a href="${ctx}/dashboard/bank/channel/create" class="btn btn-sm btn-inverse" data-toggle="modal"
               data-target="#myModal"
               data-backdrop="static">添加</a>
        </small>
    </h1>
</div>

<div class="space-10"></div>

<form class="form-inline" method="get">
    <div class="form-group">
        <input type="text" class="form-control" name="bnkNm" value="${bnkNm}" placeholder="银行名称"
               autocomplete="off"/>
    </div>

    <button class="btn btn-sm btn-inverse" data-toggle="search-submit">
        搜索
        <span class="ace-icon fa fa-search icon-on-right bigger-110"></span>
    </button>
</form>

<div class="space-10"></div>

<table id="bankChannel-table" class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th>银行代码</th>
        <th>银行名称</th>
        <th>创建时间</th>
        <th>更新时间</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <#if page.list?size gt 0>
        <#list page.list as bankChannel>
            <#include "table-tr.ftl"/>
        </#list>
    <#else>
    <tr>
        <td colspan="20">
            <div class="empty">暂无查询记录</div>
        </td>
    </tr>
    </#if>
    </tbody>
</table>
<@c.pagination url="#bank/channel" param="bnkNm=${bnkNm}"/>

<script src="${ctx}/static/app/js/dashboard/bank/channel/list.js"></script>
