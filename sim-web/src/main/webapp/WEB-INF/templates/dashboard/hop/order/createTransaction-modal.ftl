<#assign ctx="${(rca.contextPath)!''}">
<#assign modal_title="添加" />

<@override name="modal-body">
<form class="form-horizontal" role="form" id="modal-form" method="post" action="${ctx}/dashboard/hop/order/save">
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>机构号<span class="red">*</span></label>
            </div>
            <div class="col-md-7 controls">
                <@spring.formInput "order.companyNo" 'class="form-control"'/>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>子机构号<span class="red">*</span></label>
            </div>
            <div class="col-md-7 controls">
                <@spring.formInput "order.subCompanyNo" 'class="form-control"'/>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>订单号<span class="red">*</span></label>
            </div>
            <div class="col-md-7 controls">
                <@spring.formInput "order.serialNo" 'class="form-control"'/>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>合作方客户编号<span class="red">*</span></label>
            </div>
            <div class="col-md-7 controls">
                <@spring.formInput "order.partnerCustNo" 'class="form-control"'/>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>交易帐号<span class="red">*</span></label>
            </div>
            <div class="col-md-7 controls">
                <@spring.formInput "order.tradeAcct" 'class="form-control"'/>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>产品代码<span class="red">*</span></label>
            </div>
            <div class="col-md-7 controls">
                <@spring.formInput "order.prodId" 'class="form-control"'/>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>申请金额<span class="red">*</span></label>
            </div>
            <div class="col-md-7 controls">
                <@spring.formInput "order.amount" 'class="form-control"'/>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>申请份额<span class="red">*</span></label>
            </div>
            <div class="col-md-7 controls">
                <@spring.formInput "order.share" 'class="form-control"'/>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>支付方式<span class="red">*</span></label>
            </div>
            <div class="col-md-7 controls">
                <@spring.formInput "order.paymentType" 'class="form-control"'/>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>银行卡号<span class="red">*</span></label>
            </div>
            <div class="col-md-7 controls">
                <@spring.formInput "order.bankCardNo" 'class="form-control"'/>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>收费方式<span class="red">*</span></label>
            </div>
            <div class="col-md-7 controls">
                <@spring.formInput "order.chargeType" 'class="form-control"'/>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>业务码<span class="red">*</span></label>
            </div>
            <div class="col-md-7 controls">
                <@spring.formInput "order.apkind" 'class="form-control"'/>
            </div>
        </div>
    </div>

    <input type="hidden" id="orderType" name="orderType" value="2">
</form>
</@override>

<@override name="modal-footer">
<button class="btn btn-sm" data-dismiss="modal">
    <i class="ace-icon fa fa-times"></i>
    <@spring.message "app.button.cancel"/>
</button>

<button class="btn btn-sm btn-inverse" id="submit" data-loading-text="正在保存..." data-toggle="form-submit"
        data-target="#modal-form">
    <i class="ace-icon fa fa-check"></i>
    <@spring.message "app.button.save"/>
</button>

<script src="${ctx}/static/app/js/dashboard/hop/order/create-modal.js"></script>
</@override>

<@extends name="../../../modal-layout.ftl"/>