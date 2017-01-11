<#assign ctx="${(rca.contextPath)!''}">

<tr id="bankChannel-${bankChannel.id}">
    <td>${bankChannel.bnkCo}</td>
    <td>${bankChannel.bnkNm}</td>
    <td><@c.relative_date datetime=bankChannel.createdTime/></td>
    <td><@c.relative_date datetime=bankChannel.updatedTime/></td>
    <td>
        <div class="btn-group">
            <a href="${ctx}/dashboard/bank/channel/${bankChannel.id}/edit" class="btn btn-xs btn-inverse" data-toggle="modal" data-target="#myModal"
               data-backdrop="static">编辑</a>
        </div>
    </td>
</tr>