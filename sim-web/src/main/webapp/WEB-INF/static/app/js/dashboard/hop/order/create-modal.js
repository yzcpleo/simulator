$(function () {
    var $form = $('#modal-form');
    var $btn = $("#submit");

    $form.validate({
        rules: {
            companyNo: {
                required: true,
                rangelength: [1, 4]
            },
            subCompanyNo: {
                required: true,
                rangelength: [1, 8]
            },
            partnerCustNo: {
                required: true,
                rangelength: [1, 20]
            },
            custType: {
                required: true,
                rangelength: [1, 1]
            },
            name: {
                required: true,
                rangelength: [1, 40]
            },
            certType: {
                required: true,
                rangelength: [1, 2]
            },
            certNo: {
                required: true,
                rangelength: [1, 20]
            },
            mobile: {
                required: true,
                rangelength: [1, 11]
            },
            bankCardNo: {
                required: true,
                rangelength: [1, 32]
            },
            riskLevel: {
                required: true,
                rangelength: [1, 1]
            },
            remark: {
                required: true,
                rangelength: [1, 256]
            },
            serialNo: {
                required: true,
                rangelength: [1, 32]
            },
            tradeAcct: {
                required: true,
                rangelength: [1, 32]
            },
            prodId: {
                required: true,
                rangelength: [1, 32]
            },
            amount: {
                required: true,
                rangelength: [1, 16]
            },
            share: {
                required: true,
                rangelength: [1, 16]
            },
            paymentType: {
                required: true,
                rangelength: [1, 1]
            },
            bankCardNo: {
                required: true,
                rangelength: [1, 32]
            },
            chargeType: {
                required: true,
                rangelength: [1, 1]
            },
            apkind: {
                required: true,
                rangelength: [1, 3]
            },
            orderStatus: {
                required: true,
                rangelength: [1, 1]
            },
            retMsg: {
                required: true,
                rangelength: [1, 256]
            }
        },
        submitHandler: function (form, event) {
            event.preventDefault();
            $btn.button('loading');
            $(form).ajaxSubmit({
                dataType: 'json',
                success: function (response) {
                    if (response.errCode == 'success') {
                        window.location.reload();
                    } else {
                        Message.error(response.errMsg);
                    }
                    $btn.button('reset');
                },
                error: function (data, textstatus) {
                    Message.error("服务器内部错误，请稍后再试。");
                    $btn.button('reset');
                }
            });
        }
    })
});