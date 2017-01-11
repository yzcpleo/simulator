<#if bankCommand.tranSt == "Y">
<span class="label label-success arrowed-in-right">交易成功</span>
<#elseif bankCommand.tranSt == "F">
<span class="label label-danger arrowed-in-right">交易失败</span>
<#elseif bankCommand.tranSt == "I">
<span class="label label-yellow arrowed-in-right">交易处理中</span>
<#else>
<span class="label label-success arrowed-in-right">${bankCommand.tranSt}</span>
</#if>