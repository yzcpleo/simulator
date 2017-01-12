<#assign ctx="${(rca.contextPath)!''}">
<#assign modal_title="生成文件" />

<@override name="modal-body">
<form class="form-horizontal" role="form" id="modal-form" method="post" action="${ctx}/dashboard/hop/order/genFile">
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>交易类型<span class="red">*</span></label>
            </div>
            <div class="col-md-7 controls">
                <select id="orderType" name="orderType" class="form-control">
                    <option value="1">开户</option>
                    <option value="2">交易</option>
                </select>
            </div>
        </div>
    </div>
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

<script src="${ctx}/static/app/js/dashboard/hop/order/fileCreate-modal.js"></script>
</@override>

<@extends name="../../../modal-layout.ftl"/>