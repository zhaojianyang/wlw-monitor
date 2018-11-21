package com.jshx.service;

import com.alibaba.fastjson.JSONObject;
import com.jshx.domain.LiftRepository;
import com.jshx.domain.RunstatusHistoryRepository;
import com.jshx.domain.RunstatusRepository;
import com.jshx.entity.*;
import com.jshx.utils.GenerateLog;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by YAO on 2017/6/12.
 */
@Service
public class RunstatusService {


    @Autowired
    RunstatusRepository runstatusRepository;


    @Autowired
    RunstatusHistoryRepository runstatusHistoryRepository;

    @Autowired
    LiftRepository liftRepository;


    @PersistenceContext
    private EntityManager em;



    public void saveRuns(List<LiftRun> liftRunList){

        runstatusRepository.save(liftRunList);
    }


    public void saveRun(LiftRun liftRun){
        runstatusRepository.save(liftRun);
    }


    public void saveRunHistory(LiftRunHistory liftRunHistory){
        runstatusHistoryRepository.save(liftRunHistory);
    }



    public void updateRun(LiftRun liftRun){
        runstatusRepository.saveAndFlush(liftRun);
    }

    public LiftRun findByRegcode(String regcode){
        LiftRun liftRun = runstatusRepository.findByRegcode(regcode);
        return  liftRun;
    }

    public  JSONObject findliftRunPage(String regcode,String isonline,String maintdeptid,int page,
                                       int pageSize,String allOrToday,String liftCode){
   //     List<User> list = userService.findAll(SortTools.basicSort(new SortDto("desc", "userName"), new SortDto("id")));

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");//
        String  nowDate = dateFormat.format(date);
        JSONObject json = new JSONObject();

        StringBuffer sqlMaintIds = new StringBuffer();
        sqlMaintIds.append("SELECT b.row_id FROM base_maintain b RIGHT JOIN department d  " +
                "ON b.deptid = d.row_id WHERE b.row_id is not null and b.maint_monitor = '1' ");

        Query MaintIdsquery = em.createNativeQuery(sqlMaintIds.toString());
        List maintIdsLift = MaintIdsquery.getResultList();

        StringBuffer hql = new StringBuffer();
        hql.append("SELECT new com.jshx.entity.LiftRunAndLift(l.deptId,l.regcode,l.isonline ,l.timestamps,l.maincontactor,l.runcontactor," +
                "l.circuit,l.mode,l.carstatus,l.direction,l.iszone,l.floor,l.doorstatus,l.uplimit," +
                "l.downlimit,l.alarm,l.passenger,l.hours,l.times,f.liftcode,l.updateTime) FROM LiftRun l,Lift f WHERE 1=1 AND l.regcode = f.regcode ");

        if(!StringUtils.isEmpty(regcode)){
            hql.append(" AND l.regcode like '%" + regcode + "%'");
        }
        if(!StringUtils.isEmpty(isonline)){
            hql.append(" AND l.isonline = '" + isonline + "'");
        }
        if(!StringUtils.isEmpty(maintdeptid)){
            hql.append(" AND l.deptId = '" + maintdeptid + "'");
        }
        if(!StringUtils.isEmpty(liftCode)){
            hql.append("and f.liftcode like '%"+liftCode+"%'");
        }
        if(maintIdsLift.size()>0){
            hql.append(" AND f.maintainid in(:list)");
        }
        //当有维保单位变更后,会查询出多余的数据,刚上传时是该单位维保,后来变了,就会出现垃圾数据

        /*hql.append(" ORDER BY l.timestamps,f.isDemand DESC ");*/
        hql.append(" ORDER BY l.timestamps DESC ");
        Query query = em.createQuery(hql.toString());
        query.setParameter("list", maintIdsLift);
        //查询当天的实时数据,还有首页需要知道的多少条数据
        if(!StringUtils.isEmpty(nowDate) && ("today".equals(allOrToday) || "allAndToday".equals(allOrToday))){
            //hql.append(" AND l.timestamps like '%" + nowDate + "%'");
            /*hql.insert(hql.indexOf("ORDER") - 1, " AND l.timestamps like '%" + nowDate + "%' ");*/
            if("allAndToday".equals(allOrToday)){
                //查询试点的电梯个数,有可能和维保单位上传的监控数据不统一,并没有全部把试点电梯数据上传进库
                StringBuffer maintOntimeHql = new StringBuffer();
                maintOntimeHql.append("SELECT new com.jshx.entity.LiftRunAndLift(f.regcode,f.liftcode) FROM Lift f WHERE f.isDemand = '1' ");

                Query maintOntimeQuery = em.createQuery(maintOntimeHql.toString());
                List<LiftRunAndLift> maintOntimeData = maintOntimeQuery.getResultList();
                json.put("maintOntimeNum",maintOntimeData.size());
                /*json.put("maintOntimeNum","441");*/
            }
            if("today".equals(allOrToday)){
                hql.insert(hql.indexOf("ORDER") - 1, " AND f.isDemand = '1' ");
                Query todayQuery = em.createQuery(hql.toString());
                todayQuery.setParameter("list", maintIdsLift);
                List<LiftRunAndLift> todayRunLiftListTotal = todayQuery.getResultList();
                todayQuery.setFirstResult(page*pageSize);
                todayQuery.setMaxResults(pageSize);
                List<LiftRunAndLift> todayData = todayQuery.getResultList();
                int todayPages = (int)Math.ceil((double)todayRunLiftListTotal.size()/pageSize);
                json.put("todayData",todayData);
                json.put("todayDataNum",todayRunLiftListTotal.size());
                /*json.put("todayDataNum","441");*/
                json.put("todayPages",todayPages);
            }

        }
        //查询所有的实时数据,还有首页需要知道的多少条数据
        if("all".equals(allOrToday) || "allAndToday".equals(allOrToday)){
            List<LiftRunAndLift> runLiftListTotal = query.getResultList();
            json.put("total",runLiftListTotal.size());
            /*json.put("total","3512");*/
            if("all".equals(allOrToday)){
                query.setFirstResult(page*pageSize);
                query.setMaxResults(pageSize);
                List<LiftRunAndLift> rundatas = query.getResultList();
                int pages = (int)Math.ceil((double)runLiftListTotal.size()/pageSize);
                json.put("rundatas",rundatas);
                json.put("pages",pages);
            }
        }
        json.put("page",page);
/*

        //如有需要要把Lift对象赋值给结果集
        if(!StringUtils.isEmpty(regcode)){
            StringBuffer baseLiftHql = new StringBuffer();
            baseLiftHql.append("SELECT f FROM Lift f WHERE 1=1 AND f.regcode like '%" + regcode + "%' ");
            Query baseLiftQuery = em.createQuery(baseLiftHql.toString());
            List<Lift> baseLift = baseLiftQuery.getResultList();
        }
*/
        return  json;
    }





