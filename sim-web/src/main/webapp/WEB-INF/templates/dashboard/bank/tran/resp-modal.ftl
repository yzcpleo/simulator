<#assign ctx="${(rca.contextPath)!''}">
<#assign modal_title="配置错误码" />

<@override name="modal-body">
<form class="form-horizontal" role="form" id="modal-form" method="post"
      action="${ctx}/dashboard/bank/tran/${bankTran.id}/resp">
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>错误码<span class="red">*</span></label>
            </div>
            <div class="col-md-7 controls">
                <select name="respCo" class="form-control">
                    <option value="">--不改变交易结果--</option>
                    <#list bankResps as bankResp>
                        <option value="${bankResp.respCo}"
                                <#if bankTran.respCo==bankResp.respCo>selected</#if>><@c.substring str="${bankResp.respMsg}" len=8/>
                            [${bankResp.respCo}]
                        </option>
                    </#list>
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

<script src="${ctx}/static/app/js/dashboard/bank/tran/resp-modal.js"></script>
</@override>

<@extends name="../../../modal-layout.ftl"/>