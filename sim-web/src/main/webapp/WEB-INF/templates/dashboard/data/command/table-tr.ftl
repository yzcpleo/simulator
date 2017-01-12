<#assign ctx="${(rca.contextPath)!''}">

<tr id="bankCommand-${bankCommand.id}">
    <td>${bankCommand.bnkNm}[${bankCommand.bnkCo}]</td>
    <td>${bankCommand.tranNm}[${bankCommand.tranCo}]</td>
    <td>${bankCommand.merSerialNo}</td>
    <td>${bankCommand.respMsg}[${bankCommand.respCo}]</td>
    <td><#include "tran-st.ftl"/></td>
    <td><@c.relative_date datetime=bankCommand.createdTime/></td>
    <td>
        敬请期待
        <#--<div class="btn-group">-->
            <#--<a href="#data/command/${bankCommand.id}/edit" class="btn btn-xs btn-inverse">查看</a>-->

            <#--<button data-toggle="dropdown" class="btn btn-xs btn-inverse dropdown-toggle">-->
                <#--<span class="ace-icon fa fa-caret-down icon-only"></span>-->
            <#--</button>-->

            <#--<ul class="dropdown-menu dropdown-menu-right dropdown-inverse">-->
                <#--<li>-->
                    <#--<a href="javascript:" data-role="bankCommand-success" title="置交易为成功"-->
                       <#--data-url="${ctx}/dashboard/data/command/${bankCommand.id}/success">-->
                        <#--置为成功-->
                    <#--</a>-->
                <#--</li>-->
            <#--</ul>-->
        <#--</div>-->
    </td>
</tr>