    public  List<LiftDto> getMintorLiftRealtimeByRegcode(String regcode) {
        StringBuffer hql = new StringBuffer();
        hql.append("SELECT new com.jshx.entity.LiftDto(l.regcode,l.liftcode ,l.liftaddress,l.latitude,l.longitude,m.maintainName) FROM Lift l ,Maintain m  where l.maintainid = m.id " );

        if(!org.thymeleaf.util.StringUtils.isEmpty(regcode)){
            hql.append(" AND l.regcode = '" + regcode +"'");
        }

        Query query = em.createQuery(hql.toString());
        List<LiftDto> list =query.getResultList();

        return list;
    }


    /**
     * 获取实时运行数据的时间列表
     * @return
     */
    public  List<LiftRunTime> getLiftRunTimeList() {
        StringBuffer hql = new StringBuffer();
        hql.append("SELECT left(w.timestamps,8) AS time FROM wlw_runstatus w GROUP BY time ORDER BY w.timestamps DESC;");

        Query query = em.createNativeQuery(hql.toString());
        List<LiftRunTime> timeList = new ArrayList<>();
        List result = query.getResultList();

        for(int i=0;i<result.size();i++){
            LiftRunTime lift = new LiftRunTime();
            String timestamps = (String)result.get(i);
            lift.setReallytime(timestamps);
            if (timestamps != null && timestamps.length() >= 8) {
                timestamps = timestamps.substring(0,4)+"-"+timestamps.substring(4,6)+"-"+timestamps.substring(6,8);
            }
            lift.setTimestamps(timestamps);
            timeList.add(lift);
        }

        return timeList;
    }



    /**
     * 获取电梯历史运行数据信息
     * @return
     */
    public  JSONObject findLiftRunHistoriesListByRegcode(String regcode, int page, int pageSize) {

        Sort s = new Sort(Sort.Direction.DESC, "timestamps");
        Pageable pageable = new PageRequest(page,pageSize,s);
        Lift baseLift = liftRepository.findByRegcode(regcode);
        Page<LiftRunHistory> result = runstatusHistoryRepository.findLiftRunHistoriesByRegcode(regcode,pageable);
        JSONObject obj = new JSONObject();
        obj.put("total",result.getTotalElements());
        obj.put("pages",result.getTotalPages());
        obj.put("page",page);
        obj.put("liftRunHistory",result.getContent());
        obj.put("baseLift",baseLift);

        return obj;
    }


