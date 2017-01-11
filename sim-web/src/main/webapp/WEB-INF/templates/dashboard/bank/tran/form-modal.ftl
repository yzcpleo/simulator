<#assign ctx="${(rca.contextPath)!''}">
<#assign modal_title="${bankTran.id???string('编辑交易类型', '添加交易类型')}" />

<@override name="modal-body">
<form class="form-horizontal" role="form" id="modal-form" method="post"
      action="${ctx}/dashboard/bank/tran/${bankTran.id???string('update', 'save')}">
    <#if bankTran.id??>
        <input type="hidden" name="id" value="${bankTran.id}"/>
    </#if>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>银行通道<span class="red">*</span></label>
            </div>
            <div class="col-md-7 controls">
                <#if bankTran.id??>
                    <input type="text" value="${bankTran.bnkNm}" class="form-control readonly" readonly/>
                <#else>
                    <select id="bnkCo" name="bnkCo" class="form-control">
                        <#list bankChannels as bankChannel>
                            <option value="${bankChannel.bnkCo}"
                                    <#if bankTran.id?? && bankTran.bnkCo==bankChannel.bnkCo>selected</#if>>${bankChannel.bnkNm}
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
                <label>交易码<span class="red">*</span></label>
            </div>
            <div class="col-md-7 controls">
                <@spring.formInput "bankTran.tranCo" 'class="form-control" placeholder="2至16个字符"'/>
                <input type="hidden" id="old-tranCo" value="${bankTran.tranCo!''}"/>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>交易名称<span class="red">*</span></label>
            </div>
            <div class="col-md-7 controls">
                <@spring.formInput "bankTran.tranNm" 'class="form-control" placeholder="2至16个字符"'/>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>对账文件模板</label>
            </div>
            <div class="col-md-7 controls">
                <@spring.formInput "bankTran.dzTemp" 'class="form-control" placeholder="对账文件中每行的模板"'/>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>是否生成</label>
            </div>
            <div class="col-md-7 controls">
                <label class="margin-radio">
                    <input name="isGenDz" value="0" type="radio" class="ace" <#if !bankTran.id?? || bankTran.isGenDz==0>checked</#if>/>
                    <span class="lbl">不生成</span>
                </label>
                <label>
                    <input name="isGenDz" value="1" type="radio" class="ace"<#if bankTran.id?? && bankTran.isGenDz==1>checked</#if>/>
                    <span class="lbl">生成</span>
                </label>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>是否推送</label>
            </div>
            <div class="col-md-7 controls">
                <label class="margin-radio">
                    <input name="isPushDz" value="0" type="radio" class="ace" <#if !bankTran.id?? || bankTran.isPushDz==0>checked</#if>/>
                    <span class="lbl">不推送</span>
                </label>
                <label>
                    <input name="isPushDz" value="1" type="radio" class="ace"<#if bankTran.id?? && bankTran.isPushDz==1>checked</#if>/>
                    <span class="lbl">推送</span>
                </label>
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

<script src="${ctx}/static/app/js/dashboard/bank/tran/form-modal.js"></script>
</@override>

<@extends name="../../../modal-layout.ftl"/>