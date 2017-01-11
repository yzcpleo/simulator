<#assign ctx="${(rca.contextPath)!''}">

<tr id="bankResp-${bankResp.id}">
    <td>${bankResp.bnkNm}[${bankResp.bnkCo}]</td>
    <td>${bankResp.respCo}</td>
    <td>${bankResp.respMsg}</td>
    <td><@c.relative_date datetime=bankResp.updatedTime/></td>
    <td>
        <div class="btn-group">
            <a href="${ctx}/dashboard/bank/resp/${bankResp.id}/edit" class="btn btn-xs btn-inverse" data-toggle="modal"
               data-target="#myModal"
               data-backdrop="static">编辑</a>

            <button data-toggle="dropdown" class="btn btn-xs btn-inverse dropdown-toggle">
                <span class="ace-icon fa fa-caret-down icon-only"></span>
            </button>

            <ul class="dropdown-menu dropdown-menu-right dropdown-inverse">
                <li>
                    <a href="javascript:" data-role="bankResp-delete" title="删除错误码"
                       data-url="${ctx}/dashboard/bank/resp/${bankResp.id}/delete">
                        物理删除
                    </a>
                </li>
            </ul>
        </div>
    </td>
</tr>