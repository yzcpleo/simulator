<#assign ctx="${(rca.contextPath)!''}">
<#assign bnkCo = RequestParameters.bnkCo!'' />
<#assign merSerialNo = RequestParameters.merSerialNo!'' />

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

    <div class="form-group">
        <input class="form-control" name="merSerialNo" value="${merSerialNo}" placeholder="商户流水号"/>
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
        <th>银行通道</th>
        <th>交易类型</th>
        <th>商户流水号</th>
        <th>错误码</th>
        <th>交易状态</th>
        <th>交易时间</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <#if page.list?size gt 0>
        <#list page.list as bankCommand>
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
<@c.pagination url="#data/command" param="bnkCo=${bnkCo}&merSerialNo=${merSerialNo}"/>

<script src="${ctx}/static/app/js/dashboard/data/command/list.js"></script>
