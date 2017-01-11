package com.shhxzq.fin.simulator.biz.service.impl.cmbct0;

import com.shhxzq.fin.simulator.biz.util.SpringContextHolder;
import com.shhxzq.fin.simulator.common.util.XmlUtil;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;

/**
 * @author kangyonggan
 * @since 2017/1/11
 */
@Log4j2
public class SocketThread extends Thread {

    private Socket socket;

    @Setter
    private static boolean isCanRun = true;

    private CMBCT0Service cmbct0Service;

    private InputStream in;
    private OutputStream out;

    public SocketThread(Socket socket) {
        this.socket = socket;
        cmbct0Service = SpringContextHolder.getBean(CMBCT0Service.class);
        try {
            in = socket.getInputStream();
            out = socket.getOutputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        byte b[] = new byte[9999];
        int len;

        try {
            /**
             * 总共需要读三次InputStream
             * 1. 读消息头（35位）
             * 2. 读签名数据
             * 3. 读加密后的报文体
             */
            while (isCanRun && (len = in.read(b, 0, 35)) != -1) {// 报文头（总长度8+商户代码15+交易码8+签名长度4）= 35位
                /**
                 * 1. 读消息头（35位），心跳报文只有8位
                 */
                String header = new String(b, 0, len);

                log.info("报文头:" + header);

                if (header.equals("00000000")) {
                    log.info("这是心跳报文，模拟器忽略此请求");
                } else {

                    int totalLen = Integer.parseInt(new String(b, 0, 8)) + 8;// 总长度,前8位
                    log.info("报文总长度:" + totalLen);

                    // 从消息头中获取签名长度，用于第2步读取签名
                    String signLenStr = new String(b, 31, 4);// 签名长度,最后四位
                    int signLen = Integer.parseInt(signLenStr);
                    log.info("签名长度:" + signLen);

                    // 获取交易服务码
                    String transactionCode = String.valueOf(Integer.parseInt(new String(b, 23, 8).trim()));
                    log.info("交易码：" + transactionCode);

                    // 计算加密后报文体的长度，用于第三步读取报文体
                    int encryptedBytesLen = totalLen - 35 - signLen;
                    log.info("加密后报文的长度:" + encryptedBytesLen);

                    /**
                     * 2. 读签名数据
                     */
                    in.read(b, 0, signLen);
                    log.info("模拟器拿到签名什么也不做, 不需要验签");

                    /**
                     * 3. 读取加密后报文体
                     */
                    byte encryptedBytes[] = new byte[encryptedBytesLen];
                    in.read(encryptedBytes, 0, encryptedBytesLen);

                    String xml = new String(encryptedBytes, 0, encryptedBytesLen, "UTF-8");
                    log.info("代付报文:" + xml);

                    // 解析xml
                    Map<String, String> params = XmlUtil.parseXml(xml);
                    log.info("请求报文-名值对: {}", params);

                    // 响应报文
                    String result = "";
                    if (StringUtils.equals(CMBCT0TranCo.REDEEM, transactionCode)) {
                        result = cmbct0Service.redeem(params);
                    } else if (StringUtils.equals(CMBCT0TranCo.QUERY, transactionCode)) {
                        result = cmbct0Service.query(params);
                    }

                    /**
                     * 拼接响应报文， 15位合作方编码 + 8位报文码 + 4位签名长度 + 签名 + xml密文
                     */
                    String respXml = "111111111111111" + StringUtils.leftPad(transactionCode.trim(), 8, " ") + "0004" + "3333" + result;

                    String head = StringUtils.leftPad(respXml.length() + "", 8, "0");

                    log.info("响应报文:" + head + respXml);
                    out.write((head + respXml).getBytes("UTF-8"));

                    out.flush();
                }
            }
        } catch (Exception e) {
            log.error(e);
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (Exception e) {
                log.error(e);
            }
        }
    }
}
