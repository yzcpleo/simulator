<#assign ctx="${(rca.contextPath)!''}">

<div class="space-30"></div>
<form action="${ctx}/dashboard/user/password" id="form" method="post" class="form-horizontal">
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right">新密码<span class="red">*</span></label>
        <div class="col-xs-12 col-sm-5">
            <input type="text" name="password" class="form-control" placeholder="密码:6至20位的字母数字组合" autocomplete="off"/>
        </div>
    </div>

    <div class="space-20"></div>
    <div class="clearfix form-actions">
        <div class="col-xs-offset-3">
            <button id="submit" class="btn btn-inverse" data-loading-text="正在提交...">
                <i class="ace-icon fa fa-check"></i>
            <@spring.message "app.button.save"/>
            </button>
        </div>
    </div>
</form>

<script src="${ctx}/static/app/js/dashboard/user/password.js"></script>
