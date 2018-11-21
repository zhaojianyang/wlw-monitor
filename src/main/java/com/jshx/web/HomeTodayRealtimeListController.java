package com.jshx.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.jshx.entity.LiftDto;
import com.jshx.entity.Maintain;
import com.jshx.service.BaseMaintainService;
import com.jshx.service.RunstatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
public class HomeTodayRealtimeListController {


    @Autowired
    private RunstatusService runstatusService;
    @Autowired
    private BaseMaintainService baseMaintainService;


    @RequestMapping("/hometodayrealtimelist")
    public ModelAndView home(HttpServletRequest request,HttpServletResponse response) throws IOException {


        HttpSession session = request.getSession();
        ModelAndView hometodayrealtimelist;

/*        if(session.getAttribute("njLiftMonitorUser") == null){
            hometodayrealtimelist = new ModelAndView("login");
            response.sendRedirect("/jshx/login");
        }else{*/
            hometodayrealtimelist = new ModelAndView("hometodayrealtimelist");
            List<Maintain> maintainList=baseMaintainService.findMaintainDeptidList();
            hometodayrealtimelist.addObject("maintainList",maintainList);
/*        }*/
        return  hometodayrealtimelist;

    }





}
