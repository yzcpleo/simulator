<#assign ctx="${(rca.contextPath)!''}">
<#assign bnkCo = RequestParameters.bnkCo!'' />

<div class="page-header">
    <h1>
        对账文件列表
        <small class="pull-right">
            <a href="${ctx}/dashboard/data/dz/genall" class="btn btn-sm btn-inverse" data-toggle="modal"
               data-target="#myModal" data-backdrop="static">批量生成
            </a>
            <a href="javascript:" class="btn btn-sm btn-inverse" data-role="dzFile-push" title="批量推送给商户"
               data-url="${ctx}/dashboard/data/dz/pushall">
                批量推送
            </a>
            <a href="${ctx}/dashboard/data/dz/gen" class="btn btn-sm btn-inverse" data-toggle="modal"
               data-target="#myModal" data-backdrop="static">单个生成
            </a>
        </small>
    </h1>
</div>

<div class="space-10"></div>

<form class="form-inline" method="get">
    <div class="form-group">
        <select name="bnkCo" class="form-control">
            <option value="">--全部通道--</option>
        <#list bankChannels as bankChannel>
            <option value="${bankChannel.bnkCo}" <#if bnkCo==bankChannel.bnkCo>selected</#if>>${bankChannel.bnkNm}
                [${bankChannel.bnkCo}]
            </option>
        </#list>
        </select>
    </div>

    <button class="btn btn-sm btn-inverse" data-toggle="search-submit">
        搜索
        <span class="ace-icon fa fa-search icon-on-right bigger-110"></span>
    </button>
</form>

<div class="space-10"></div>

<table id="dzFile-table" class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th>银行通道</th>
        <th>交易类型</th>
        <th width="45%">对账文件路径</th>
        <th>生成时间</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <#if page.list?size gt 0>
        <#list page.list as dzFile>
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
<@c.pagination url="#data/dz" param="bnkCo=${bnkCo}"/>

<script src="${ctx}/static/app/js/dashboard/data/dz/list.js"></script>
