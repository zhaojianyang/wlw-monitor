package com.jshx.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jshx.entity.*;
import com.jshx.service.*;
import com.jshx.utils.GenerateLog;
import com.jshx.utils.TokenUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by YAO on 2017/5/25.
 */
@Controller
public class DevicedataController {

    @Autowired
    RunstatusService runstatusService;

    @Autowired
    FaultrecordService faultrecordService;

    @Autowired
    MaintainerService maintainerService;

    @Autowired
    WBRecordService wbRecordService;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    FaultCodeService faultCodeService;

    @Autowired
    JudgeLiftService judgeLiftService;

    @RequestMapping("/token")
    @ResponseBody
    public String token(String appid) throws JsonProcessingException {
        String token = TokenUtil.crearteToken(appid);
        ObjectMapper mapper = new ObjectMapper();
        String result;
        if (null != appid && !"".equals(appid)) {
            Department department = departmentService.findById(appid);
            if (department != null) {
                TokenInfo tokenInfo = new TokenInfo();
                tokenInfo.setAccess_token(token);
                tokenInfo.setExpires_in(7200);
                result = mapper.writeValueAsString(tokenInfo);
            } else {
                ResponseInfo responseInfo = new ResponseInfo();
                responseInfo.setErrcode("1003");
                responseInfo.setErrmsg("appid不存在");
                result = mapper.writeValueAsString(responseInfo);
            }
        } else {
            ResponseInfo responseInfo = new ResponseInfo();
            responseInfo.setErrcode("1003");
            responseInfo.setErrmsg("appid为空");
            result = mapper.writeValueAsString(responseInfo);
        }
        System.out.println(result);
        return result;
    }

