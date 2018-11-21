package com.jshx.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.jshx.entity.*;
import com.jshx.service.*;
import com.jshx.utils.DeptId;
import com.jshx.utils.HttpClientUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by YAO on 2017/5/25.
 */
@Controller
public class HomeController {

    @Autowired
    private FaultrecordService faultrecordService;

    @Autowired
    private LiftService liftService;

    @Autowired
    private RunstatusService runstatusService;

    @Autowired
    private WBRecordService wbRecordService;

    @Autowired
    private BaseMaintainService baseMaintainService;

    @Autowired
    private StatisticAnalysisService statisticAnalysisService;

    @Autowired
    private WBFormService wbFormService;

    private Map<String, String> checkHighFreRegcode = new ConcurrentHashMap<String, String>();

    @RequestMapping("")
    public  ModelAndView defaultview(HttpServletRequest request,HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        ModelAndView home;
/*
        if(session.getAttribute("njLiftMonitorUser") == null){
            home = new ModelAndView("login");
            response.sendRedirect("/jshx/login");
        }else{*/
            home = new ModelAndView("home");
/*        }*/
        return  home;
    }


    @RequestMapping("/home")
    public  ModelAndView home(HttpServletRequest request,HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        ModelAndView home;

/*        if(session.getAttribute("njLiftMonitorUser") == null){
            home = new ModelAndView("login");
            response.sendRedirect("/jshx/login");
        }else{*/
            home = new ModelAndView("home");
            List<Maintain> maintainList=baseMaintainService.findMaintainDeptidList();
            home.addObject("maintainList",maintainList);
/*        }*/
        return  home;
    }

