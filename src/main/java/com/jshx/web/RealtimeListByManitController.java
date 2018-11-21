package com.jshx.web;


import com.jshx.entity.Maintain;
import com.jshx.service.BaseMaintainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by YAO on 2017/5/25.
 */
@Controller
public class RealtimeListByManitController {


    @Autowired
    private BaseMaintainService baseMaintainService;


    @RequestMapping("/realtimelistbymanit")
    public ModelAndView home(HttpServletRequest request,HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        ModelAndView realtimelistbymanit;
/*
        if(session.getAttribute("njLiftMonitorUser") == null){
            realtimelistbymanit = new ModelAndView("login");
            response.sendRedirect("/jshx/login");
        }else{*/
            realtimelistbymanit = new ModelAndView("realtimelistbymanit");
            List<Maintain> maintainList=baseMaintainService.findMaintainDeptidList();
            realtimelistbymanit.addObject("maintainList",maintainList);
/*        }*/
        return  realtimelistbymanit;

    }


}