    /**
     * 运行数据上传
     *
     * @param access_token
     * @param runInfo
     * @return
     */
    @ResponseBody
    @RequestMapping("/run")
    public String run(@RequestParam(name = "access_token") String access_token, @RequestBody RunInfo runInfo) throws JsonProcessingException {
        ResponseInfo responseInfo = new ResponseInfo();
        ObjectMapper mapper = new ObjectMapper();
        String result = "";
        String appid = TokenUtil.chechToken(access_token.trim());
        Date date = new Date();
        String wrongResult = "";
        Logger logger = Logger.getLogger(GenerateLog.class);//记录错误和警告日志
        Map<String,String> info = new HashMap();
        String msg = "";
        info.put("errMsg","");

        if (null != appid && TokenUtil.time_out.equals(appid)) {
            responseInfo.setErrcode("1001");
            msg = info.get("errMsg");
            msg = "token time out";
            responseInfo.setErrmsg(msg);
            info.put("errMsg",msg);
            result = mapper.writeValueAsString(responseInfo);
            /*wrongResult = wrongResult + ";" + result;*/
            logger.warn("deptId:" + appid +" ; " + "token time out(runstatus)");
        } else if (null != appid && TokenUtil.does_not_exist.equals(appid)) {
            responseInfo.setErrcode("1001");
            msg = info.get("errMsg");
            msg = "token does not exist";
            responseInfo.setErrmsg(msg);
            info.put("errMsg",msg);
            result = mapper.writeValueAsString(responseInfo);

            /*wrongResult = wrongResult + ";" + result;*/
            //logger.warn("deptId:" + appid +" ; " + "token does not exist(runstatus)");
        } else if (null != appid && !"".equals(appid)) {
            List<LiftRun> liftRunList = runInfo.getLift();
            for (LiftRun run : liftRunList) {

                String regcodeOld = run.getRegcode();
                String regcode = "";

                if (regcodeOld != null && !"".equals(regcodeOld)) {//去除字符串中的空格、回车、换行符、制表符.
                    Pattern p = Pattern.compile("\\s*|\t|\r|\n");
                    Matcher m = p.matcher(regcodeOld);
                    regcode = m.replaceAll("");
                    regcode = regcode.replace("-","");
                }else{
                    regcode = regcodeOld;
                }

                int length = regcode.length();

                if (null == regcode || "".equals(regcode)) {
                    responseInfo.setErrcode("1002");
                    msg = info.get("errMsg");
                    msg = msg + "regcode can not be empty; ";
                    responseInfo.setErrmsg(msg);
                    info.put("errMsg",msg);
                    result = mapper.writeValueAsString(responseInfo);

                    /*wrongResult = wrongResult + ";" + result;*/
                    logger.warn("deptId:" + appid +" ; " + "regcode can not be empty(runstatus) ");
                    continue;
                }
                if (length != 20) {
                    responseInfo.setErrcode("1002");
                    msg = info.get("errMsg");
                    msg = msg + "the length of this regcode ("+ regcode+ ") is " + length +
                                ",should be 20 ; ";
                    responseInfo.setErrmsg(msg);
                    info.put("errMsg",msg);
                    logger.warn("deptId:" + appid +" ; " + "regcode:" + regcode +" ; " +
                            "the length of this regcode is " + length + ",is illegal,should be 20(runstatus) ");
                    result = mapper.writeValueAsString(responseInfo);

                    /*wrongResult = wrongResult + ";" + result;*/
                    continue;
                }

                Lift lift = judgeLiftService.judgeBelongToMonitorLifts(regcode);//判断该电梯是否在96333库

                if (lift == null) {
                    responseInfo.setErrcode("1002");
                    msg = info.get("errMsg");
                    msg = msg + "regcode(" + regcode + ")is not in 96333 database ; ";
                    responseInfo.setErrmsg(msg);
                    info.put("errMsg",msg);
                    logger.warn("deptId:" + appid +" ; " + "regcode:" + regcode +" ; " +
                            "regcode is illegal,this regcode is not in 96333 database(runstatus)  ");
                    result = mapper.writeValueAsString(responseInfo);

                    /*wrongResult = wrongResult + ";" + result;*/
                    continue;
                }else if(lift != null){
                    Maintain maintain = judgeLiftService.judgeBelongToOtherMainter(lift.getMaintainid());
                    String deptId = maintain.getDeptId();
                    if(deptId == null || !appid.equals(deptId)){
                        responseInfo.setErrcode("1002");
                        msg = info.get("errMsg");
                        /*msg = msg + "this lift(" + regcode + ")doesn't belong to your company!" +
                                    "(这部电梯属于(" + maintain.getMaintainName() + ")维保!应由原厂维保单位上传数据!) ; ";*/
                        msg = msg + "this lift(" + regcode + ")doesn't belong to your company!";

                        responseInfo.setErrmsg(msg);
                        info.put("errMsg",msg);
                        result = mapper.writeValueAsString(responseInfo);

                        logger.warn("deptId:" + appid +" ; " + "regcode:" + regcode +" ; " +
                                "this lift doesn't belong to your company,it belongs to (" + maintain.getMaintainName() +").(runstatus)  ");

                        /*wrongResult = wrongResult + ";" + result;*/
                        continue;
                    }
                }

                if (null == run.getIsonline() || "".equals(run.getIsonline())) {
                    responseInfo.setErrcode("1002");
                    msg = info.get("errMsg");
                    msg = msg + "isonline can not be empty ; ";
                    responseInfo.setErrmsg(msg);
                    info.put("errMsg",msg);
                    //logger.warn("deptId:" + appid +" ; " + "regcode:" + regcode +" ; " + "isonline can not be empty");
                    result = mapper.writeValueAsString(responseInfo);

                    /*wrongResult = wrongResult + ";" + result;*/
                    continue;
                } else if (null == run.getHours() || "".equals(run.getHours())) {
                    responseInfo.setErrcode("1002");
                    msg = info.get("errMsg");
                    msg = msg + "hours can not be empty ; ";
                    responseInfo.setErrmsg(msg);
                    info.put("errMsg",msg);
                    //logger.warn("deptId:" + appid +" ; " + "regcode:" + regcode +" ; " + "hours can not be empty");
                    result = mapper.writeValueAsString(responseInfo);
                    /*wrongResult = wrongResult + ";" + result;
                    continue;*/
                } else if (null == run.getTimes() || "".equals(run.getTimes())) {
                    responseInfo.setErrcode("1002");
                    msg = info.get("errMsg");
                    msg = msg + "times can not be empty ; ";
                    responseInfo.setErrmsg(msg);
                    info.put("errMsg",msg);
                    //logger.warn("deptId:" + appid +" ; " + "regcode:" + regcode +" ; " + "times can not be empty");
                    result = mapper.writeValueAsString(responseInfo);
                    /*wrongResult = wrongResult + ";" + result;
                    continue;*/
                } else {
                    if (!"0".equals(run.getIsonline()) && !"1".equals(run.getIsonline())) {
                        responseInfo.setErrcode("1002");
                        msg = info.get("errMsg");
                        msg = msg + "isonline Can only be 0 or 1 ; ";
                        responseInfo.setErrmsg(msg);
                        info.put("errMsg",msg);

                        //logger.warn("deptId:" + appid +" ; " + "regcode:" + regcode +" ; " + "isonline Can only be 0 or 1");
                        result = mapper.writeValueAsString(responseInfo);

                        /*wrongResult = wrongResult + ";" + result;*/
                        continue;
                    }
                    if ("1".equals(run.getIsonline())) {
                        if (null == run.getTimestamps() || "".equals(run.getTimestamps())) {
                            responseInfo.setErrcode("1002");
                            msg = info.get("errMsg");
                            msg = msg + "timestamps can not be empty ; ";
                            responseInfo.setErrmsg(msg);
                            info.put("errMsg",msg);
                            //logger.warn("deptId:" + appid +" ; " + "regcode:" + regcode +" ; " + "timestamps can not be empty");

                            result = mapper.writeValueAsString(responseInfo);

                            //return result;
                        } else if (null == run.getMaincontactor() || "".equals(run.getMaincontactor())) {
                            /*responseInfo.setErrcode("1002");
                            responseInfo.setErrmsg("maincontactor can not be empty");*/
                            //logger.warn("deptId:" + appid +" ; " + "regcode:" + regcode +" ; " + "maincontactor can not be empty");

                            /*result = mapper.writeValueAsString(responseInfo);*/

                            //return result;
                        } else if (null == run.getRuncontactor() || "".equals(run.getRuncontactor())) {
                            /*responseInfo.setErrcode("1002");
                            responseInfo.setErrmsg("runcontactor can not be empty");*/
                            //logger.warn("deptId:" + appid +" ; " + "regcode:" + regcode +" ; " + "runcontactor can not be empty");

                            /*result = mapper.writeValueAsString(responseInfo);*/

                            //return result;
                        } else if (null == run.getCircuit() || "".equals(run.getCircuit())) {
                            /*responseInfo.setErrcode("1002");
                            responseInfo.setErrmsg("circuit can not be empty");*/
                            //logger.warn("deptId:" + appid +" ; " + "regcode:" + regcode +" ; " + "circuit can not be empty");

                            /*result = mapper.writeValueAsString(responseInfo);*/

                            //return result;
                        } else if (null == run.getMode() || "".equals(run.getMode())) {
                            /*responseInfo.setErrcode("1002");
                            responseInfo.setErrmsg("mode can not be empty");*/
                            //logger.warn("deptId:" + appid +" ; " + "regcode:" + regcode +" ; " + "mode can not be empty");

                            /*result = mapper.writeValueAsString(responseInfo);*/

                            //return result;
                        } else if (null == run.getCarstatus() || "".equals(run.getCarstatus())) {
                            /*responseInfo.setErrcode("1002");
                            responseInfo.setErrmsg("carstatus can not be empty");*/
                            //logger.warn("deptId:" + appid +" ; " + "regcode:" + regcode +" ; " + "carstatus can not be empty");

                            /*result = mapper.writeValueAsString(responseInfo);*/

                            //return result;
                        } else if (null == run.getDirection() || "".equals(run.getDirection())) {
                            /*responseInfo.setErrcode("1002");
                            responseInfo.setErrmsg("direction can not be empty");*/
                            //logger.warn("deptId:" + appid +" ; " + "regcode:" + regcode +" ; " + "direction can not be empty");

                            /*result = mapper.writeValueAsString(responseInfo);*/

                            //return result;
                        } else if (null == run.getIszone() || "".equals(run.getIszone())) {
                            /*responseInfo.setErrcode("1002");
                            responseInfo.setErrmsg("iszone can not be empty");*/
                            //logger.warn("deptId:" + appid +" ; " + "regcode:" + regcode +" ; " + "iszone can not be empty");

                            /*result = mapper.writeValueAsString(responseInfo);*/

                            //return result;
                        } else if (null == run.getFloor() || "".equals(run.getFloor())) {
                            /*responseInfo.setErrcode("1002");
                            responseInfo.setErrmsg("floor can not be empty");*/
                            //logger.warn("deptId:" + appid +" ; " + "regcode:" + regcode +" ; " + "floor can not be empty");

                            /*result = mapper.writeValueAsString(responseInfo);*/

                            //return result;
                        } else if (null == run.getDoorstatus() || "".equals(run.getDoorstatus())) {
                            /*responseInfo.setErrcode("1002");
                            responseInfo.setErrmsg("doorstatus can not be empty");*/
                            //logger.warn("deptId:" + appid +" ; " + "regcode:" + regcode +" ; " + "doorstatus can not be empty");

                            /*result = mapper.writeValueAsString(responseInfo);*/

                            //return result;
                        } else if (null == run.getUplimit() || "".equals(run.getUplimit())) {
                            /*responseInfo.setErrcode("1002");
                            responseInfo.setErrmsg("uplimit can not be empty");*/
                            //logger.warn("deptId:" + appid +" ; " + "regcode:" + regcode +" ; " + "uplimit can not be empty");

                            /*result = mapper.writeValueAsString(responseInfo);*/

                            //return result;
                        } else if (null == run.getDownlimit() || "".equals(run.getDownlimit())) {
                            /*responseInfo.setErrcode("1002");
                            responseInfo.setErrmsg("downlimit can not be empty");*/
                            //logger.warn("deptId:" + appid +" ; " + "regcode:" + regcode +" ; " + "downlimit can not be empty");

                            /*result = mapper.writeValueAsString(responseInfo);*/

                            //return result;
                        } else if (null == run.getAlarm() || "".equals(run.getAlarm())) {
                            /*responseInfo.setErrcode("1002");
                            responseInfo.setErrmsg("alarm can not be empty");*/
                            //logger.warn("deptId:" + appid +" ; " + "regcode:" + regcode +" ; " + "alarm can not be empty");

                            /*result = mapper.writeValueAsString(responseInfo);*/

                            //return result;
                        } else if (null == run.getPassenger() || "".equals(run.getPassenger())) {
                            /*responseInfo.setErrcode("1002");
                            responseInfo.setErrmsg("passenger can not be empty");*/
                            //logger.warn("deptId:" + appid +" ; " + "regcode:" + regcode +" ; " + "passenger can not be empty");

                            /*result = mapper.writeValueAsString(responseInfo);*/

                            //return result;
                        }
                    }
                    LiftRun liftRun = runstatusService.findByRegcode(regcode);
                    LiftRunHistory liftRunHistory = new LiftRunHistory();
                    if(liftRun==null){
                        run.setCreateTime(date);
                        run.setUpdateTime(date);
                        run.setDeptId(appid);
                        runstatusService.saveRun(run);
                    }else{
                        liftRun.setUpdateTime(date);
                        liftRun.setIsonline(run.getIsonline());
                        liftRun.setTimestamps(run.getTimestamps());
                        liftRun.setMaincontactor(run.getMaincontactor());
                        liftRun.setRuncontactor(run.getRuncontactor());
                        liftRun.setCircuit(run.getCircuit());
                        liftRun.setIszone(run.getIszone());
                        liftRun.setMode(run.getMode());
                        liftRun.setCarstatus(run.getCarstatus());
                        liftRun.setDirection(run.getDirection());
                        liftRun.setIsonline(run.getIsonline());
                        liftRun.setFloor(run.getFloor());
                        liftRun.setDoorstatus(run.getDoorstatus());
                        liftRun.setUplimit(run.getUplimit());
                        liftRun.setDownlimit(run.getDownlimit());
                        liftRun.setAlarm(run.getAlarm());
                        liftRun.setPassenger(run.getPassenger());
                        liftRun.setHours(run.getHours());
                        liftRun.setTimes(run.getTimes());
                        runstatusService.saveRun(liftRun);
                    }
                    liftRunHistory.setCreateTime(date);
                    liftRunHistory.setRegcode(regcode);
                    liftRunHistory.setDeptId(appid);
                    liftRunHistory.setUpdateTime(date);
                    liftRunHistory.setIsonline(run.getIsonline());
                    liftRunHistory.setTimestamps(run.getTimestamps());
                    liftRunHistory.setMaincontactor(run.getMaincontactor());
                    liftRunHistory.setRuncontactor(run.getRuncontactor());
                    liftRunHistory.setCircuit(run.getCircuit());
                    liftRunHistory.setMode(run.getMode());
                    liftRunHistory.setCarstatus(run.getCarstatus());
                    liftRunHistory.setDirection(run.getDirection());
                    liftRunHistory.setIsonline(run.getIsonline());
                    liftRunHistory.setFloor(run.getFloor());
                    liftRunHistory.setDoorstatus(run.getDoorstatus());
                    liftRunHistory.setUplimit(run.getUplimit());
                    liftRunHistory.setDownlimit(run.getDownlimit());
                    liftRunHistory.setAlarm(run.getAlarm());
                    liftRunHistory.setPassenger(run.getPassenger());
                    liftRunHistory.setHours(run.getHours());
                    liftRunHistory.setTimes(run.getTimes());
                    liftRunHistory.setIszone(run.getIszone());
                    runstatusService.saveRunHistory(liftRunHistory);
                    responseInfo.setErrcode("0");
                    msg = info.get("errMsg");
                    msg = msg + "(regcode:" + regcode + ") OK ; ";
                    responseInfo.setErrmsg(msg);
                    info.put("errMsg",msg);
                    result = mapper.writeValueAsString(responseInfo);

                    /*wrongResult = wrongResult + ";" + result;*/
                }
            }

        } else {
            responseInfo.setErrcode("1002");
            msg = info.get("errMsg");
            msg = msg + "invalid token ; ";
            responseInfo.setErrmsg(msg);
            info.put("errMsg",msg);
            //logger.warn("deptId:" + appid +" ; code : 1002" + "invalid token");
            result = mapper.writeValueAsString(responseInfo);

            /*wrongResult = wrongResult + ";" + result;*/
        }



        return result;
    }