    @RequestMapping(value="/findAllMintorLift",method= RequestMethod.GET)
    public void findAllMintorLift(@RequestParam(name = "regcode") String regcode,
                                  @RequestParam(name = "liftcode") String liftcode,
                                  @RequestParam(name = "liftaddress") String liftaddress,
                                  @RequestParam(name = "maintdeptid") String maintdeptid,
                                  HttpServletResponse response) throws JsonProcessingException {
        JSONObject obj = new JSONObject();
        List<LiftDto> list = liftService.findAllMintorLiftDto(regcode,liftcode,liftaddress,maintdeptid);
        try {
            obj.put("code", "0");
            obj.put("list", JSONArray.toJSON(list));
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.getWriter().print(obj);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 模糊查询电梯列表
     * @param searchValue
     * @param response
     * @throws JsonProcessingException
     */
    @ResponseBody
    @RequestMapping(value="/fuzzyQueryAllMintorLift",method= RequestMethod.GET)
    public JSONObject fuzzyQueryAllMintorLift(@RequestParam(name = "searchValue") String searchValue,
                                              @RequestParam(name = "page") int page,
                                              HttpServletResponse response) throws JsonProcessingException {
        JSONObject json = new JSONObject();
        Page<LiftDto> liftList = liftService.fuzzyQueryAllMintorLift(searchValue,page);

        json.put("code", "0");
        json.put("liftList", JSONArray.toJSON(liftList));
        json.put("pages", liftList.getTotalPages());
        json.put("page", page);
        json.put("rundatas", liftList.getContent());
        return json;


    }


    @RequestMapping(value="/rundetail",method= RequestMethod.GET)
    public  ModelAndView rundetail(HttpServletRequest request,HttpServletResponse response,
                                   @RequestParam(name = "regcode") String regcode) throws Exception{
        /*highFreRegcodeTime 表示是否调高频接口,存入ConcurrentHashMap中,保留3分钟,如果3分钟后再查看该电梯运行数据则再存入ConcurrentHashMap*/
        LiftRun liftRun = runstatusService.findByRegcode(regcode);

        //HttpSession session = request.getSession();
        //String highFreRegcode = (String)session.getAttribute(regcode);

        String highFreRegcodeTime = checkHighFreRegcode.get(regcode);
/*        Long nowTime = System.currentTimeMillis();//把当前时间毫秒数放进去
        Long value = 3*60*1000L;
        if(highFreRegcodeTime != null && !"".equals(highFreRegcodeTime)){
            value = nowTime - highFreRegcodeTime;
        }*/

        try {
            if(highFreRegcodeTime == null || "".equals(highFreRegcodeTime)/* || value <= 1*60*1000*/){
                HttpClient clientGet = new HttpClient();
                clientGet.getHttpConnectionManager().getParams().setConnectionTimeout(2000);//该处设置连接超时无效
                clientGet.getParams().setConnectionManagerTimeout(2000);//处设置连接超时
                if(liftRun.getDeptId().equals(DeptId.DEPTID_KL)){
                    GetMethod methodGet = new GetMethod("http://180.76.173.146:9000/lift/runstatus?regcode="+regcode);
                    clientGet.executeMethod(methodGet);
                }else if(liftRun.getDeptId().equals(DeptId.DEPTID_TL)){
                    GetMethod methodGet = new GetMethod("https://kc3ip-nanjing-govplatform-cn.mychinabluemix.net/lift/runstatus?regcode="+regcode);
                    clientGet.executeMethod(methodGet);
                }else if(liftRun.getDeptId().equals(DeptId.DEPTID_DS)){
                    /*GetMethod methodGet = new GetMethod("https://ioeuat.iotdataserver.net/lift/runstatus?regcode="+regcode);*/
                    /*GetMethod methodGet = new GetMethod(" http://ioe.thyssen.com.cn/lift/runstatus?regcode="+regcode);*/
                    HttpClientUtils.requestForRestful(" https://ioe.thyssen.com.cn/lift/runstatus?regcode="+regcode,"","GET");
                    /*clientGet.executeMethod(methodGet);*/
                }else if(liftRun.getDeptId().equals(DeptId.DEPTID_RL)){
                    GetMethod methodGet = new GetMethod("http://59.41.255.180:19198/lift/runstatus?regcode="+regcode);
                    clientGet.executeMethod(methodGet);
                }else if(liftRun.getDeptId().equals(DeptId.DEPTID_SL)){
                    GetMethod methodGet = new GetMethod("http://bpmpublic.smec-cn.com:8011/nanjing/rest/lift/runstatus?regcode="+regcode);
                    clientGet.executeMethod(methodGet);
                }

                //session.setAttribute(regcode, regcode);

                checkHighFreRegcode.put(regcode, regcode);
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    public void run() {
                        //session.removeAttribute(regcode);
                        //session.invalidate(); //删除所有session中保存的键
                        checkHighFreRegcode.remove(regcode);
                    }
                }, 3*60*1000);// 设定指定的时间time,此处为3分钟

                /*timer.cancel();*/

                System.gc();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            ModelAndView rundetail = new ModelAndView("rundetail");
            rundetail.addObject("regcode",regcode);
            return rundetail;
        }

    }

    @RequestMapping("/getDtjbxx")
    public void getDtjbxx(@RequestParam(name = "regcode") String regcode,HttpServletResponse response){
        try {
            JSONObject obj = new JSONObject();
            LiftDto liftDto = liftService.getDtjbxx(regcode);
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            obj.put("liftdto",liftDto);
            response.getWriter().print(obj);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @RequestMapping("/getDtyxzt")
    @ResponseBody
    public JSONObject getDtyxzt(@RequestParam(name = "regcode") String regcode,HttpServletResponse response){

        LiftRun liftRun = runstatusService.findByRegcode(regcode);
        JSONObject obj = new JSONObject();
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        obj.put("liftrun",liftRun);
        return obj;


    }


    @RequestMapping("/getDtlsgz")
    public void getDtlsgz(@RequestParam(name = "regcode") String regcode,@RequestParam(name = "page") int page,HttpServletResponse response){
        try {
            Page<LiftFault> liftFaultPage =faultrecordService.findAllByRegcode(regcode,page,5);
            JSONObject obj = new JSONObject();
            obj.put("total",liftFaultPage.getTotalElements());
            obj.put("pages",liftFaultPage.getTotalPages());
            obj.put("page",page);
            obj.put("liftfaultpage",liftFaultPage.getContent());
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.getWriter().print(JSONObject.toJSONString(obj, SerializerFeature.DisableCircularReferenceDetect));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @RequestMapping("/getDtlswb")
    public void getDtlswb(@RequestParam(name = "regcode") String regcode,@RequestParam(name = "page") int page,HttpServletResponse response){
        try {
            Page<WBRecord> wbRecordPage = wbRecordService.findAllByRegcode(regcode,page,5);
            JSONObject obj  = wbFormService.findAllByRegcode(regcode,page,5);
            obj.put("wbrecordpage",wbRecordPage.getContent());
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.getWriter().print(JSONObject.toJSONString(obj, SerializerFeature.DisableCircularReferenceDetect));
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    /**
     * 查询电梯最近报警信息
     * @param response
     */
    @RequestMapping("/getLiftFaultAlarmList")
    @ResponseBody
    public JSONObject getLiftFaultAlarmList(HttpServletResponse response){

        List<StatisticAnalysis> alarmList = statisticAnalysisService.getLiftFaultAlarmList();

        JSONObject json = new JSONObject();
        json.put("data",alarmList);
        return json;


    }


    /**
     *
     * @param regcode
     * @param response
     * @return
     */
    @RequestMapping("/getDtlsyx")
    @ResponseBody
    public JSONObject getDtlsyx(@RequestParam(name = "regcode") String regcode,@RequestParam(name = "page") int page,HttpServletResponse response){

        JSONObject obj =runstatusService.findLiftRunHistoriesListByRegcode(regcode,page,5);

        return obj;
    }


}
