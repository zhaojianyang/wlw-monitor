package com.jshx.web;

import com.alibaba.fastjson.JSONObject;
import com.jshx.entity.LiftDto;
import com.jshx.entity.LiftFault;
import com.jshx.entity.LiftRun;
import com.jshx.entity.Maintain;
import com.jshx.service.BaseMaintainService;
import com.jshx.service.FaultrecordService;
import com.jshx.service.LiftService;
import com.jshx.service.RunstatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
public class FaultrecordListController {


    @Autowired
    private LiftService liftService;

    @Autowired
    private FaultrecordService faultrecordService;
    @Autowired
    private BaseMaintainService baseMaintainService;


    @RequestMapping("/faultrecordlist")
    public ModelAndView home(HttpServletRequest request,HttpServletResponse response) throws IOException {
//        ModelAndView faultrecordlist = new ModelAndView("faultrecordlist");
//        return faultrecordlist;


        HttpSession session = request.getSession();
        ModelAndView faultrecordlist;
        List<Maintain> maintainList=baseMaintainService.findMaintainDeptidList();
/*        if(session.getAttribute("njLiftMonitorUser") == null){
            faultrecordlist = new ModelAndView("login");
            response.sendRedirect("/jshx/login");
        }else{*/
            faultrecordlist = new ModelAndView("faultrecordlist");
            faultrecordlist.addObject("maintainList",maintainList);
/*        }*/
        return  faultrecordlist;


    }


    @RequestMapping("/getFaultrecordList")
    @ResponseBody
    public JSONObject getFaultrecordList(@RequestParam(name = "regcode") String regcode,
                                @RequestParam(name = "istrap") String istrap,
                                @RequestParam(name = "maintdeptid") String maintdeptid,
                                @RequestParam(name = "page") int page,
                                @RequestParam(name = "liftCode") String liftCode,
                                HttpServletResponse response){

        JSONObject  obj = faultrecordService.findAllLiftFaultPage(regcode,istrap,maintdeptid,page-1,10,liftCode);
        return obj;
    }

    @RequestMapping("/getFaultrecordDetail/{id}")
    public ModelAndView getRealtimeDetail(@PathVariable String id){
        LiftFault liftFault = faultrecordService.findById(id);
        ModelAndView liftFaultdetail = new ModelAndView("faultrecorddetail");
        liftFaultdetail.addObject("liftfault",liftFault);
        return liftFaultdetail;
    }

}
