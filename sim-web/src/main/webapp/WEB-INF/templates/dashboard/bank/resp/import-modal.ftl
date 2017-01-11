<#assign ctx="${(rca.contextPath)!''}">
<#assign modal_title="批量导入错误码" />

<@override name="modal-body">
<form class="form-horizontal" role="form" id="modal-form" method="post" enctype="multipart/form-data"
      action="${ctx}/dashboard/bank/resp/import">
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>银行通道<span class="red">*</span></label>
            </div>
            <div class="col-md-7 controls">
                <select id="bnkCo" name="bnkCo" class="form-control">
                    <#list bankChannels as bankChannel>
                        <option value="${bankChannel.bnkCo}">${bankChannel.bnkNm}[${bankChannel.bnkCo}]</option>
                    </#list>
                </select>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>选择Excel<span class="red">*</span></label>
            </div>
            <div class="col-md-7 controls">
                <input type="file" name="excel" id="excel">
                <div class="help-block">
                    请上传 xlsx、xls 格式的文件，文件大小不能超过10MB。<br>
                    建议下载模板，在模板的基础上修改。
                </div>
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

<script src="${ctx}/static/app/js/dashboard/bank/resp/import-modal.js"></script>
</@override>

<@extends name="../../../modal-layout.ftl"/>