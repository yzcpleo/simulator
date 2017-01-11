<#assign ctx="${(rca.contextPath)!''}">

<@override name="content">
<div id="login-box" class="login-box visible widget-box no-border">
    <div class="widget-body">
        <div class="widget-main">
            <h4 class="header grey lighter bigger">
                <i class="ace-icon fa fa-coffee green"></i>
                登录
            </h4>

            <div class="space-6"></div>

            <form id="login-form" action="${ctx}/login" method="post" novalidate="novalidate">
                <div>
                    <label for="username">用户名</label>
                    <div class="input-icon input-icon-right">
                        <input value="admin" type="text" name="username" class="form-control"
                               placeholder="用户名:5至20位以字母开头的小写字母和数字的组合">
                        <i class="ace-icon fa fa-user"></i>
                    </div>
                </div>

                <div class="space space-8"></div>

                <div>
                    <label for="password">密码</label>
                    <div class="input-icon input-icon-right">
                        <input value="123456" type="password" name="password" class="form-control"
                               placeholder="密码:6至20位的字母数字组合">
                        <i class="ace-icon fa fa-key"></i>
                    </div>
                </div>

                <div class="space space-8"></div>

                <div>
                    <label for="captcha">验证码</label>
                    <div class="input-icon input-icon-right">
                        <input type="text" name="captcha" class="col-xs-6" placeholder="请输入4位数字的验证码" autocomplete="off">
                        <img onclick="this.src='${ctx}/captcha?'+Math.random();" src="/captcha">
                    </div>
                </div>

                <div class="space-14"></div>

                <div class="clearfix">
                    <button type="reset" class="width-30 pull-left btn btn-sm">
                        <i class="ace-icon fa fa-refresh"></i>
                        重置
                    </button>
                    <button id="submit" class="width-35 pull-right btn btn-sm btn-inverse"
                            data-loading-text="登录中...">
                        <i class="ace-icon fa fa-key"></i>
                        登录
                    </button>
                </div>
            </form>
        </div>

        <div class="toolbar clearfix">
            <div>
                <a href="javascript:" class="forgot-password-link">
                    <i class="ace-icon fa fa-arrow-left"></i>
                    忘记密码？
                </a>
            </div>
            <div>
                <a href="javascript:" class="user-signup-link">
                    注册
                    <i class="ace-icon fa fa-arrow-right"></i>
                </a>
            </div>
        </div>
    </div>
</div>
</@override>

<@override name="script">
<script src="${ctx}/static/app/js/web/auth/login.js"></script>
</@override>

<@extends name="../auth-layout.ftl"/>