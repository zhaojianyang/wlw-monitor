package com.jshx.utils;


import com.jshx.service.RunstatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by zjy on 2017/10/19.
 */
@Component
public class TimerTaskUtil implements ApplicationRunner {


    @Autowired
    private RunstatusService runstatusService;


    @Override
    public void run(ApplicationArguments var1) throws Exception{ //系统每次启动后就会自动执行

        //删除部分电梯历史运行数据信息,只保留三天的数据
        Timer timer = new Timer();
        Calendar calEnviron = Calendar.getInstance();
        // 每天的02:00.am开始执行
        calEnviron.set(Calendar.HOUR_OF_DAY, 2);
        calEnviron.set(Calendar.MINUTE, 00);
        // date为制定时间
        Date dateSetter = new Date();
        dateSetter = calEnviron.getTime();
        // nowDate为当前时间
        Date nowDateSetter = new Date();
        // 所得时间差为，距现在待触发时间的间隔
        long intervalEnviron = dateSetter.getTime() - nowDateSetter.getTime();
        if (intervalEnviron < 0) {
            calEnviron.add(Calendar.DAY_OF_MONTH, 1);
            dateSetter = calEnviron.getTime();
            intervalEnviron = dateSetter.getTime() - nowDateSetter.getTime();
        }

        timer.schedule(new TimerTask() {
            public void run() {
                runstatusService.deleteLiftRunHistoriesByCreateTime();
            }
        }, intervalEnviron,24*60*60*1000);// 每24小时执行一次


    }





}
