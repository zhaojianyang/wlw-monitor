package com.jshx.web;

import com.alibaba.fastjson.JSONObject;
import com.jshx.entity.Maintain;
import com.jshx.entity.StatisticAnalysis;
import com.jshx.service.BaseMaintainService;
import com.jshx.service.StatisticAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by ZHAO on 2017/8/15.
 */
@Controller
public class ClassifyMonitorController {

    @Autowired
    private BaseMaintainService baseMaintainService;


    @Autowired
    private StatisticAnalysisService statisticAnalysisService;


    @RequestMapping("/classifymonitor")
    public ModelAndView home(HttpServletRequest request,HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        ModelAndView classifymonitor;

/*        if(session.getAttribute("njLiftMonitorUser") == null){
            classifymonitor = new ModelAndView("login");
            response.sendRedirect("/jshx/login");
        }else{*/
            classifymonitor = new ModelAndView("classifymonitor");
            List<Maintain> maintainList=baseMaintainService.findMaintainDeptidList();
            classifymonitor.addObject("maintainList",maintainList);
/*        }*/
        return  classifymonitor;
    }



}
