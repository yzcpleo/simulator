<#assign ctx="${(rca.contextPath)!''}">

<div class="space-10"></div>
<form id="form" action="${ctx}/dashboard/user/profile" method="post" enctype="multipart/form-data"
      class="form-horizontal">
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right">用户名</label>
        <div class="col-xs-12 col-sm-5">
            <input type="text" name="username" value="${user.username!''}" class="form-control readonly" readonly/>
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right">姓名<span class="red">*</span></label>
        <div class="col-xs-12 col-sm-5">
        <@spring.formInput "user.fullname" 'class="form-control" placeholder="2至4个汉字"'/>
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="title">当前头像</label>

        <div class="col-xs-12 col-sm-5">
            <span class="profile-picture">
                <img id="largeAvatar" class="editable img-responsive"
                     src="<#if user.largeAvatar?has_content>${ctx}/${user.largeAvatar}<#else>${ctx}/static/ace/dist/avatars/profile-pic.jpg</#if>"></img>
            </span>
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="title">新头像</label>

        <div class="col-xs-12 col-sm-5">
            <input type="file" name="avatar" id="avatar">
            <div class="help-block">
                请上传 png、gif、jpg 格式的图片文件，文件大小不能超过2MB。<br>
                建议上传一张 124*124 像素或等比例的图片。
            </div>
        </div>
    </div>

    <div class="clearfix form-actions">
        <div class="col-xs-offset-3">
            <button id="submit" class="btn btn-inverse" data-loading-text="正在提交...">
                <i class="ace-icon fa fa-check"></i>
            <@spring.message "app.button.save"/>
            </button>
        </div>
    </div>
</form>

<script src="${ctx}/static/app/js/dashboard/user/profile.js"></script>
