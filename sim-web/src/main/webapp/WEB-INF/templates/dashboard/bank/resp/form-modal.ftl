<#assign ctx="${(rca.contextPath)!''}">
<#assign modal_title="${bankResp.id???string('编辑交易类型', '添加交易类型')}" />

<@override name="modal-body">
<form class="form-horizontal" role="form" id="modal-form" method="post"
      action="${ctx}/dashboard/bank/resp/${bankResp.id???string('update', 'save')}">
    <#if bankResp.id??>
        <input type="hidden" name="id" value="${bankResp.id}"/>
    </#if>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>银行通道<span class="red">*</span></label>
            </div>
            <div class="col-md-7 controls">
                <#if bankResp.id??>
                    <input type="text" value="${bankResp.bnkNm}" class="form-control readonly" readonly/>
                <#else>
                    <select id="bnkCo" name="bnkCo" class="form-control">
                        <#list bankChannels as bankChannel>
                            <option value="${bankChannel.bnkCo}"
                                    <#if bankResp.id?? && bankResp.bnkCo==bankChannel.bnkCo>selected</#if>>${bankChannel.bnkNm}
                                [${bankChannel.bnkCo}]
                            </option>
                        </#list>
                    </select>
                </#if>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>错误码<span class="red">*</span></label>
            </div>
            <div class="col-md-7 controls">
                <@spring.formInput "bankResp.respCo" 'class="form-control" placeholder="1至16个字符"'/>
                <input type="hidden" id="old-respCo" value="${bankResp.respCo!''}"/>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>错误描述<span class="red">*</span></label>
            </div>
            <div class="col-md-7 controls">
                <@spring.formInput "bankResp.respMsg" 'class="form-control" placeholder="1至128个字符"'/>
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

<script src="${ctx}/static/app/js/dashboard/bank/resp/form-modal.js"></script>
</@override>

<@extends name="../../../modal-layout.ftl"/>