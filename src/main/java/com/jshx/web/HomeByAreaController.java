package com.jshx.web;

import com.alibaba.fastjson.JSONObject;
import com.jshx.entity.StatisticAnalysis;
import com.jshx.service.BaseAreaService;
import com.jshx.service.BaseCompanyService;
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
public class HomeByAreaController {


    @Autowired
    private StatisticAnalysisService statisticAnalysisService;


    @Autowired
    private BaseAreaService baseAreaService;


    @RequestMapping("/homebyarealist")
    public ModelAndView home(HttpServletRequest request,HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        ModelAndView homebyarealist;

/*        if(session.getAttribute("njLiftMonitorUser") == null){
            homebyarealist = new ModelAndView("login");
            response.sendRedirect("/jshx/login");
        }else{*/
            homebyarealist = new ModelAndView("homebyarealist");
            List<StatisticAnalysis> baseAreaList = baseAreaService.findBaseAreaList();
            homebyarealist.addObject("baseAreaList",baseAreaList);
/*        }*/
        return  homebyarealist;
    }


    /**
     * 以覆盖区域查询监控运行的电梯
     * @param areaId
     * @param page
     * @param response
     */
    @RequestMapping("/getLiftListGroupByAreaId")
    public void getLiftListGroupByAreaId(@RequestParam(name = "areaId") String areaId,
                                         @RequestParam(name = "page") int page,
                                         HttpServletResponse response){

        Page<StatisticAnalysis> liftRunPage = statisticAnalysisService.getLiftListGroupByAreaId (areaId,page);

        try {
            JSONObject obj = new JSONObject();
            obj.put("total",liftRunPage.getTotalElements());
            obj.put("page", page);
            obj.put("pages", liftRunPage.getTotalPages());
            obj.put("rundatas", liftRunPage.getContent());
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.getWriter().print(obj);
        }catch (Exception e){
            e.printStackTrace();
        }
    }





}
