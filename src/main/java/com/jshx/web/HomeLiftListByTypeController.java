package com.jshx.web;

import com.alibaba.fastjson.JSONObject;
import com.jshx.entity.Maintain;
import com.jshx.entity.StatisticAnalysis;
import com.jshx.service.BaseMaintainService;
import com.jshx.service.RunstatusService;
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
public class HomeLiftListByTypeController {


    @Autowired
    private RunstatusService runstatusService;

    @Autowired
    private BaseMaintainService baseMaintainService;


    @Autowired
    private StatisticAnalysisService statisticAnalysisService;


    @RequestMapping("/homeliftlistbytype")
    public ModelAndView home(HttpServletRequest request,HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        ModelAndView homeliftlistbytype;

/*        if(session.getAttribute("njLiftMonitorUser") == null){
            homeliftlistbytype = new ModelAndView("login");
            response.sendRedirect("/jshx/login");
        }else{*/
            homeliftlistbytype = new ModelAndView("homeliftlistbytype");
            List<Maintain> maintainList=baseMaintainService.findMaintainDeptidList();
            homeliftlistbytype.addObject("maintainList",maintainList);
/*        }*/
        return  homeliftlistbytype;
    }


    /**
     * 根据维保单位或使用单位或区域查询所有监控运行的电梯
     * @param searchValue
     * @param page
     * @param response
     */
    @RequestMapping("/getAllLiftsByDifferentTypeId")
    public void getAllLiftsByDifferentTypeId(@RequestParam(name = "searchValue") String searchValue,
                                             @RequestParam(name = "page") int page,
                                             HttpServletResponse response){

        Page<StatisticAnalysis> liftRunPage = statisticAnalysisService.getAllLiftsByDifferentTypeId (searchValue,page);


        try {

            JSONObject obj = new JSONObject();
            obj.put("total",liftRunPage.getTotalElements());
            obj.put("pages", liftRunPage.getTotalPages());
            obj.put("rundatas", liftRunPage.getContent());
            obj.put("page", page);
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.getWriter().print(obj);

        }catch (Exception e){
            e.printStackTrace();
        }
    }








}
