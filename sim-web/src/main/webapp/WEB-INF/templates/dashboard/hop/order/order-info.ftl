<#if order.orderType == "1">
<span>${order.companyNo}|${order.subCompanyNo}|${order.partnerCustNo}|${order.custType}|${order.name}|${order.certType}|${order.certNo}|${order.mobile}|${order.bankCardNo}|${order.riskLevel}|${order.remark}</span>
<#elseif order.orderType == "2">
<span>${order.companyNo}|${order.subCompanyNo}|${order.serialNo}|${order.partnerCustNo}|${order.tradeAcct}|${order.prodId}|${order.amount}|${order.share}|${order.paymentType}|${order.bankCardNo}|${order.chargeType}|${order.apkind}</span>
</#if>