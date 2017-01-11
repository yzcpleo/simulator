package com.shhxzq.fin.simulator.web.listener;

import com.shhxzq.fin.simulator.biz.service.impl.cmbct0.SocketThread;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 民生T+0的服务
 *
 * @author kangyonggan
 * @since 2017/1/11
 */
@Log4j2
public class CMBCT0ServerSocketListener implements ServletContextListener {

    private static final int PORT = 9108;

    private ServerSocket serverSocket;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("开始启动民生T+0服务...");
        try {
            serverSocket = new ServerSocket(PORT);

            new Thread() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            log.info("民生T+0垫资服务在" + PORT + "端口监听...");
                            Socket socket = serverSocket.accept();
                            new SocketThread(socket).start();
                        } catch (Exception e) {
                            log.error("socket连接民生T+0异常", e);
                        }
                    }
                }
            }.start();
        } catch (Exception e) {
            log.error("民生T+0服务启动异常", e);
            close();
        }
        log.info("启动民生T+0服务完毕...");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        close();
    }

    private void close() {
        log.info("民生T+0服务关闭");
        try {
            SocketThread.setCanRun(false);
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (Exception e) {
            log.error(e);
        }
    }
}
