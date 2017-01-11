<#assign ctx="${(rca.contextPath)!''}">
<#assign modal_title="${bankChannel.id???string('编辑银行通道', '添加银行通道')}" />

<@override name="modal-body">
<form class="form-horizontal" role="form" id="modal-form" method="post"
      action="${ctx}/dashboard/bank/channel/${bankChannel.id???string('update', 'save')}">
    <#if bankChannel.id??>
        <input type="hidden" name="id" value="${bankChannel.id}"/>
    </#if>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>银行代码<span class="red">*</span></label>
            </div>
            <div class="col-md-7 controls">
                <@spring.formInput "bankChannel.bnkCo" 'class="form-control" placeholder="2至12位字母或数字的组合"'/>
                <input type="hidden" id="old-bnkCo" value="${bankChannel.bnkCo!''}"/>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>银行名称<span class="red">*</span></label>
            </div>
            <div class="col-md-7 controls">
                <@spring.formInput "bankChannel.bnkNm" 'class="form-control" placeholder="2至16个字符"'/>
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

<script src="${ctx}/static/app/js/dashboard/bank/channel/form-modal.js"></script>
</@override>

<@extends name="../../../modal-layout.ftl"/>