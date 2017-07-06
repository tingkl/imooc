package com.imooc.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by tingkl on 2016/12/13.
 */
public class AsynServlet extends HttpServlet {
    private static Logger LOGGER = LoggerFactory.getLogger(AsynServlet.class);
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.debug("servlet执行开始时间:" + new Date());
        AsyncContext context = req.startAsync();
        new Thread(new Excutor(context)).start();
        LOGGER.debug("servlet执行开始时间:" + new Date());
    }

    public class Excutor implements Runnable {
        private AsyncContext context;
        public Excutor(AsyncContext context) {
            this.context = context;
        }
        public void run() {
            // 执行相关复杂业务
            try {
                LOGGER.debug("业务执行开始时间:" + new Date());
                ServletResponse res = context.getResponse();
                Thread.sleep(1000 * 10);
                LOGGER.debug("业务执行完成时间:" + new Date());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
