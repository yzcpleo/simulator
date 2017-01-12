package com.shhxzq.fin.simulator.web.controller.dashboard;

import com.alibaba.fastjson.JSON;
import com.shhxzq.fin.simulator.model.dto.HopRequest;
import com.shhxzq.fin.simulator.web.controller.BaseController;
import com.shhxzq.kernel.security.SecurityUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author kangyonggan
 * @since 2017/1/12
 */
@Log4j2
@Controller
@RequestMapping("dashboard/hop/songguo")
public class DashboardHopSongguoController extends BaseController {


    private String privateKey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCsw0Ok68hlWDicqwfLSR+NiOdmVjYx2VekXZiZT7nLw4qVYsbOhBRUC2fE2XNPFNBcaeKuZmFrpGSDqSVs0goPi7xiQSbGQep2EOeq87jk3ZsKXAdtPvX1XmHTepY5+xmZBMQbVah1Vtzjjlf/iFjcyPupACCALz0Ylez7u9pv8m3AY/i0YU08xPUx9a19IUzj24xEr2K2kYe1J7edKI57BSaOYPgOCxj3msVk+Gj6cUCF+dB0AX8ECROh0QWEGLsRobQt4oUhAwusPN0kgQQg9Uy6M/UjQEQotn86JRKPwvXJcyq53EoFvgqm/PtzWvWWbxHmVdj19vEM/n9GpQmnAgMBAAECggEAUwxKqCzv2Efgbu+If6BXGqKFGhy3UJ86Ejkr8gbxOZJ2O/mPuBal7wDMkUQ2uf03bDU6UrvEeQo9h0z4QKd3TqHNnS3UhdmJ69eUhglDCEG/FevHZiyt75W/UPnM3XJni7dOzhUPNdjbtkfm5V+V2AyFbWgyN2x94iOwGBLlnooQN+RsQoVliXXygbYF6XYwsWmbMlB9mRhoEzonzmsmBo2nnOrAARjNtw4tjmCGkI9K67QXE6pxK8ogtxteGduk4xDnGvVD2hMc2HUB6qtTYZZlefzOOXadmSeL8FhzKUD5H5es5mYKLIuYGfr9wV6yLmf4OkAMM45hXHrNMQrLgQKBgQDniHuxffyqpDr5WnEIKDpEBG7I43DmM0rle28GbawL/juGg36LCsZgDhlwlgCM4wF758JrOI0Oyvtj3VJav6DIsTTsZl0G9TliY4zKdZgaEHdb6Axej0vQa1QMS201C+HTT+HaQz4s4eF6gLg/i6LcXUF50zniMG5Fy7BpUiexcwKBgQC/BOdVlnX94DbOiMokXFhTT0jGyxyNmM9FK4gbVpjRIgHOgIdwr9trbJgL5d3RCAwVRKTYfiPrbTpPao5bSSKJdwAikEtivkIhEGBKEoYeWwqPTm4/79YZ5rAG02Ed2928JfYayF+SZI/yRARQSDgielz+5g6FG+hzQqLoOTPp/QKBgA+0HRebwPBd9TYGYVY5TEJivpTXgEfMwM6xwYUBGUMy+hyUfJe3ol7PdgBB3EWx+97IiFI3YrHXKJfMYhKPnrsd8cX652JabYrzz4/HzAowhbfxFC2xsGWxceDnmL+ZT7bCW0Ivf18R7vYdFuIQeXpSxOcbYXiq6j/Hoe5yyQhrAoGAASO/WZRfOdeHnC3WvubKJB0Z+w2lKvcZbXk4A6m9manRRvEfXb2+2mI4egGyFBgvMkVJkn0WK8ZoDac+GC9UhGtwVcR0nq8x586YNHjt0eqLIpW+NKVyqo7kx/Wk46+3H/M+B6TgZRgyf6iGOhBkPVhri53FwmeLOHzSSf5lX+UCgYAt+fOZA/h/lG6EB/nnvbANCvVrXGUJkp8oGJKS9uIUUdk3AU3tB9JQfnwE0y676nI8m9DtOUoeee8N543JKKckMtNtzbYe+/yDvR8mIow3DwBSb7kQEYqWJ0+DWPLVZ66MYOAMXmaj7bB1MmW8FwZl+/M4qV6wSt6jK/qD3JfqXg==";

    private String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArMNDpOvIZVg4nKsHy0kfjYjnZlY2MdlXpF2YmU+5y8OKlWLGzoQUVAtnxNlzTxTQXGnirmZha6Rkg6klbNIKD4u8YkEmxkHqdhDnqvO45N2bClwHbT719V5h03qWOfsZmQTEG1WodVbc445X/4hY3Mj7qQAggC89GJXs+7vab/JtwGP4tGFNPMT1MfWtfSFM49uMRK9itpGHtSe3nSiOewUmjmD4DgsY95rFZPho+nFAhfnQdAF/BAkTodEFhBi7EaG0LeKFIQMLrDzdJIEEIPVMujP1I0BEKLZ/OiUSj8L1yXMqudxKBb4Kpvz7c1r1lm8R5lXY9fbxDP5/RqUJpwIDAQAB";

//    @Autowired
//    private SongguoService songguoService;


    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("HOP_SONGGUO")
    public String test() {
        return getPathIndex();
    }

    @RequestMapping(value = "post2hop", method = RequestMethod.POST)
    @RequiresPermissions("HOP_SONGGUO")
    @ResponseBody
    public Map<String, Object> post2hop(HttpServletRequest request) {
        Map<String, Object> resultMap = getResultMap();

        String r = request.getParameter("request");
        HopRequest req = JSON.parseObject(r, HopRequest.class);

//        String signString = songguoService.handle(req);
//        String sign = SecurityUtils.sign(privateKey, signString);
        String sign = SecurityUtils.sign(privateKey, req.getContent());

        log.info("Sign String is {}", sign);
        req.setSignature(sign);

        String reqString = JSON.toJSONString(req);

        String resp = send(reqString);

        resultMap.put("request", reqString);
        resultMap.put("response", resp);
        return resultMap;
    }


    private String send(String data) {
        HttpClient httpClient = new HttpClient();
        PostMethod mypost = new PostMethod("http://10.199.101.150:6080/hop/otc-front");
        mypost.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
        String resMsg = "";
        try {
            RequestEntity entity = new StringRequestEntity(data, "text/xml", "UTF-8");
            mypost.setRequestEntity(entity);
            httpClient.executeMethod(mypost);

            resMsg = mypost.getResponseBodyAsString();
            System.out.println(resMsg);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mypost.releaseConnection();
        }

        return resMsg;
    }

}
