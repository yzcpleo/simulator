<#assign modal_title="设置角色" />

<@override name="modal-body">
<form class="form-horizontal" role="form" id="modal-form" method="post"
      action="${ctx}/dashboard/system/user/${userId}/roles">
    <div class="control-group">
        <#list all_roles as role>
            <div class="checkbox">
                <label>
                    <input name="roles" type="checkbox" value="${role.code}"
                           class="ace" ${user_roles?seq_contains(role.code)?string("checked", "")}/>
                    <span class="lbl"> ${role.name} </span>
                </label>
            </div>
        </#list>
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
<script src="${ctx}/static/app/js/dashboard/system/user/roles-modal.js"></script>
</@override>

<@extends name="../../../modal-layout.ftl"/>