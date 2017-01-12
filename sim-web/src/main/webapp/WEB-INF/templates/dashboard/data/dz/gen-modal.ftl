<#assign ctx="${(rca.contextPath)!''}">
<#assign modal_title="手动生成对账文件" />

<@override name="modal-body">
<link rel="stylesheet" href="${ctx}/static/ace/dist/css/bootstrap-datetimepicker.min.css"/>
<form class="form-horizontal" role="form" id="modal-form" method="post"
      action="${ctx}/dashboard/data/dz/gen">
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>对账交易<span class="red">*</span></label>
            </div>
            <div class="col-md-7 controls">
                <select name="id" class="form-control">
                    <#list bankTrans as bankTran>
                        <option value="${bankTran.id}"><@c.substring str="${bankTran.bnkNm}[${bankTran.bnkCo}] - ${bankTran.tranNm}" len=26/>
                            [${bankTran.tranCo}]
                        </option>
                    </#list>
                </select>
            </div>
        </div>

        <div class="form-group">
            <div class="col-md-3 control-label">
                <label>对账日期<span class="red">*</span></label>
            </div>
            <div class="col-md-7 controls">
                <input type="datetime" id="workDay" name="workDay" class="form-control"/>
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

<script src="${ctx}static/libs/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
<script src="${ctx}static/libs/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="${ctx}/static/app/js/dashboard/data/dz/gen-modal.js"></script>
</@override>

<@extends name="../../../modal-layout.ftl"/>