$(function () {
    updateState("hop/songguo");

    var $form = $("#form");
    var $btn = $("#submit");

    $form.validate({
        errorPlacement: function (error, element) {
            error.appendTo(element.parent());
        },
        submitHandler: function (form, event) {
            event.preventDefault();
            $btn.button('loading');
            $(form).ajaxSubmit({
                dataType: 'json',
                success: function (response) {
                    if (response.errCode == 'success') {
                        Message.success("修改成功");
                        $("#request").text(response.request);
                        $("#response").text(response.response);
                    } else {
                        Message.error(response.errMsg);
                    }
                    $btn.button('reset');
                },
                error: function () {
                    Message.error("服务器内部错误，请稍后再试。");
                    $btn.button('reset');
                }
            });
        },
        errorElement: "div",
        errorClass: "error"
    });

    var requestStr = document.getElementById("request").value;
    var responseStr = document.getElementById("response").value;

    if (responseStr && typeof(responseStr) != "undefined" && responseStr != 0) {
        var reqObj = JSON.parse(requestStr);
        var reqPretty = JSON.stringify(reqObj, undefined, 4);
        document.getElementById("request").innerHTML = reqPretty;

        var respObj = JSON.parse(responseStr);
        var respPretty = JSON.stringify(respObj, undefined, 4);
        document.getElementById("response").innerHTML = respPretty;
    }


    $("#0CIF000004").click(function () {
        jsonString = "{\n" +
            "  \"interfaceId\": \"0CIF000004\",\n" +
            "  \"companyNo\": \"9003\",\n" +
            "  \"subCompanyNo\": \"90030000\",\n" +
            "  \"signType\": \"01\",\n" +
            "  \"signature\": \"可以不填\",\n" +
            "  \"content\": \"{\\\"bizType\\\": \\\"${bizType}\\\",\\\"mobile\\\": \\\"${mobile}\\\",\\\"cardNo\\\": \\\"${cardNo}\\\",\\\"bankNo\\\": \\\"507\\\",\\\"name\\\": \\\"${name}\\\",\\\"certType\\\": \\\"0\\\",\\\"certNo\\\": \\\"${certNo}\\\",\\\"partnerCustNo\\\": \\\"${partnerCustNo}\\\",\\\"custType\\\": \\\"${custType}\\\",\\\"riskLevel\\\": \\\"${riskLevel}\\\"}\"\n" +
            "}";
        var jsonObj = JSON.parse(jsonString);
        var pretty = JSON.stringify(jsonObj, undefined, 4);

        document.getElementById("request").innerHTML = pretty;
    });

    $("#0CIF000005-btn").click(function () {
        jsonString = "{\n" +
            "  \"interfaceId\": \"0CIF000005\",\n" +
            "  \"companyNo\": \"9003\",\n" +
            "  \"subCompanyNo\": \"90030000\",\n" +
            "  \"signType\": \"01\",\n" +
            "  \"signature\": \"可以不填\",\n" +
            "  \"content\": \"{\\\"smsSerialNo\\\": \\\"${smsSerialNo}\\\",\\\"bankSmsSerialNo\\\": \\\"${bankSmsSerialNo}\\\",\\\"mobileCode\\\": \\\"mobileCode\\\"}\"\n" +
            "}";
        var jsonObj = JSON.parse(jsonString);
        var pretty = JSON.stringify(jsonObj, undefined, 4);

        document.getElementById("request").innerHTML = pretty;
    });

    $("#0CIF000006-btn").click(function () {
        jsonString = "{\n" +
            "  \"interfaceId\": \"0CIF000006\",\n" +
            "  \"companyNo\": \"9003\",\n" +
            "  \"subCompanyNo\": \"90030000\",\n" +
            "  \"signType\": \"01\",\n" +
            "  \"signature\": \"可以不填\",\n" +
            "  \"content\": \"{\\\"serialNo\\\": \\\"${serialNo}\\\",\\\"bankNo\\\": \\\"${bankNo}\\\",\\\"cardNo\\\": \\\"${cardNo}\\\"}\"\n" +
            "}";
        var jsonObj = JSON.parse(jsonString);
        var pretty = JSON.stringify(jsonObj, undefined, 4);

        document.getElementById("request").innerHTML = pretty;
    });

    $("#0CTSTRD001-btn").click(function () {
        jsonString = "{\n" +
            "  \"interfaceId\": \"0CTSTRD001\",\n" +
            "  \"companyNo\": \"9003\",\n" +
            "  \"subCompanyNo\": \"90030000\",\n" +
            "  \"signType\": \"01\",\n" +
            "  \"signature\": \"可以不填\",\n" +
            "  \"content\": \"{\\\"serialNo\\\": \\\"${serialNo}\\\",\\\"tradeAcct\\\": \\\"${tradeAcct}\\\",\\\"prodId\\\": \\\"${prodId}\\\",\\\"amount\\\": \\\"${amount}\\\",\\\"share\\\": \\\"${share}\\\",\\\"paymentType\\\": \\\"${paymentType}\\\",\\\"bankAccount\\\": \\\"${bankAccount}\\\",\\\"bankNo\\\": \\\"${bankNo}\\\",\\\"chargeType\\\": \\\"${chargeType}\\\",\\\"apkind\\\": \\\"${apkind}\\\"}\"\n" +
            "}";
        var jsonObj = JSON.parse(jsonString);
        var pretty = JSON.stringify(jsonObj, undefined, 4);

        document.getElementById("request").innerHTML = pretty;
    });


    $("#0CTSTRD002-btn").click(function () {
        jsonString = "{\n" +
            "  \"interfaceId\": \"0CTSTRD002\",\n" +
            "  \"companyNo\": \"9003\",\n" +
            "  \"subCompanyNo\": \"90030000\",\n" +
            "  \"signType\": \"01\",\n" +
            "  \"signature\": \"可以不填\",\n" +
            "  \"content\": \"{\\\"serialNo\\\": \\\"${serialNo}\\\",\\\"tradeAcco\\\": \\\"${tradeAcco}\\\",\\\"prodId\\\": \\\"${prodId}\\\",\\\"amount\\\": \\\"${amount}\\\",\\\"paymentType\\\": \\\"${paymentType}\\\",\\\"bankAccount\\\": \\\"${bankAccount}\\\",\\\"bankNo\\\": \\\"${bankNo}\\\",\\\"chargeType\\\": \\\"${chargeType}\\\",\\\"apkind\\\": \\\"${apkind}\\\"}\"\n" +
            "}";
        var jsonObj = JSON.parse(jsonString);
        var pretty = JSON.stringify(jsonObj, undefined, 4);

        document.getElementById("request").innerHTML = pretty;
    });

    $("#1CTSTRQ001-btn").click(function () {
        jsonString = "{\n" +
            "  \"interfaceId\": \"1CTSTRQ001\",\n" +
            "  \"companyNo\": \"9003\",\n" +
            "  \"subCompanyNo\": \"90030000\",\n" +
            "  \"signType\": \"01\",\n" +
            "  \"signature\": \"可以不填\",\n" +
            "  \"content\": \"{\\\"serialNo\\\": \\\"${serialNo}\\\"}\"\n" +
            "}";
        var jsonObj = JSON.parse(jsonString);
        var pretty = JSON.stringify(jsonObj, undefined, 4);

        document.getElementById("request").innerHTML = pretty;
    });


    $("#1CTSPRD002-btn").click(function () {
        jsonString = "{\n" +
            "  \"interfaceId\": \"1CTSPRD002\",\n" +
            "  \"companyNo\": \"9003\",\n" +
            "  \"subCompanyNo\": \"90030000\",\n" +
            "  \"signType\": \"01\",\n" +
            "  \"signature\": \"可以不填\",\n" +
            "  \"content\": \"{\\\"ProdType\\\": \\\"${ProdType}\\\",\\\"prodId\\\": \\\"${prodId}\\\"}\"\n" +
            "}";
        var jsonObj = JSON.parse(jsonString);
        var pretty = JSON.stringify(jsonObj, undefined, 4);

        document.getElementById("request").innerHTML = pretty;
    });


    $("#1CTSSHA003-btn").click(function () {
        jsonString = "{\n" +
            "  \"interfaceId\": \"1CTSSHA003\",\n" +
            "  \"companyNo\": \"9003\",\n" +
            "  \"subCompanyNo\": \"90030000\",\n" +
            "  \"signType\": \"01\",\n" +
            "  \"signature\": \"可以不填\",\n" +
            "  \"content\": \"{\\\"prodId\\\": \\\"${prodId}\\\",\\\"tradeAcco\\\": \\\"${tradeAcco}\\\"}\"\n" +
            "}";
        var jsonObj = JSON.parse(jsonString);
        var pretty = JSON.stringify(jsonObj, undefined, 4);

        document.getElementById("request").innerHTML = pretty;
    });

    $("#0CIF000002-btn").click(function () {
        jsonString = "{\n" +
            "  \"interfaceId\": \"0CIF000002\",\n" +
            "  \"companyNo\": \"9003\",\n" +
            "  \"subCompanyNo\": \"90030000\",\n" +
            "  \"signType\": \"01\",\n" +
            "  \"signature\": \"可以不填\",\n" +
            "  \"content\": \"{\\\"partnerCustNo\\\": \\\"${partnerCustNo}\\\",\\\"custType\\\": \\\"${custType}\\\",\\\"name\\\": \\\"${name}\\\",\\\"certType\\\": \\\"${certType}\\\",\\\"certNo\\\": \\\"${certNo}\\\",\\\"mobile\\\": \\\"${mobile}\\\",\\\"riskLevel\\\": \\\"${riskLevel}\\\",\\\"remark\\\": \\\"${remark}\\\"}\"\n" +
            "}";
        var jsonObj = JSON.parse(jsonString);
        var pretty = JSON.stringify(jsonObj, undefined, 4);

        document.getElementById("request").innerHTML = pretty;
    });


    $("#1CTSTRQ004-btn").click(function () {
        jsonString = "{\n" +
            "  \"interfaceId\": \"1CTSTRQ004\",\n" +
            "  \"companyNo\": \"9003\",\n" +
            "  \"subCompanyNo\": \"90030000\",\n" +
            "  \"signType\": \"01\",\n" +
            "  \"signature\": \"可以不填\",\n" +
            "  \"content\": \"{\\\"prodId\\\": \\\"${prodId}\\\",\\\"tradeAcco\\\": \\\"${tradeAcco}\\\",\\\"pageNum\\\": \\\"${pageNum}\\\",\\\"pageSize\\\": \\\"${pageSize}\\\"}\"\n" +
            "}";
        var jsonObj = JSON.parse(jsonString);
        var pretty = JSON.stringify(jsonObj, undefined, 4);

        document.getElementById("request").innerHTML = pretty;
    });


    $("#1CTSSHA006-btn").click(function () {
        jsonString = "{\n" +
            "  \"interfaceId\": \"1CTSSHA006\",\n" +
            "  \"companyNo\": \"9003\",\n" +
            "  \"subCompanyNo\": \"90030000\",\n" +
            "  \"signType\": \"01\",\n" +
            "  \"signature\": \"可以不填\",\n" +
            "  \"content\": \"{\\\"tradeAcco\\\": \\\"${tradeAcco}\\\",\\\"pageNum\\\": \\\"${pageNum}\\\",\\\"pageSize\\\": \\\"${pageSize}\\\"}\"\n" +
            "}";
        var jsonObj = JSON.parse(jsonString);
        var pretty = JSON.stringify(jsonObj, undefined, 4);

        document.getElementById("request").innerHTML = pretty;
    });


    $("#1CTSSHA007-btn").click(function () {
        jsonString = "{\n" +
            "  \"interfaceId\": \"1CTSSHA007\",\n" +
            "  \"companyNo\": \"9003\",\n" +
            "  \"subCompanyNo\": \"90030000\",\n" +
            "  \"signType\": \"01\",\n" +
            "  \"signature\": \"可以不填\",\n" +
            "  \"content\": \"{\\\"prodId\\\": \\\"${prodId}\\\",\\\"tradeAcco\\\": \\\"${tradeAcco}\\\",\\\"bankAccount:\\\" \\\"${bankAccount}\\\",\\\"bankNo:\\\" \\\"${bankNo}\\\"}\"\n" +
            "}";
        var jsonObj = JSON.parse(jsonString);
        var pretty = JSON.stringify(jsonObj, undefined, 4);

        document.getElementById("request").innerHTML = pretty;
    });

});