    /**
     * 查询维保单位上传电梯但是不在9633库的电梯
     * @param regcode
     * @param isonline
     * @param maintdeptid
     * @param page
     * @param pageSize
     * @param allOrToday
     * @param liftCode
     * @return
     */
    public JSONObject findNotInBaseLiftTableLifts(String regcode,String isonline,String maintdeptid,int page,
                                                  int pageSize,String allOrToday,String liftCode){
        JSONObject json = new JSONObject();


        StringBuffer sql = new StringBuffer();
        sql.append("SELECT w.deptId,w.regcode,w.isonline ,ifnull(w.timestamps,'') as timestamps," +
                "ifnull(w.maincontactor,'') as maincontactor,ifnull(w.runcontactor,'') as runcontactor, " +
                " ifnull(w.circuit,'') as circuit,ifnull(w.mode,'') as mode,ifnull(w.carstatus,'') as carstatus," +
                " ifnull(w.direction,'') as direction,ifnull(w.iszone,'') as iszone,ifnull(w.floor,'') as floor," +
                " ifnull(w.doorstatus,'') as doorstatus,ifnull(w.uplimit,'') as uplimit, ifnull(w.downlimit,'') as downlimit," +
                " ifnull(w.alarm,'') as alarm,ifnull(w.passenger,'') as passenger,ifnull(w.hours,'') as hours," +
                " ifnull(w.run_times,'') as times FROM wlw_runstatus w   " +
                " LEFT JOIN base_lift l ON w.regcode = l.regcode WHERE l.row_id is null ");
        if(!StringUtils.isEmpty(regcode)){
            sql.append(" AND w.regcode like '%" + regcode + "%'");
        }
        if(!StringUtils.isEmpty(isonline)){
            sql.append(" AND w.isonline = '" + isonline + "'");
        }
        if(!StringUtils.isEmpty(maintdeptid)){
            sql.append(" AND w.deptId = '" + maintdeptid + "'");
        }
        if(!StringUtils.isEmpty(maintdeptid)){
            sql.append(" AND w.deptId = '" + maintdeptid + "'");
        }

        if(!StringUtils.isEmpty(liftCode)){
            sql.append(" AND l.liftcode like '" + liftCode + "'");
        }

        sql.append(" ORDER BY w.timestamps DESC ");
        Query query = em.createNativeQuery(sql.toString());

        List notInLiftsAll = query.getResultList();
        query.setFirstResult(page*pageSize);
        query.setMaxResults(pageSize);
        List notInLifts = query.getResultList();

        List<LiftRunAndLift> rundatas = new ArrayList<>();
        for(int i=0;i<notInLifts.size();i++){
            Object[] cells = (Object[]) notInLifts.get(i);
            LiftRunAndLift lift = new LiftRunAndLift();

            lift.setDeptId(cells[0].toString());
            lift.setRegcode(cells[1].toString());
            lift.setIsonline(cells[2].toString());
            lift.setTimestamps(cells[3].toString());
            lift.setMaincontactor(cells[4].toString());
            lift.setRuncontactor(cells[5].toString());
            lift.setCircuit(cells[6].toString());
            lift.setMode(cells[7].toString());
            lift.setCarstatus(cells[8].toString());
            lift.setDirection(cells[9].toString());
            lift.setIszone(cells[10].toString());
            lift.setFloor(cells[11].toString());
            lift.setDoorstatus(cells[12].toString());
            lift.setUplimit(cells[13].toString());
            lift.setDownlimit(cells[14].toString());
            lift.setAlarm(cells[15].toString());
            lift.setPassenger(cells[16].toString());
            lift.setHours(cells[17].toString());
            lift.setTimes(cells[18].toString());
            lift.setLiftCode("");

            rundatas.add(lift);
        }


        int pages = (int)Math.ceil((double)notInLiftsAll.size()/pageSize);
        json.put("rundatas",rundatas);
        json.put("total",notInLiftsAll.size());
        json.put("pages",pages);

        json.put("page",page);


        return json;
    }

    /**
     * 根据维保单位deptid获取maintId,即base_maintain主键
     * @param deptId
     * @return
     */
    public String getMaintIdByDeptId(String deptId){

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT b.row_id as maintId FROM base_maintain b RIGHT JOIN department d " +
                "ON b.deptid = d.row_id " +
                "WHERE b.row_id is not null and b.deptid = '" + deptId + "'");
        Query query = em.createNativeQuery(sql.toString());
        List list = query.getResultList();
        String mainId = "";

