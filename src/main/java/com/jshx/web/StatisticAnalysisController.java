package com.jshx.web;


import com.alibaba.fastjson.JSONObject;
import com.jshx.entity.DataTable;
import com.jshx.entity.StatisticAnalysis;
import com.jshx.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by ZHAO on 2017/8/03.
 */
@Controller
public class StatisticAnalysisController {


    @Autowired
    private StatisticAnalysisService statisticAnalysisService;


    @RequestMapping("/statisticAnalysis")
    public ModelAndView home(HttpServletRequest request,HttpServletResponse response) throws IOException {

//        ModelAndView statisticAnalysis = new ModelAndView("statisticAnalysis");
//        return statisticAnalysis;

        HttpSession session = request.getSession();
        ModelAndView statisticAnalysis;
/*
        if(session.getAttribute("njLiftMonitorUser") == null){
            statisticAnalysis = new ModelAndView("login");
            response.sendRedirect("/jshx/login");
        }else{*/
            statisticAnalysis = new ModelAndView("statisticAnalysis");
/*        }*/
        return  statisticAnalysis;

    }



    /**
     * 分页查询统计分析维保详情的信息列表
     */
    @RequestMapping(value = "/bigdataMaintAnalysis", method = RequestMethod.GET)
    public void bigdataAreaAnalysis(HttpServletRequest request, HttpServletResponse response) {
        String draw = request.getParameter("draw");
        int page = new Integer(request.getParameter("start"));
        int pageSize = new Integer(request.getParameter("length"));
//            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//�������ڸ�ʽ
//            staticsdate = df.format(new Date());

        Page<StatisticAnalysis> analysisList = statisticAnalysisService.findStatisticAnalysisListInfos(page, pageSize);

        long totalSize = analysisList.getTotalElements();
        DataTable dataTable = new DataTable();
        dataTable.setDraw(Integer.valueOf(draw));
        dataTable.setRecordsTotal(totalSize);
        dataTable.setRecordsFiltered(totalSize);
        dataTable.setData(analysisList.getContent());
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().print(JSONObject.toJSONString(dataTable));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
