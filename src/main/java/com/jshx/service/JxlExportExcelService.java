package com.jshx.service;

import com.jshx.domain.DepartmentRepository;
import com.jshx.domain.RunstatusRepository;
import com.jshx.entity.*;
import com.jshx.utils.JxlExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by ZHAO on 2017/8/30.
 */
@Service
public class JxlExportExcelService {


    @Autowired
    RunstatusRepository runstatusRepository;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private BaseMaintainService baseMaintainService;

    @Autowired
    private DepartmentRepository departmentRepository;


    /**
     * 获取上传的实时运行的电梯信息
     * @param regcode
     * @param isonline
     * @param deptId
     * @param startTime
     * @param endTime
     * @return
     */
    public List<LiftRun> getExportRealtimeExcelData(String regcode, String isonline, String deptId,
                                               String startTime, String endTime) {
        //     List<User> list = userService.findAll(SortTools.basicSort(new SortDto("desc", "userName"), new SortDto("id")));

/*

        List<LiftRun> runLiftList = runstatusRepository.findAll(new Specification<LiftRun>() {
            @Override
            public Predicate toPredicate(Root<LiftRun> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();

                if (!StringUtils.isEmpty(regcode)) {
                    list.add(criteriaBuilder.like(root.get("regcode").as(String.class), "%" + regcode + "%"));
                }
                if (!StringUtils.isEmpty(isonline)) {
                    list.add(criteriaBuilder.equal(root.get("isonline").as(String.class), isonline));
                }
                if (!StringUtils.isEmpty(deptId)) {
                    list.add(criteriaBuilder.equal(root.get("deptId").as(String.class), deptId));
                }
                if (!StringUtils.isEmpty(startTime)) {
                    list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("timestamps"), startTime));
                }
                if (!StringUtils.isEmpty(endTime)) {
                    list.add(criteriaBuilder.lessThanOrEqualTo(root.get("timestamps"), endTime + "235959"));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        }, new Sort(Sort.Direction.DESC, "timestamps"));
*/


        StringBuffer hql = new StringBuffer();
        hql.append("SELECT new com.jshx.entity.LiftRun(l.deptId,l.regcode,l.isonline ,l.timestamps,l.maincontactor,l.runcontactor," +
                "l.circuit,l.mode,l.carstatus,l.direction,l.iszone,l.floor,l.doorstatus,l.uplimit," +
                "l.downlimit,l.alarm,l.passenger,l.hours,l.times) FROM LiftRun l WHERE 1=1 ");

        if (!StringUtils.isEmpty(regcode)) {
            hql.append(" AND l.regcode like '%" + regcode + "%'");
        }
        if (!StringUtils.isEmpty(isonline)) {
            hql.append(" AND l.isonline = '" + isonline + "'");
        }
        if (!StringUtils.isEmpty(deptId)) {
            hql.append(" AND l.deptId = '" + deptId + "'");
        }
        if (!StringUtils.isEmpty(startTime)) {
            hql.append(" AND l.timestamps >= '" + startTime + "'");
        }
        if (!StringUtils.isEmpty(endTime)) {
            hql.append(" AND l.timestamps <= '" + endTime + "235959'");
        }
        hql.append(" ORDER BY l.timestamps DESC ");

        Query query = em.createQuery(hql.toString());
        List<LiftRun> runLiftList = query.getResultList();

        return runLiftList;
    }