        if(list.size()>0){
            mainId = (String)list.get(0);
        }
        return mainId;
    }


    /**
     * 查询电梯是自己制造,但是并不属于自己维保,但是却由自己上传入库了
     * @param regcode
     * @param liftCode
     * @param isonline
     * @param deptId
     * @param maintId
     * @param page
     * @param pageSize
     * @return
     */
    public JSONObject findNotBelongToUplodaMaint(String regcode,String liftCode,String isonline,String deptId,
                                                 String maintId,int page,int pageSize){

        JSONObject json = new JSONObject();

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT t.* ,bm.maintain_name AS maintName FROM  " +
                "( \n" +
                "SELECT w.deptId,w.regcode,w.isonline ,ifnull(w.timestamps,'') as timestamps, " +
                "ifnull(w.maincontactor,'') as maincontactor,ifnull(w.runcontactor,'') as runcontactor, " +
                "ifnull(w.circuit,'') as circuit,ifnull(w.mode,'') as mode,ifnull(w.carstatus,'') as carstatus, " +
                "ifnull(w.direction,'') as direction,ifnull(w.iszone,'') as iszone,ifnull(w.floor,'') as floor, " +
                "ifnull(w.doorstatus,'') as doorstatus,ifnull(w.uplimit,'') as uplimit, ifnull(w.downlimit,'') as downlimit, " +
                "ifnull(w.alarm,'') as alarm,ifnull(w.passenger,'') as passenger,ifnull(w.hours,'') as hours, " +
                "ifnull(w.run_times,'') as times,bl.liftcode,bl.maintainid AS maintId " +
                "FROM wlw_runstatus w LEFT JOIN base_lift bl " +
                "ON w.regcode = bl.regcode   WHERE w.deptid = '" + deptId + "' " +
                "AND bl.maintainid <> '" + maintId + "')t " +
                "LEFT JOIN base_maintain bm ON t.maintId = bm.row_id WHERE 1=1 ");
        if(!StringUtils.isEmpty(regcode)){
            sql.append(" AND t.regcode like '%" + regcode + "%'");
        }
        if(!StringUtils.isEmpty(isonline)){
            sql.append(" AND t.isonline = '" + isonline + "'");
        }/*
        if(!StringUtils.isEmpty(deptId)){
            sql.append(" AND t.deptId = '" + deptId + "'");
        }*/
        if(!StringUtils.isEmpty(liftCode)){
            sql.append(" AND t.liftCode = '" + liftCode + "'");
        }

        sql.append(" ORDER BY t.timestamps DESC ");
        Query query = em.createNativeQuery(sql.toString());

        List notInLiftsAll = query.getResultList();
        query.setFirstResult(page*pageSize);
        query.setMaxResults(pageSize);
        List notInLifts = query.getResultList();

        List<LiftRunAndLift> rundatas = new ArrayList<>();
        for(int i=0;i<notInLifts.size();i++){
            Object[] cells = (Object[]) notInLifts.get(i);
            LiftRunAndLift lift = new LiftRunAndLift();

            lift.setDeptId(cells[0].toString());
            lift.setRegcode(cells[1].toString());
            lift.setIsonline(cells[2].toString());
            lift.setTimestamps(cells[3].toString());
            lift.setMaincontactor(cells[4].toString());
            lift.setRuncontactor(cells[5].toString());
            lift.setCircuit(cells[6].toString());
            lift.setMode(cells[7].toString());
            lift.setCarstatus(cells[8].toString());
            lift.setDirection(cells[9].toString());
            lift.setIszone(cells[10].toString());
            lift.setFloor(cells[11].toString());
            lift.setDoorstatus(cells[12].toString());
            lift.setUplimit(cells[13].toString());
            lift.setDownlimit(cells[14].toString());
            lift.setAlarm(cells[15].toString());
            lift.setPassenger(cells[16].toString());
            lift.setHours(cells[17].toString());
            lift.setTimes(cells[18].toString());
            lift.setLiftCode(cells[19].toString());
            lift.setMaintId(cells[20].toString());
            lift.setMaintName(cells[21].toString());

            rundatas.add(lift);
        }

        int pages = (int)Math.ceil((double)notInLiftsAll.size()/pageSize);
        json.put("rundatas",rundatas);
        json.put("total",notInLiftsAll.size());
        json.put("pages",pages);

        json.put("page",page);
        json.put("msg","maintError");

        return json;
    }



    /**
     * 删除部分电梯历史运行数据信息,只保留三天的数据
     * @return
     */
    public  void deleteLiftRunHistoriesByCreateTime() {

        Logger logger = Logger.getLogger(GenerateLog.class);//记录错误和警告日志

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -3);
        date = calendar.getTime();
        String nowDate = sdf.format(date);//将时间设置到三天前

        try{
            int row = runstatusHistoryRepository.deleteLiftRunHistoriesByCreateTime(sdf.parse(nowDate));
            logger.warn(sf.format(new Date()) + " 共删除 wlw_runstatus_history 表中 " +
                    nowDate +" 之前数据: " + row + " 条;  ");
        }catch (Exception e){
            logger.warn(e);
            e.printStackTrace();
        }

    }




}
