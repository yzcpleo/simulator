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
                <label>客户类型<span class="red">*</span></label>
            </div>
            <div class="col-md-7 controls">
                <@spring.formInput "order.custType" 'class="form-control"'/>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>客户姓名<span class="red">*</span></label>
            </div>
            <div class="col-md-7 controls">
                <@spring.formInput "order.name" 'class="form-control"'/>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>证件类型<span class="red">*</span></label>
            </div>
            <div class="col-md-7 controls">
                <@spring.formInput "order.certType" 'class="form-control"'/>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>证件号码<span class="red">*</span></label>
            </div>
            <div class="col-md-7 controls">
                <@spring.formInput "order.certNo" 'class="form-control"'/>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>手机号码<span class="red">*</span></label>
            </div>
            <div class="col-md-7 controls">
                <@spring.formInput "order.mobile" 'class="form-control"'/>
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
                <label>风险测评等级<span class="red">*</span></label>
            </div>
            <div class="col-md-7 controls">
                <@spring.formInput "order.riskLevel" 'class="form-control"'/>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>附加信息<span class="red">*</span></label>
            </div>
            <div class="col-md-7 controls">
                <@spring.formInput "order.remark" 'class="form-control"'/>
            </div>
        </div>
    </div>
    <input type="hidden" id="orderType" name="orderType" value="1">
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