package com.jshx.web;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jshx.domain.TestRepository;
import com.jshx.entity.*;
import com.jshx.service.*;
import com.jshx.utils.DeptId;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
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
import java.util.HashMap;
import java.util.List;

/**
 * Created by YAO on 2017/5/25.
 */
@Controller
public class RealtimeListController {


    @Autowired
    private LiftService liftService;

    @Autowired
    private RunstatusService runstatusService;

    @Autowired
    private BaseMaintainService baseMaintainService;


    @RequestMapping("/realtimelist")
    public ModelAndView home(HttpServletRequest request,HttpServletResponse response)  throws IOException {
//        ModelAndView realtimelist = new ModelAndView("realtimelist");
//        return realtimelist;


        HttpSession session = request.getSession();
        ModelAndView realtimelist;

/*        if(session.getAttribute("njLiftMonitorUser") == null){
            realtimelist = new ModelAndView("login");
            response.sendRedirect("/jshx/login");
        }else{*/
            realtimelist = new ModelAndView("realtimelist");
            List<Maintain> maintainList = baseMaintainService.findMaintainDeptidList();
            realtimelist.addObject("maintainList",maintainList);
/*
        }*/
        return  realtimelist;

    }

    @RequestMapping("/getRealtimeData")
    public void findAllMintorLiftDtoByPage(@RequestParam(name = "regcode") String regcode,
                                           @RequestParam(name = "liftcode") String liftcode,
                                           @RequestParam(name = "liftaddress") String liftaddress,
                                           @RequestParam(name = "page") int page,
                                           HttpServletResponse response) {
        try {
            Page<LiftDto> liftDtoPage = liftService.findAllMintorLiftDtoByPage(regcode, liftcode, liftaddress, page, 10);
            JSONObject obj = new JSONObject();
            obj.put("pages", liftDtoPage.getTotalPages());
            obj.put("page", page);
            obj.put("rundatas", liftDtoPage.getContent());
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.getWriter().print(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/getRealtimeList")
    @ResponseBody
    public JSONObject getRealtimeList(@RequestParam(name = "regcode") String regcode,
                                @RequestParam(name = "isonline") String isonline,
                                @RequestParam(name = "maintdeptid") String maintdeptid,
                                @RequestParam(name = "page") int page,
                                @RequestParam(name = "allOrToday") String allOrToday,
                                @RequestParam(name = "liftCode") String liftCode,
                                HttpServletResponse response){
        JSONObject  result = new JSONObject();
        String searchType = "";//不在96333库里的运行监控的电梯是没有电梯识别码的
        String[] cells = maintdeptid.split(",");
        String deptId = cells[0].toString();
        if(cells.length>1){
            searchType = cells[1].toString();//查询不属于自己维保的电梯,在maintdeptid中加了",maintId"予以识别
        }
        String mainId = runstatusService.getMaintIdByDeptId(deptId);
        if("NotIn".equals(searchType)){//查询不在96333库的电梯
            result = runstatusService.findNotInBaseLiftTableLifts(regcode,isonline,deptId,page-1,10, allOrToday, liftCode);

        }else if("mainId".equals(searchType)){//查询不属于自己维保的电梯,却被自己上传到库里了
            result = runstatusService.findNotBelongToUplodaMaint(regcode,liftCode,isonline,deptId,mainId,page-1,10);

        }else{
            result = runstatusService.findliftRunPage(regcode,isonline,deptId,page-1,10, allOrToday, liftCode);
        }

        return result;
    }


    @RequestMapping("/getRealtimeDetail/{regcode}")
    public ModelAndView getRealtimeDetail(@PathVariable String regcode){
        LiftRun liftRun = runstatusService.findByRegcode(regcode);
        ModelAndView realtimedetail = new ModelAndView("realtimedetail");
        realtimedetail.addObject("liftrun",liftRun);
        return realtimedetail;
    }

}