    /**
     * 故障数据上传
     *
     * @param access_token
     * @param faultInfo
     * @return
     */
    @ResponseBody
    @RequestMapping("/fault")
    public String fault(@RequestParam(name = "access_token") String access_token, @RequestBody FaultInfo faultInfo) throws JsonProcessingException {

        ResponseInfo responseInfo = new ResponseInfo();
        ObjectMapper mapper = new ObjectMapper();
        String result;
        String appid = TokenUtil.chechToken(access_token.trim());
        Date date = new Date();
        Logger logger = Logger.getLogger(GenerateLog.class);//记录错误和警告日志

        if (null != appid && TokenUtil.time_out.equals(appid)) {
            responseInfo.setErrcode("1001");
            responseInfo.setErrmsg("token timeout");
        } else if (null != appid && TokenUtil.does_not_exist.equals(appid)) {
            responseInfo.setErrcode("1001");
            responseInfo.setErrmsg("token does not exist");
        } else if (null != appid && !"".equals(appid)) {

            List<FaultData> faultList = faultInfo.getLift();

            for (FaultData fault : faultList) {

                String regcodeOld = fault.getRegcode();
                String regcode = "";

                if (regcodeOld != null && !"".equals(regcodeOld)) {//去除字符串中的空格、回车、换行符、制表符.
                    Pattern p = Pattern.compile("\\s*|\t|\r|\n");
                    Matcher m = p.matcher(regcodeOld);
                    regcode = m.replaceAll("");
                    regcode = regcode.replace("-","");
                }else{
                    regcode = regcodeOld;
                }

                int length = regcode.length();

                if (null == regcode || "".equals(regcode)) {
                    responseInfo.setErrcode("1002");
                    responseInfo.setErrmsg("regcode can not be empty");
                    result = mapper.writeValueAsString(responseInfo);
                    logger.warn("deptId:" + appid +" ; " + "regcode can not is empty(fault)");
                    return result;
                }
                if (length != 20) {
                    responseInfo.setErrcode("1002");
                    responseInfo.setErrmsg("the length of this regcode ("+ regcode+ ") is " + length +
                            ",is illegal,should be 20");
                    logger.warn("deptId:" + appid +" ; " + "regcode:" + regcode +" ; " +
                            "the length of this regcode is " + length + ",is illegal,should be 20(fault) ");
                    result = mapper.writeValueAsString(responseInfo);
                    return result;
                }

                Lift lift = judgeLiftService.judgeBelongToMonitorLifts(regcode);//判断该电梯是否在96333库

                if (lift == null) {
                    responseInfo.setErrcode("1002");
                    responseInfo.setErrmsg("regcode is illegal,this regcode ("+ regcode+ ") is not in 96333 database ");
                    logger.warn("deptId:" + appid +" ; " + "regcode:" + regcode +" ; " +
                            "regcode is illegal,this regcode is not in 96333 database(fault) ");
                    result = mapper.writeValueAsString(responseInfo);
                    return result;
                }else if(lift != null){
                    Maintain maintain = judgeLiftService.judgeBelongToOtherMainter(lift.getMaintainid());
                    String deptId = maintain.getDeptId();
                    if(deptId == null || !appid.equals(deptId)){
                        responseInfo.setErrcode("1002");
                        responseInfo.setErrmsg("this lift doesn't belong to your company!" +
                                "(这部电梯不属于你们维保,属于(" + maintain.getMaintainName() + ")维保!应由原厂维保单位上传数据!)");
                        result = mapper.writeValueAsString(responseInfo);
                        logger.warn("deptId:" + appid +" ; " + "regcode:" + regcode +" ; " +
                                "this lift doesn't belong to your company,it belongs to (" + maintain.getMaintainName() +"). (fault) ");
                        return result;
                    }
                }

                if (null == fault.getFaultid() || "".equals(fault.getFaultid())) {
                    responseInfo.setErrcode("1002");
                    responseInfo.setErrmsg("faultid can not be empty");
                    result = mapper.writeValueAsString(responseInfo);
                    return result;
                } else if (null == fault.getStarttime() || "".equals(fault.getStarttime())) {
                    responseInfo.setErrcode("1002");
                    responseInfo.setErrmsg("starttime can not be empty");
                    result = mapper.writeValueAsString(responseInfo);
                    return result;
                } else if (null == fault.getType() || "".equals(fault.getType())) {
                    responseInfo.setErrcode("1002");
                    responseInfo.setErrmsg("type can not be empty");
                    result = mapper.writeValueAsString(responseInfo);
                    return result;
                } else if (null == fault.getDealstatus() || "".equals(fault.getDealstatus())) {
                    responseInfo.setErrcode("1002");
                    responseInfo.setErrmsg("dealstatus can not be empty");
                    result = mapper.writeValueAsString(responseInfo);
                    return result;
                } else if (null == fault.getDealtime() || "".equals(fault.getDealtime())) {
                    responseInfo.setErrcode("1002");
                    responseInfo.setErrmsg("dealtime can not be empty");
                    result = mapper.writeValueAsString(responseInfo);
                    return result;
                } else if (null == fault.getDealperson() || "".equals(fault.getDealperson())) {
                    responseInfo.setErrcode("1002");
                    responseInfo.setErrmsg("dealperson can not be empty");
                    result = mapper.writeValueAsString(responseInfo);
                    return result;
                } else if (null == fault.getSelfrepair() || "".equals(fault.getSelfrepair())) {
                    responseInfo.setErrcode("1002");
                    responseInfo.setErrmsg("selfrepair can not be empty");
                    result = mapper.writeValueAsString(responseInfo);
                    return result;
                } else if (null == fault.getMaincontactor() || "".equals(fault.getMaincontactor())) {
                    responseInfo.setErrcode("1002");
                    responseInfo.setErrmsg("maincontactor can not be empty");
                    result = mapper.writeValueAsString(responseInfo);
                    return result;
                } else if (null == fault.getRuncontactor() || "".equals(fault.getRuncontactor())) {
                    responseInfo.setErrcode("1002");
                    responseInfo.setErrmsg("runcontactor can not be empty");
                    result = mapper.writeValueAsString(responseInfo);
                    return result;
                } else if (null == fault.getCircuit() || "".equals(fault.getCircuit())) {
                    responseInfo.setErrcode("1002");
                    responseInfo.setErrmsg("circuit can not be empty");
                    result = mapper.writeValueAsString(responseInfo);
                    return result;
                } else if (null == fault.getCarstatus() || "".equals(fault.getCarstatus())) {
                    responseInfo.setErrcode("1002");
                    responseInfo.setErrmsg("carstatus can not be empty");
                    result = mapper.writeValueAsString(responseInfo);
                    return result;
                } else if (null == fault.getDirection() || "".equals(fault.getDirection())) {
                    responseInfo.setErrcode("1002");
                    responseInfo.setErrmsg("direction can not be empty");
                    result = mapper.writeValueAsString(responseInfo);
                    return result;
                } else if (null == fault.getIszone() || "".equals(fault.getIszone())) {
                    responseInfo.setErrcode("1002");
                    responseInfo.setErrmsg("iszone can not be empty");
                    result = mapper.writeValueAsString(responseInfo);
                    return result;
                } else if (null == fault.getFloor() || "".equals(fault.getFloor())) {
                    responseInfo.setErrcode("1002");
                    responseInfo.setErrmsg("floor can not be empty");
                    result = mapper.writeValueAsString(responseInfo);
                    return result;
                } else if (null == fault.getDoorstatus() || "".equals(fault.getDoorstatus())) {
                    responseInfo.setErrcode("1002");
                    responseInfo.setErrmsg("doorstatus can not be empty");
                    result = mapper.writeValueAsString(responseInfo);
                    return result;
                } else if (null == fault.getUplimit() || "".equals(fault.getUplimit())) {
                    responseInfo.setErrcode("1002");
                    responseInfo.setErrmsg("uplimit can not be empty");
                    result = mapper.writeValueAsString(responseInfo);
                    return result;
                } else if (null == fault.getDownlimit() || "".equals(fault.getDownlimit())) {
                    responseInfo.setErrcode("1002");
                    responseInfo.setErrmsg("downlimit can not be empty");
                    result = mapper.writeValueAsString(responseInfo);
                    return result;
                } else if (null == fault.getAlarm() || "".equals(fault.getAlarm())) {
                    responseInfo.setErrcode("1002");
                    responseInfo.setErrmsg("alarm can not be empty");
                    result = mapper.writeValueAsString(responseInfo);
                    return result;
                }/*else if (null == fault.getHours() || "".equals(fault.getHours())) {
                    responseInfo.setErrcode("1002");
                    responseInfo.setErrmsg("hours can not be empty");
                    result = mapper.writeValueAsString(responseInfo);
                    return result;
                }else if (null == fault.getTimes() || "".equals(fault.getTimes())) {
                    responseInfo.setErrcode("1002");
                    responseInfo.setErrmsg("times can not be empty");
                    result = mapper.writeValueAsString(responseInfo);
                    return result;
                }*/
                if ("00".equals(fault.getDealstatus())) {
                    if (null == fault.getIstrap() || "".equals(fault.getIstrap())) {
                        responseInfo.setErrcode("1002");
                        responseInfo.setErrmsg("when dealstatus is '00',istrap can not be empty");
                        result = mapper.writeValueAsString(responseInfo);
                        return result;
                    }
                }

                FaultCode faultCode = faultCodeService.findByCode(fault.getType());
                if (null == faultCode) {
                    responseInfo.setErrcode("1002");
                    responseInfo.setErrmsg("type error");
                    result = mapper.writeValueAsString(responseInfo);
                    return result;
                }
                //LiftFault liftFault = faultrecordService.findByFaultid(fault.getFaultid());
                LiftFault liftFault = faultrecordService.findByFaultid(fault.getFaultid(),fault.getRegcode());
                if(liftFault == null){
                    liftFault = new LiftFault();

                }
                liftFault.setCreateTime(date);
                liftFault.setDeptId(appid);
                liftFault.setRegcode(fault.getRegcode());
                liftFault.setFaultid(fault.getFaultid());
                liftFault.setStarttime(fault.getStarttime());
                liftFault.setDealstatus(fault.getDealstatus());
                liftFault.setDealtime(fault.getDealtime());
                liftFault.setDealperson(fault.getDealperson());
                liftFault.setIstrap(fault.getIstrap());
                liftFault.setSelfrepair(fault.getSelfrepair());
                liftFault.setDealnote(fault.getDealnote());
                liftFault.setMaincontactor(fault.getMaincontactor());
                liftFault.setRuncontactor(fault.getRuncontactor());
                liftFault.setCircuit(fault.getCircuit());
                liftFault.setCarstatus(fault.getCarstatus());
                liftFault.setDirection(fault.getDirection());
                liftFault.setIszone(fault.getIszone());
                liftFault.setFloor(fault.getFloor());
                liftFault.setDoorstatus(fault.getDoorstatus());
                liftFault.setUplimit(fault.getUplimit());
                liftFault.setDownlimit(fault.getDownlimit());
                liftFault.setAlarm(fault.getAlarm());
                liftFault.setFaultCode(faultCode);
                liftFault.setHours(fault.getHours());
                liftFault.setTimes(fault.getTimes());
                faultrecordService.saveFault(liftFault);

            }
            responseInfo.setErrcode("0");
            responseInfo.setErrmsg("ok");
        } else {
            responseInfo.setErrcode("1002");
            responseInfo.setErrmsg("invalid token");
        }
        result = mapper.writeValueAsString(responseInfo);
        return result;

    }

