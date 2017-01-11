<#assign ctx="${(rca.contextPath)!''}">
<#assign bnkCo = RequestParameters.bnkCo!'' />

<div class="page-header">
    <h1>
        错误码列表
        <small class="pull-right">
            <a href="${ctx}/download/bank_resp_template.xlsx" class="btn btn-sm btn-inverse" target="_blank">下载模板</a>

            <a href="${ctx}/dashboard/bank/resp/import" class="btn btn-sm btn-inverse" data-toggle="modal"
               data-target="#myModal"
               data-backdrop="static">批量导入</a>

            <a href="${ctx}/dashboard/bank/resp/create" class="btn btn-sm btn-inverse" data-toggle="modal"
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

<table id="bankResp-table" class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th>银行通道</th>
        <th>错误码</th>
        <th>错误描述</th>
        <th>更新时间</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <#if page.list?size gt 0>
        <#list page.list as bankResp>
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
<@c.pagination url="#bank/resp" param="bnkCo=${bnkCo}"/>

<script src="${ctx}/static/app/js/dashboard/bank/resp/list.js"></script>
