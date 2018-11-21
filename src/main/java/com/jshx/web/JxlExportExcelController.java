package com.jshx.web;

import com.jshx.entity.*;
import com.jshx.service.*;
import com.jshx.utils.JxlExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZHAO on 2017/8/30.
 */
@Controller
public class JxlExportExcelController {

    @Autowired
    private JxlExportExcelService jxlExportExcelService;

    @Autowired
    private RunstatusService runstatusService;

    @Autowired
    private FaultrecordService faultrecordService;


    @RequestMapping(value="/confirmexportexcel",method= RequestMethod.GET)
    public ModelAndView confirmexportexcel(HttpServletResponse response,@RequestParam(name = "type") String type){

        ModelAndView confirmexportexcel = new ModelAndView("confirmexportexcel");
/*
        List<LiftRunTime> timeList = new ArrayList<>();
        if("fault".equals(type)){
            timeList = faultrecordService.getLiftFaultRecordTimeList();
        }else if("realtime".equals(type)){
            timeList = runstatusService.getLiftRunTimeList();
        }

        confirmexportexcel.addObject("timeList",timeList);*/
        return confirmexportexcel;


    }


    @RequestMapping(value="/exportRealtimeExcelData",method= RequestMethod.GET)
    public String exportRealtimeExcelData(@RequestParam(name = "regcode") String regcode,
                                          @RequestParam(name = "isonline") String isonline,
                                          @RequestParam(name = "deptId") String deptId,
                                          @RequestParam(name = "startTime") String startTime,
                                          @RequestParam(name = "endTime") String endTime,
                                          HttpServletResponse response){


        jxlExportExcelService.exportRealtimeExcelData(response,regcode,isonline,deptId,startTime,endTime);
        return "success";


    }



    @RequestMapping(value="/exportFaultrecordListExcelData",method= RequestMethod.GET)
    public String exportFaultrecordListExcelData(@RequestParam(name = "regcode") String regcode,
                                                 @RequestParam(name = "istrap") String istrap,
                                                 @RequestParam(name = "deptId") String deptId,
                                                 @RequestParam(name = "startTime") String startTime,
                                                 @RequestParam(name = "endTime") String endTime,
                                                 @RequestParam(name = "liftCode") String liftCode,
                                                 HttpServletResponse response){

        jxlExportExcelService.exportFaultrecordListExcelData(response,regcode,istrap,deptId,startTime,endTime,liftCode);
        return "success";


    }




}