    /**
     * 维保数据上传
     *
     * @param access_token
     * @param maintainInfo
     * @return
     */
    @ResponseBody
    @RequestMapping("/maintain")
    public String maintain(@RequestParam(name = "access_token") String access_token, @RequestBody MaintainInfo maintainInfo) throws JsonProcessingException {

        ResponseInfo responseInfo = new ResponseInfo();
        ObjectMapper mapper = new ObjectMapper();
        String result;
        Date date = new Date();
        String appid = TokenUtil.chechToken(access_token.trim());
        if (null != appid && TokenUtil.time_out.equals(appid)) {
            responseInfo.setErrcode("1001");
            responseInfo.setErrmsg("token timeout");
        } else if (null != appid && TokenUtil.does_not_exist.equals(appid)) {
            responseInfo.setErrcode("1001");
            responseInfo.setErrmsg("token does not exist");
        } else if (null != appid && !"".equals(appid)) {

            Map<String, Maintainer> maintainerMap = this.getMaintainerMap();

            ArrayList<WBRecord> wbRecordList = new ArrayList<>();

            List<LiftMaintain> maintainList = maintainInfo.getLift();
            for (LiftMaintain maintain : maintainList) {
                if (null == maintain.getRegcode() || "".equals(maintain.getRegcode())) {
                    responseInfo.setErrcode("1002");
                    responseInfo.setErrmsg("regcode can not be empty");
                    result = mapper.writeValueAsString(responseInfo);
                    return result;
                } else if (null == maintain.getMaintainid() || "".equals(maintain.getMaintainid())) {
                    responseInfo.setErrcode("1002");
                    responseInfo.setErrmsg("maintainid can not be empty");
                    result = mapper.writeValueAsString(responseInfo);
                    return result;
                } else if (null == maintain.getStarttime() || "".equals(maintain.getStarttime())) {
                    responseInfo.setErrcode("1002");
                    responseInfo.setErrmsg("starttime can not be empty");
                    result = mapper.writeValueAsString(responseInfo);
                    return result;
                } else if (null == maintain.getEndtime() || "".equals(maintain.getEndtime())) {
                    responseInfo.setErrcode("1002");
                    responseInfo.setErrmsg("endtime can not be empty");
                    result = mapper.writeValueAsString(responseInfo);
                    return result;
                } else if (null == maintain.getPerson() || "".equals(maintain.getPerson())) {
                    responseInfo.setErrcode("1002");
                    responseInfo.setErrmsg("person can not be empty");
                    result = mapper.writeValueAsString(responseInfo);
                    return result;
                } else if (null == maintain.getMobile() || "".equals(maintain.getMobile())) {
                    responseInfo.setErrcode("1002");
                    responseInfo.setErrmsg("mobile can not be empty");
                    result = mapper.writeValueAsString(responseInfo);
                    return result;
                } else if (null == maintain.getMaintaintype() || "".equals(maintain.getMaintaintype())) {
                    responseInfo.setErrcode("1002");
                    responseInfo.setErrmsg("maintaintype can not be empty");
                    result = mapper.writeValueAsString(responseInfo);
                    return result;
                }

                List<MaintainWork> maintainwork = maintain.getMaintainwork();
                if (null != maintainwork && 0 != maintainwork.size()) {
                    for (MaintainWork work : maintainwork) {

                        if (null == work.getCode() || "".equals(work.getCode())) {
                            responseInfo.setErrcode("1002");
                            responseInfo.setErrmsg("code can not be empty");
                            result = mapper.writeValueAsString(responseInfo);
                            return result;
                        } else if (null == work.getResult() || "".equals(work.getResult())) {
                            responseInfo.setErrcode("1002");
                            responseInfo.setErrmsg("result can not be empty");
                            result = mapper.writeValueAsString(responseInfo);
                            return result;
                        } else if (null == work.getReplacement() || "".equals(work.getReplacement())) {
                            responseInfo.setErrcode("1002");
                            responseInfo.setErrmsg("replacement can not be empty");
                            result = mapper.writeValueAsString(responseInfo);
                            return result;
                        }

                        if (!"00".equals(work.getReplacement()) && !"01".equals(work.getReplacement())) {
                            responseInfo.setErrcode("1002");
                            responseInfo.setErrmsg("replacement Can only be '00' or '01'");
                            result = mapper.writeValueAsString(responseInfo);
                            return result;
                        }
                        if ("01".equals(work.getReplacement())) {
                            if (null == work.getParts() || "".equals(work.getParts())) {
                                responseInfo.setErrcode("1002");
                                responseInfo.setErrmsg("when replacement is '01',parts can not be empty");
                                result = mapper.writeValueAsString(responseInfo);
                                return result;
                            }
                        }
                        //TODO
                    }
                }
                //TODO

                Maintainer maintainer = maintainerMap.get(maintain.getMobile());
                if (maintainer == null) {
                    maintainer = new Maintainer();
                    maintainer.setCreateTime(date);
                    maintainer.setDeptId(appid);
                    maintainer.setDelflag("0");
                    maintainer.setPeopleName(maintain.getPerson());
                    maintainer.setMobile(maintain.getMobile());
                    maintainerService.saveMaintainer(maintainer);
                }

                WBRecord wbRecord = new WBRecord();
                wbRecord.setCreateTime(date);
                wbRecord.setDeptId(appid);
                wbRecord.setRegcode(maintain.getRegcode());
                wbRecord.setSource("0");
                wbRecord.setFormId(maintain.getMaintainid());
                wbRecord.setStarttime(maintain.getStarttime());
                wbRecord.setStoptime(maintain.getEndtime());
                wbRecord.setNextdate(maintain.getNextdate());
                wbRecord.setMaintainer(maintainer);
                wbRecord.setMaintaintype(maintain.getMaintaintype());
                wbRecord.setMaintainRecords(mapper.writeValueAsString(maintain.getMaintainwork()));
                wbRecord.setMemo(maintain.getNote());
                wbRecordList.add(wbRecord);

            }
            wbRecordService.saveWBRecordList(wbRecordList);
            responseInfo.setErrcode("0");
            responseInfo.setErrmsg("ok");
        } else {
            responseInfo.setErrcode("1002");
            responseInfo.setErrmsg("invalid token");
        }
        result = mapper.writeValueAsString(responseInfo);
        return result;
    }

    private Map<String, Maintainer> getMaintainerMap() {

        Map<String, Maintainer> maintainerMap = new HashMap<String, Maintainer>();

        List<Maintainer> maintainerList = maintainerService.findMaintainerList();

        if (maintainerList != null && 0 != maintainerList.size()) {
            for (Maintainer maintainer : maintainerList) {
                maintainerMap.put(maintainer.getMobile(), maintainer);
            }
        }
        return maintainerMap;
    }

}
