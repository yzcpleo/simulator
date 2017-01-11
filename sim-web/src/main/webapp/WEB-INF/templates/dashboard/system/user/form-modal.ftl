<#assign ctx="${(rca.contextPath)!''}">
<#assign modal_title="${user.id???string('编辑用户', '添加新用户')}" />

<@override name="modal-body">
<form class="form-horizontal" role="form" id="modal-form" method="post"
      action="${ctx}/dashboard/system/user/${user.id???string('update', 'save')}">
    <#if user.id??>
        <input type="hidden" name="id" value="${user.id}"/>
    </#if>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>用户名<span class="red">*</span></label>
            </div>
            <div class="col-md-7 controls">
                <@spring.formInput "user.username" 'class="form-control" placeholder="5至20位以字母开头的小写字母和数字的组合"'/>
                <input type="hidden" id="old-username" value="${user.username!''}"/>
            </div>
        </div>
    </div>
    <#if !user.id??>
        <div class="row">
            <div class="row form-group">
                <div class="col-md-3 control-label">
                    <label>密码<span class="red">*</span></label>
                </div>
                <div class="col-md-7 controls">
                    <input type="text" name="password" class="form-control" placeholder="6至20位的字母数字组合"/>
                </div>
            </div>
        </div>
    </#if>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>姓名<span class="red">*</span></label>
            </div>
            <div class="col-md-7 controls">
                <@spring.formInput "user.fullname" 'class="form-control" placeholder="2至4个汉字"'/>
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

<button class="btn btn-sm btn-inverse" id="submit" data-loading-text="正在保存..." data-toggle="form-submit" data-target="#modal-form">
    <i class="ace-icon fa fa-check"></i>
    <@spring.message "app.button.save"/>
</button>

<script src="${ctx}/static/app/js/dashboard/system/user/form-modal.js"></script>
</@override>

<@extends name="../../../modal-layout.ftl"/>