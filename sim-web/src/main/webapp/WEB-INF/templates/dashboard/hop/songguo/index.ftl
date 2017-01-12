<#assign ctx="${(rca.contextPath)!''}">

<div class="space-10"></div>

<div class="page-header">
    <h1>
        松果金融
        <small class="pull-right">
            <a id="0CIF000002-btn" class="btn btn-sm btn-inverse" data-backdrop="static">开户</a>
            <a id="0CIF000004-btn" class="btn btn-sm btn-inverse" data-backdrop="static">绑卡短信验证码发送</a>
            <a id="0CIF000005-btn" class="btn btn-sm btn-inverse" data-backdrop="static">绑卡</a>
            <a id="0CIF000006-btn" class="btn btn-sm btn-inverse" data-backdrop="static">绑卡结果查询</a>
            <a id="0CTSTRD001-btn" class="btn btn-sm btn-inverse" data-backdrop="static">现金宝实时交易</a>
            <a id="0CTSTRD002-btn" class="btn btn-sm btn-inverse" data-backdrop="static">定期宝实时交易</a>
            <a id="1CTSTRQ001-btn" class="btn btn-sm btn-inverse" data-backdrop="static">单笔订单查询</a>
            <a id="1CTSTRQ004-btn" class="btn btn-sm btn-inverse" data-backdrop="static">交易查询</a>
            <a id="1CTSPRD002-btn" class="btn btn-sm btn-inverse" data-backdrop="static">产品状态查询</a>
            <a id="1CTSSHA003-btn" class="btn btn-sm btn-inverse" data-backdrop="static">客户产品份额查询</a>
            <a id="1CTSSHA006-btn" class="btn btn-sm btn-inverse" data-backdrop="static">客户产品份额批量查询</a>
            <a id="1CTSSHA007-btn" class="btn btn-sm btn-inverse" data-backdrop="static">现金宝快取、普取额度查询</a>
        </small>
    </h1>
</div>

<div class="space-10"></div>

<div id="0CIF000004">
    <form class="form-horizontal" role="form" id="form" method="post" action="${ctx}/dashboard/hop/songguo/post2hop">
        <div id="hjg">
            <div class="row">
                <div class="form-group">
                    <label for="" class="col-xs-12 col-sm-3 col-md-3 control-label no-padding-right">Request:</label>
                    <div class="col-xs-12 col-sm-6">
                    <span class="block input-icon input-icon-right">
                        <textarea id="request" name="request" class="autosize-transition form-control"
                                  style="overflow: hidden; word-wrap: break-word; resize: horizontal; height: 300px;"></textarea>
                     </span>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="form-group">
                    <label for="" class="col-xs-12 col-sm-3 col-md-3 control-label no-padding-right">Response:</label>
                    <div class="col-xs-12 col-sm-6">
                    <span class="block input-icon input-icon-right">
                        <textarea id="response" name="response" class="autosize-transition form-control"
                                  style="overflow: hidden; word-wrap: break-word; resize: horizontal; height: 300px;"></textarea>
                     </span>
                    </div>
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
</div>
<script src="${ctx}static/app/js/dashboard/hop/songguo/index.js"></script>
