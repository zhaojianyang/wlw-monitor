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
public class HomeMaintainController {

    @Autowired
    private BaseMaintainService baseMaintainService;


    @Autowired
    private StatisticAnalysisService statisticAnalysisService;


    @RequestMapping("/homemaintainlist")
    public ModelAndView home(HttpServletRequest request,HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        ModelAndView homemaintainlist;

/*        if(session.getAttribute("njLiftMonitorUser") == null){
            homemaintainlist = new ModelAndView("login");
            response.sendRedirect("/jshx/login");
        }else{*/
            homemaintainlist = new ModelAndView("homemaintainlist");
            List<Maintain> maintainList=baseMaintainService.findMaintainDeptidList();
            homemaintainlist.addObject("maintainList",maintainList);
/*        }*/
        return  homemaintainlist;
    }


    /**
     * 以维保单位查询监控运行的电梯
     * @param maintDeptid
     * @param page
     * @param response
     */
    @RequestMapping("/getLiftListGroupByMaintDepartment")
    public void getLiftListGroupByMaintDepartment(@RequestParam(name = "maintDeptid") String maintDeptid,
                                                  @RequestParam(name = "page") int page,
                                                  HttpServletResponse response){

        Page<StatisticAnalysis> liftRunPage = statisticAnalysisService.getLiftListGroupByMaintDepartment (maintDeptid,page);


        try {

            JSONObject obj = new JSONObject();
            obj.put("total",liftRunPage.getTotalElements());
            obj.put("pages", liftRunPage.getTotalPages());
            obj.put("page", page);
            obj.put("rundatas", liftRunPage.getContent());
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.getWriter().print(obj);

        }catch (Exception e){
            e.printStackTrace();
        }
    }





}
