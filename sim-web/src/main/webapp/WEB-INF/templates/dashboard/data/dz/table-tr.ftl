<#assign ctx="${(rca.contextPath)!''}">

<tr id="dzFile-${dzFile.id}">
    <td>${dzFile.bnkNm}[${dzFile.bnkCo}]</td>
    <td>${dzFile.tranNm}[${dzFile.tranCo}]</td>
    <td><a href="${ctx}/${dzFile.filePath}" target="_blank">${dzFile.filePath}</a></td>
    <td><@c.relative_date datetime=dzFile.createdTime/></td>
    <td>
        <#if dzFile.isPushDz==1>
            <div class="btn-group">
                <a href="javascript:" class="btn btn-xs btn-inverse" data-role="dzFile-push" title="推送给商户"
                   data-url="${ctx}/dashboard/data/dz/${dzFile.id}/push">
                    手动推送
                </a>
            </div>
        <#else>
            不需要推送
        </#if>
    </td>
</tr>