    /**
     * 获取上传的故障信息
     * @param regcode
     * @param istrap
     * @param deptId
     * @param startTime
     * @param endTime
     * @return
     */
    public List<LiftFaultAndLift> getFaultrecordListExportDate(String regcode, String istrap, String deptId,
                                                        String startTime, String endTime, String liftCode) {
        StringBuffer hql = new StringBuffer();
/*        hql.append("SELECT new com.jshx.entity.LiftFault(l.createTime,l.updateTime ,l.deptId,l.regcode,l.faultid," +
                "l.starttime,l.dealstatus,l.dealtime,l.dealperson,l.istrap,l.selfrepair,l.dealnote,l.maincontactor," +
                "l.runcontactor,l.circuit,l.carstatus,l.direction,l.iszone,l.floor,l.doorstatus,l.uplimit,l.downlimit," +
                "l.alarm,l.faultCode.content,l.faultCode.memo,l.faultCodel.hours,l.times) " +
                "FROM LiftFault l WHERE 1=1 ");*/
        hql.append("SELECT new com.jshx.entity.LiftFaultAndLift(l.createTime,l.updateTime ,l.deptId,l.regcode,l.faultid," +
                "l.starttime,l.dealstatus,l.dealtime,l.dealperson,l.istrap,l.selfrepair,l.dealnote,l.maincontactor," +
                "l.runcontactor,l.circuit,l.carstatus,l.direction,l.iszone,l.floor,l.doorstatus,l.uplimit,l.downlimit," +
                "l.alarm,l.faultCode,l.hours,l.times,f.liftcode) FROM LiftFault l,Lift f WHERE 1=1 AND l.regcode = f.regcode  ");

        if (!StringUtils.isEmpty(regcode)) {
            hql.append(" AND l.regcode like '%" + regcode + "%'");
        }
        if (!StringUtils.isEmpty(istrap)) {
            hql.append(" AND l.istrap = '" + istrap + "'");
        }
        if (!StringUtils.isEmpty(deptId)) {
            hql.append(" AND l.deptId = '" + deptId + "'");
        }
        if (!StringUtils.isEmpty(startTime)) {
            hql.append(" AND l.starttime >= '" + startTime + "'");
        }
        if (!StringUtils.isEmpty(endTime)) {
            hql.append(" AND l.starttime <= '" + endTime + "235959'");
        }
        if (!StringUtils.isEmpty(liftCode)) {
            hql.append(" AND f.liftcode like '%" + liftCode + "%'");
        }
        hql.append(" ORDER BY l.starttime DESC ");

        Query query = em.createQuery(hql.toString());
/*        query.setFirstResult(1275);
        query.setMaxResults(1300);*/
        List<LiftFaultAndLift> resultlist = query.getResultList();
        return resultlist;
    }



