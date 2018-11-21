package com.jshx.web;

import com.alibaba.fastjson.JSONObject;
import com.jshx.entity.StatisticAnalysis;
import com.jshx.service.BaseCompanyService;
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
public class HomeUseCompanyController {


    @Autowired
    private StatisticAnalysisService statisticAnalysisService;


    @Autowired
    private BaseCompanyService baseCompanyService;


    @RequestMapping("/homeusecompanylist")
    public ModelAndView home(HttpServletRequest request,HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        ModelAndView homeusecompanylist;

/*        if(session.getAttribute("njLiftMonitorUser") == null){
            homeusecompanylist = new ModelAndView("login");
            response.sendRedirect("/jshx/login");
        }else{*/
            homeusecompanylist = new ModelAndView("homeusecompanylist");
            List<StatisticAnalysis> baseCompanyList = baseCompanyService.findUseCompanyList();
            homeusecompanylist.addObject("baseCompanyList",baseCompanyList);
/*        }*/
        return  homeusecompanylist;
    }


    /**
     * 以使用单位查询监控运行的电梯
     * @param companyId
     * @param page
     * @param response
     */
    @RequestMapping("/getLiftListGroupByUseCompanyId")
    public void getLiftListGroupByUseCompanyId(@RequestParam(name = "companyId") String companyId,
                                               @RequestParam(name = "page") int page,
                                               HttpServletResponse response){

        Page<StatisticAnalysis> liftRunPage = statisticAnalysisService.getLiftListGroupByUseCompanyId (companyId,page);


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
