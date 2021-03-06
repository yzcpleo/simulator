<#assign ctx="${(rca.contextPath)!''}">

<tr id="bankTran-${bankTran.id}">
    <td>${bankTran.bnkNm}[${bankTran.bnkCo}]</td>
    <td>${bankTran.tranNm}[${bankTran.tranCo}]</td>
    <td title="${bankTran.respMsg!''}"><#if bankTran.respCo!=''><@c.substring str="${bankTran.respMsg}" len=10/>[${bankTran.respCo}]</#if></td>
    <td title="${bankTran.dzTemp!''}"><@c.substring str="${bankTran.dzTemp!''}" len=60/></td>
    <td><#include "gen-push.ftl"/></td>
    <td><@c.relative_date datetime=bankTran.updatedTime/></td>
    <td>
        <div class="btn-group">
            <a href="${ctx}/dashboard/bank/tran/${bankTran.id}/edit" class="btn btn-xs btn-inverse" data-toggle="modal"
               data-target="#myModal" data-backdrop="static">编辑</a>

            <button data-toggle="dropdown" class="btn btn-xs btn-inverse dropdown-toggle">
                <span class="ace-icon fa fa-caret-down icon-only"></span>
            </button>

            <ul class="dropdown-menu dropdown-menu-right dropdown-inverse">
                <li>
                    <a href="${ctx}/dashboard/bank/tran/${bankTran.id}/resp" data-toggle="modal"
                       data-target="#myModal" data-backdrop="static">配置错误码</a>
                </li>
            </ul>
        </div>
    </td>
</tr>