    /**
     * 设置导出的故障信息的显示格式
     * @param faultList
     * @return
     */
    public List<Map<String, Object>> setFaultrecordListExportDate(List<LiftFaultAndLift> faultList) {

        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < faultList.size(); i++) {
            LiftFaultAndLift fault = faultList.get(i);
            Map<String, Object> dataMap = new LinkedHashMap<>();

            String startTime = fault.getStarttime();
            if (startTime != null && startTime.length() > 12) {
                startTime = startTime.substring(0, 4) + "-" + startTime.substring(4, 6) + "-" + startTime.substring(6, 8) + " "
                        + startTime.substring(8, 10) + ":" + startTime.substring(10, 12) + ":" + startTime.substring(12);
                fault.setStarttime(startTime);
            }

            String dealTime = fault.getDealtime();
            if (dealTime != null && dealTime.length() > 12) {
                dealTime = dealTime.substring(0, 4) + "-" + dealTime.substring(4, 6) + "-" + dealTime.substring(6, 8) + " "
                        + dealTime.substring(8, 10) + ":" + dealTime.substring(10, 12) + ":" + dealTime.substring(12);
                fault.setDealtime(dealTime);
            }

            String dealstatus = fault.getDealstatus();
            if ("00".equals(dealstatus)) {
                fault.setDealstatus("处置完成");
            } else if ("01".equals(dealstatus)) {
                fault.setDealstatus("故障发生");
            } else if ("02".equals(dealstatus)) {
                fault.setDealstatus("发出通知");
            } else if ("03".equals(dealstatus)) {
                fault.setDealstatus("处置响应");
            } else if ("04".equals(dealstatus)) {
                fault.setDealstatus("处置到场");
            } else if ("05".equals(dealstatus)) {
                fault.setDealstatus("误报");
            }

            String istrapNow = fault.getIstrap();
            if ("0".equals(istrapNow)) {
                fault.setIstrap("否");
            } else if (!"0".equals(istrapNow)) {
                fault.setIstrap("是");
            }

            String selfrepair = fault.getSelfrepair();
            //fault.setSelfrepair("");
            if ("0".equals(selfrepair)) {
                fault.setSelfrepair("否");
            } else if ("1".equals(selfrepair)) {
                fault.setSelfrepair("是");
            }

            String maincontactor = fault.getMaincontactor();
            //fault.setMaincontactor("");
            if ("0".equals(maincontactor)) {
                fault.setMaincontactor("吸合");
            } else if ("1".equals(maincontactor)) {
                fault.setMaincontactor("断开");
            }

            String runcontactor = fault.getRuncontactor();
            //fault.setRuncontactor("");
            if ("0".equals(runcontactor)) {
                fault.setRuncontactor("吸合");
            } else if ("1".equals(runcontactor)) {
                fault.setRuncontactor("断开");
            }

            String circuit = fault.getCircuit();
            //fault.setCircuit("");
            if ("0".equals(circuit)) {
                fault.setCircuit("正常");
            } else if ("1".equals(circuit)) {
                fault.setCircuit("断开");
            }

            String carstatus = fault.getCarstatus();
            //fault.setCarstatus("");
            if ("0".equals(carstatus)) {
                fault.setCarstatus("停止");
            } else if ("1".equals(carstatus)) {
                fault.setCarstatus("运行");
            }

            String direction = fault.getDirection();
            //fault.setDirection("");
            if ("0".equals(direction)) {
                fault.setDirection("无方向");
            } else if ("1".equals(direction)) {
                fault.setDirection("上行");
            } else if ("2".equals(direction)) {
                fault.setDirection("下行");
            }

            String iszone = fault.getIszone();
            //fault.setIszone("");
            if ("0".equals(iszone)) {
                fault.setIszone("否");
            } else if ("1".equals(iszone)) {
                fault.setIszone("是");
            }

            String doorstatus = fault.getDoorstatus();
            //fault.setDoorstatus("");
            if ("0".equals(doorstatus)) {
                fault.setDoorstatus("无关门到位信号");
            } else if ("1".equals(doorstatus)) {
                fault.setDoorstatus("关门到位");
            }

            String uplimit = fault.getUplimit();
            //fault.setUplimit("");
            if ("0".equals(uplimit)) {
                fault.setUplimit("否");
            } else if ("1".equals(uplimit)) {
                fault.setUplimit("是");
            }

            String downlimit = fault.getDownlimit();
            //fault.setDownlimit("");
            if ("0".equals(downlimit)) {
                fault.setDownlimit("否");
            } else if ("1".equals(downlimit)) {
                fault.setDownlimit("是");
            }

            String alarm = fault.getAlarm();
            //fault.setAlarm("");
            if ("0".equals(alarm)) {
                fault.setAlarm("否");
            } else if ("1".equals(alarm)) {
                fault.setAlarm("是");
            }

            if(fault.getHours() == null){
                fault.setHours("");
            }
            if(fault.getTimes() == null){
                fault.setTimes("");
            }
            String deptId = fault.getDeptId();
            if(deptId == null){
                fault.setDeptId("");
            }
            Department department= departmentRepository.findById(deptId);
            String deptName = "";
            if(department != null){
                deptName = department.getDeptName();
            }

            dataMap.put("regcode", fault.getRegcode());
            dataMap.put("liftCode", fault.getLiftCode());
            dataMap.put("deptName", deptName);
            dataMap.put("faultid", fault.getFaultid());
            dataMap.put("startTime", startTime);
/*            dataMap.put("faultType", fault.getContent());*/
            dataMap.put("faultType", fault.getFaultCode().getContent());
            dataMap.put("dealstatus", fault.getDealstatus());
            dataMap.put("dealTime", dealTime);
            dataMap.put("dealperson", fault.getDealperson());
            dataMap.put("istrap", fault.getIstrap());
            dataMap.put("selfrepair", fault.getSelfrepair());
            dataMap.put("dealnote", fault.getDealnote());
            dataMap.put("maincontactor", fault.getMaincontactor());
            dataMap.put("runcontactor", fault.getRuncontactor());
            dataMap.put("circuit", fault.getCircuit());
            dataMap.put("carstatus", fault.getCarstatus());
            dataMap.put("direction", fault.getDirection());
            dataMap.put("iszone", fault.getIszone());
            dataMap.put("floor", fault.getFloor());
            dataMap.put("doorstatus", fault.getDoorstatus());
            dataMap.put("uplimit", fault.getUplimit());
            dataMap.put("downlimit", fault.getDownlimit());
            dataMap.put("alarm", fault.getAlarm());
            dataMap.put("hours", fault.getHours());
            dataMap.put("times", fault.getTimes());
            dataList.add(dataMap);
        }
        return dataList;

    }



    /**
     * 设置导出的实时运行信息显示格式
     * @param liftRunList
     * @return
     */
    public List<Map<String, Object>> setExportRealtimeExcelData(List<LiftRun> liftRunList){

        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

        for (int i = 0; i < liftRunList.size(); i++) {
            LiftRun lift = liftRunList.get(i);

            String isonline = lift.getIsonline();
            if ("0".equals(isonline)) {
                lift.setIsonline("否");
            }else if ("1".equals(isonline)) {
                lift.setIsonline("是");
            }

            String timestamps = lift.getTimestamps();
            if (timestamps != null && timestamps.length() > 12) {
                timestamps = timestamps.substring(0, 4) + "-" + timestamps.substring(4, 6) + "-" + timestamps.substring(6, 8) + " "
                        + timestamps.substring(8, 10) + ":" + timestamps.substring(10, 12) + ":" + timestamps.substring(12, 14);
            }

            String maincontactor = lift.getMaincontactor();
            if ("0".equals(maincontactor)) {
                lift.setMaincontactor("吸合");
            }else if ("1".equals(maincontactor)) {
                lift.setMaincontactor("断开");
            }else if (maincontactor == null) {
                lift.setMaincontactor(" ");
            }

            String runcontactor = lift.getRuncontactor();
            if ("0".equals(runcontactor)) {
                lift.setRuncontactor("吸合");
            }else if ("1".equals(runcontactor)) {
                lift.setRuncontactor("断开");
            }else if (runcontactor == null) {
                lift.setRuncontactor(" ");
            }

            String circuit = lift.getCircuit();
            if ("0".equals(circuit)) {
                lift.setCircuit("正常");
            }else if ("1".equals(circuit)) {
                lift.setCircuit("断开");
            }else if (circuit == null) {
                lift.setCircuit(" ");
            }

            String mode = lift.getMode();
            if ("0".equals(mode)) {
                lift.setMode("停止服务");
            }else if ("1".equals(mode)) {
                lift.setMode("正常运行");
            } else if ("2".equals(mode)) {
                lift.setMode("检修");
            } else if ("3".equals(mode)) {
                lift.setMode("消防返回");
            } else if ("4".equals(mode)) {
                lift.setMode("应急电源运行");
            } else if ("5".equals(mode)) {
                lift.setMode("其他模式");
            }else if (mode == null) {
                lift.setMode(" ");
            }

            String carstatus = lift.getCarstatus();
            if ("0".equals(carstatus)) {
                lift.setCarstatus("停止");
            }else if ("1".equals(carstatus)) {
                lift.setCarstatus("运行");
            }else if (carstatus == null) {
                lift.setCarstatus(" ");
            }

            String direction = lift.getDirection();
            if ("0".equals(direction)) {
                lift.setDirection("无方向");
            }else if ("1".equals(direction)) {
                lift.setDirection("上行");
            }else if ("2".equals(direction)) {
                lift.setDirection("下行");
            }else if (direction == null) {
                lift.setDirection(" ");
            }

            String iszone = lift.getIszone();
            if ("0".equals(iszone)) {
                lift.setIszone("否");
            }else if ("1".equals(iszone)) {
                lift.setIszone("是");
            }else if (iszone == null) {
                lift.setIszone(" ");
            }

            String floor = lift.getFloor();
            if(floor == null){
                lift.setFloor(" ");
            }

            String doorstatus = lift.getDoorstatus();
            if ("0".equals(doorstatus)) {
                lift.setDoorstatus("否");
            }else if ("1".equals(doorstatus)) {
                lift.setDoorstatus("是");
            }else if (doorstatus == null) {
                lift.setDoorstatus(" ");
            }

            String uplimit = lift.getUplimit();
            if ("0".equals(uplimit)) {
                lift.setUplimit("否");
            }else if ("1".equals(uplimit)) {
                lift.setUplimit("是");
            }else if (uplimit == null) {
                lift.setUplimit(" ");
            }

            String downlimit = lift.getDownlimit();
            if ("0".equals(downlimit)) {
                lift.setDownlimit("否");
            }else if ("1".equals(downlimit)) {
                lift.setDownlimit("是");
            }else if (downlimit == null) {
                lift.setDownlimit(" ");
            }

            String alarm = lift.getAlarm();
            if ("0".equals(alarm)) {
                lift.setAlarm("否");
            }else if ("1".equals(alarm)) {
                lift.setAlarm("是");
            }else if (alarm == null) {
                lift.setAlarm(" ");
            }

            String passenger = lift.getPassenger();
            if ("0".equals(passenger)) {
                lift.setPassenger("否");
            }else if ("1".equals(passenger)) {
                lift.setPassenger("是");
            }else if (passenger == null) {
                lift.setPassenger(" ");
            }

            String hours = lift.getHours();
            if (hours == null) {
                lift.setHours(" ");
            }

            String times = lift.getTimes();
            if (times == null) {
                lift.setTimes(" ");
            }

            Map<String, Object> dataMap = new LinkedHashMap<>();

            dataMap.put("regcode", lift.getRegcode());
            dataMap.put("isonline", lift.getIsonline());
            dataMap.put("updateTime", timestamps);
            dataMap.put("maincontactor", lift.getMaincontactor());
            dataMap.put("runcontactor", lift.getRuncontactor());
            dataMap.put("circuit", lift.getCircuit());
            dataMap.put("mode", lift.getMode());
            dataMap.put("carstatus", lift.getCarstatus());
            dataMap.put("direction", lift.getDirection());
            dataMap.put("iszone", lift.getIszone());
            dataMap.put("floor", lift.getFloor());
            dataMap.put("doorstatus", lift.getDoorstatus());
            dataMap.put("uplimit", lift.getUplimit());
            dataMap.put("downlimit", lift.getDownlimit());
            dataMap.put("alarm", lift.getAlarm());
            dataMap.put("passenger", lift.getPassenger());
            dataMap.put("hours", lift.getHours());
            dataMap.put("times", lift.getTimes());

            dataList.add(dataMap);
        }
        return dataList;
    }

    /**
     * 设置导出的文件名
     * @param startTime
     * @param endTime
     * @param fileName
     * @return
     */
    public String setFileName(String startTime, String endTime, String fileName) {

        if(!("".equals(startTime) || "".equals(endTime))) {
            if(startTime.equals(endTime)){
                fileName = fileName + "(" + startTime + ")";
            }else{
                fileName = fileName + "(" + startTime + "~" + endTime + ")";
            }
        }else if("".equals(startTime) && !"".equals(endTime)) {
            fileName = fileName + "(?~" + endTime + ")";
        }else if(!"".equals(startTime) && "".equals(endTime)) {
            fileName = fileName + "(" + startTime + "~?)";
        }else{
            fileName = fileName + "(全)";
        }
        return fileName;
    }



    /**
     * 导出上传的故障信息
     * @param response
     * @param regcode
     * @param istrap
     * @param deptId
     * @param queryStartTime
     * @param endTime
     */
    public void exportFaultrecordListExcelData(HttpServletResponse response, String regcode, String istrap,
                                               String deptId, String queryStartTime, String endTime, String liftCode) {

        List<LiftFaultAndLift> faultList = getFaultrecordListExportDate(regcode, istrap, deptId, queryStartTime, endTime,liftCode);

        List<Map<String, Object>> dataList = setFaultrecordListExportDate(faultList);

        JxlExcelUtils jxlUtils = new JxlExcelUtils();
        String sheetName = "故障记录";

        //设置列名
        List<String> columns = new ArrayList();
        columns.add("注册码");
        columns.add("识别码");
        columns.add("维保单位");
        columns.add("故障编号");
        columns.add("发生时间");
        columns.add("故障类型");
        columns.add("处置状态");
        columns.add("处置时间");
        columns.add("处置人");
        columns.add("是否困人");
        columns.add("是否自恢复");
        columns.add("处置说明");
        columns.add("总接触器");
        columns.add("运行接触器");
        columns.add("安全回路");
        columns.add("轿厢运行状态");
        columns.add("运行方向");
        columns.add("是否在门区");
        columns.add("发生故障楼层");
        columns.add("是否关门到位");
        columns.add("上极限是否动作");
        columns.add("下极限是否动作");
        columns.add("报警按钮是否动作");
        columns.add("累计运行小时数");
        columns.add("累计运行次数");

        String maintName = "";
        List<Maintain> maintainList = baseMaintainService.findMaintainDeptidList();//维保单位名称
        for (Maintain m : maintainList) {
            if (m.getDeptId().equals(deptId)) {
                maintName = m.getMaintainName();
            }
        }

        String fileName = maintName + "电梯历史故障记录";
        fileName = setFileName(queryStartTime, endTime, fileName);
        jxlUtils.exportexcle(response, fileName, dataList, sheetName, columns);


    }





    /**
     * 导出上传的实时运行信息
     * @param response
     * @param regcode
     * @param isonline
     * @param deptId
     * @param startTime
     * @param endTime
     */
    public void exportRealtimeExcelData(HttpServletResponse response, String regcode,
                                        String isonline, String deptId, String startTime, String endTime) {

        List<LiftRun> liftRunList = getExportRealtimeExcelData(regcode, isonline, deptId, startTime, endTime);

        List<Map<String, Object>> dataList = setExportRealtimeExcelData(liftRunList);

        JxlExcelUtils jxlUtils = new JxlExcelUtils();

        List<String> columns = new ArrayList();
        columns.add("注册码");
        columns.add("是否在线");
        columns.add("更新时间");
        columns.add("总接触器");
        columns.add("运行接触器");
        columns.add("安全回路");
        columns.add("运行模式");
        columns.add("轿厢运行状态");
        columns.add("运行方向");
        columns.add("轿厢是否在门区");
        columns.add("当前楼层");
        columns.add("关门到位");
        columns.add("上极限是否动作");
        columns.add("下极限是否动作");
        columns.add("报警按钮是否动作");
        columns.add("是否有人");
        columns.add("累计运行小时数");
        columns.add("累计运行次数");


        String maintName = "";
        List<Maintain> maintainList = baseMaintainService.findMaintainDeptidList();
        for (Maintain m : maintainList) {
            if (m.getDeptId().equals(deptId)) {
                maintName = m.getMaintainName();
            }
        }

        String fileName = maintName + "电梯运行实时数据";
        fileName = setFileName(startTime, endTime, fileName);
        String sheetName = "实时数据";
        jxlUtils.exportexcle(response, fileName, dataList, sheetName, columns);

    }



}
