<#assign ctx="${(rca.contextPath)!''}">

<#if isList>
    <#list cache as obj>
    ${obj}
    <div class="space-10"></div>
    <div class="hr hr-18 dotted"></div>
    <div class="space-10"></div>
    </#list>
<#else>
${cache}
</#if>

<script src="${ctx}/static/app/js/dashboard/data/cache/detail.js"></script>
