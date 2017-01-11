<#assign ctx="${(rca.contextPath)!''}">
<#assign bnkCo = RequestParameters.bnkCo!'' />

<div class="page-header">
    <h1>
        交易类型列表
        <small class="pull-right">
            <a href="${ctx}/dashboard/bank/tran/create" class="btn btn-sm btn-inverse" data-toggle="modal"
               data-target="#myModal"
               data-backdrop="static">添加</a>
        </small>
    </h1>
</div>

<div class="space-10"></div>

<form class="form-inline" method="get">
    <div class="form-group">
        <select name="bnkCo" class="form-control">
            <option value="">--全部通道--</option>
            <#list bankChannels as bankChannel>
                <option value="${bankChannel.bnkCo}" <#if bnkCo==bankChannel.bnkCo>selected</#if>>${bankChannel.bnkNm}[${bankChannel.bnkCo}]</option>
            </#list>
        </select>
    </div>

    <button class="btn btn-sm btn-inverse" data-toggle="search-submit">
        搜索
        <span class="ace-icon fa fa-search icon-on-right bigger-110"></span>
    </button>
</form>

<div class="space-10"></div>

<table id="bankTran-table" class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th>银行通道</th>
        <th>交易类型</th>
        <th>错误码</th>
        <th width="40%">对账文件模板</th>
        <th>生成/推送</th>
        <th>更新时间</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <#if page.list?size gt 0>
        <#list page.list as bankTran>
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
<@c.pagination url="#bank/tran" param="bnkCo=${bnkCo}"/>

<script src="${ctx}/static/app/js/dashboard/bank/tran/list.js"></script>
