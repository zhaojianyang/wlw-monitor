package com.jshx.service;


import com.jshx.entity.StatisticAnalysis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZHAO on 2017/8/03.
 */
@Service
public class StatisticAnalysisService {

    @PersistenceContext
    private  EntityManager em;


    /**
     * 分页查询统计分析维保详情的信息列表
     * @param page
     * @param pageSize
     * @return
     */
    public Page<StatisticAnalysis> findStatisticAnalysisListInfos (int page, int pageSize){
        StringBuffer hql = new StringBuffer();

        /*这是在数据没出问题之前的sql,问题即上传的运行数据有的不在base_lift表中,日立上传的电梯还有部分不属于他们维保
        hql.append("\n" +
                "SELECT j.*,k.joinLift FROM\n" +
                "(SELECT c.maintName,c.deptId,coalesce(c.ownLift,0) AS ownLift,\n" +
                " ifnull(d.uploadCount,0) AS uploadCount,ifnull(d.coverLift,0) AS coverLift,c.createTime,c.updateTime\n" +
                "FROM\n" +
                "(SELECT a.*,ifnull(MIN(w.createtime),\"\") AS createTime,ifnull(MAX(w.updatetime),\"\") AS updateTime FROM\n" +
                "(SELECT t.maintName,t.ownLift,t.deptId from \n" +
                "(SELECT m.MAINTAIN_NAME AS maintName,count(*) AS ownLift,\n" +
                "m.deptid AS deptId FROM base_lift b \n" +
                "RIGHT JOIN base_maintain m ON b.MAINTAINID = m.ROW_ID \n" +
                "GROUP BY m.MAINTAIN_NAME)t\n" +
                "INNER JOIN department d ON t.deptId = d.ROW_ID)a \n" +
                "LEFT JOIN wlw_runstatus w on w.DEPTID = a.deptId \n" +
                "GROUP BY w.DEPTID)c\n" +
                "LEFT JOIN\n" +
                "(SELECT a.dept_name,a.deptid,a.uploadCount,b.coverLift FROM\n" +
                "(SELECT d.dept_name,w.DEPTID,COUNT(*) AS uploadCount FROM wlw_faultrecord w INNER JOIN department d \n" +
                "ON w.DEPTID = d.ROW_ID\n" +
                " GROUP BY d.dept_name)a LEFT JOIN\n" +
                "(SELECT d.dept_name,t.DEPTID,count(*) AS coverLift FROM\n" +
                "(SELECT DEPTID from wlw_faultrecord GROUP BY REGCODE, DEPTID)t\n" +
                "INNER JOIN department d ON t.DEPTID = d.ROW_ID \n" +
                "GROUP BY d.dept_name)b ON a.DEPTID = b.DEPTID)d \n" +
                "ON c.deptId = d.deptid \n" +
                "ORDER BY c.updateTime DESC) j INNER JOIN \n" +
                " (\n" +
                "SELECT u.*,count(*) joinLift FROM(\n" +
                "(SELECT d.dept_name,w.DEPTID,w.REGCODE FROM\n" +
                "wlw_faultrecord w \n" +
                "INNER JOIN department d ON w.DEPTID = d.ROW_ID \n" +
                "GROUP BY w.REGCODE,DEPTID \n" +
                "ORDER BY w.DEPTID )\n" +
                "UNION \n" +
                "(SELECT a.*,w.REGCODE FROM\n" +
                "(SELECT t.matintname,t.deptid from \n" +
                "(SELECT m.MAINTAIN_NAME matintname,m.DEPTID deptid FROM base_lift b RIGHT JOIN base_maintain m ON b.MAINTAINID = m.ROW_ID \n" +
                "GROUP BY m.MAINTAIN_NAME)t\n" +
                "INNER JOIN department d ON t.deptid = d.ROW_ID)a \n" +
                "RIGHT JOIN wlw_runstatus w on w.DEPTID = a.deptid \n" +
                "GROUP BY w.REGCODE ORDER BY w.DEPTID)\n" +
                ")u\n" +
                "GROUP BY u.DEPTID\n" +
                ")k \n" +
                "ON j.deptId = k.deptid\n" +
                "ORDER BY j.deptId");*/
        hql.append("SELECT j.*,k.joinLift FROM \n" +
                "(SELECT c.maintName,c.deptId,coalesce(c.ownLift,0) AS ownLift, \n" +
                " ifnull(d.uploadCount,0) AS uploadCount,ifnull(d.coverLift,0) AS coverLift,c.createTime,c.updateTime\n" +
                "FROM\n" +
                "(SELECT a.*,ifnull(MIN(w.createtime),\"\") AS createTime,ifnull(MAX(w.updatetime),\"\") AS updateTime FROM\n" +
                "(SELECT t.maintName,t.ownLift,t.deptId from \n" +
                "(SELECT m.MAINTAIN_NAME AS maintName,count(*) AS ownLift,\n" +
                "m.deptid AS deptId FROM base_lift b \n" +
                "RIGHT JOIN base_maintain m ON b.MAINTAINID = m.ROW_ID \n" +
                "GROUP BY m.MAINTAIN_NAME)t \n" +
                "INNER JOIN department d ON t.deptId = d.ROW_ID)a \n" +
                "LEFT JOIN wlw_runstatus w on w.DEPTID = a.deptId \n" +
                "GROUP BY w.DEPTID)c \n" +
                "LEFT JOIN \n" +
                "(SELECT a.dept_name,a.deptid,a.uploadCount,b.coverLift FROM \n" +
                "(SELECT d.dept_name,w.DEPTID,COUNT(*) AS uploadCount FROM wlw_faultrecord w INNER JOIN department d \n" +
                "ON w.DEPTID = d.ROW_ID \n" +
                " GROUP BY d.dept_name)a LEFT JOIN\n" +
                "(SELECT d.dept_name,t.DEPTID,count(*) AS coverLift FROM \n" +
                "(SELECT DEPTID from wlw_faultrecord GROUP BY REGCODE, DEPTID)t \n" +
                "INNER JOIN department d ON t.DEPTID = d.ROW_ID \n" +
                "GROUP BY d.dept_name)b ON a.DEPTID = b.DEPTID)d \n" +
                "ON c.deptId = d.deptid \n" +
                "ORDER BY c.updateTime DESC) j INNER JOIN \n" +
                " (\n" +
                "SELECT u.*,count(*) joinLift FROM(\n" +
                "SELECT m.MAINTAIN_NAME matintname,a.* FROM \n" +
                "(SELECT regcode,deptid FROM wlw_faultrecord \n" +
                "UNION  SELECT regcode,deptid FROM wlw_runstatus)a LEFT JOIN base_maintain m \n" +
                "ON a.deptid = m.deptid\n" +
                ")u\n" +
                "GROUP BY u.DEPTID \n" +
                ")k \n" +
                "ON j.deptId = k.deptid \n" +
                "ORDER BY j.deptId \n");

        Query query = em.createNativeQuery(hql.toString());
        List totalList = query.getResultList();

        if(page == 0){
            page = 1;
        }
        int start = (page-1)* pageSize;
        query.setFirstResult(start);
        query.setMaxResults(pageSize);

        List querylist = query.getResultList();
        List<StatisticAnalysis> result = new ArrayList<>();

        if(!CollectionUtils.isEmpty(querylist)){
            for(int i=0;i<querylist.size();i++){
                StatisticAnalysis ss = new StatisticAnalysis();
                Object[] cells = (Object[]) querylist.get(i);
                ss.setMaintName(cells[0].toString());
                ss.setDeptId(cells[1].toString());
                ss.setOwnLift(cells[2].toString());

/*                if("8a8181f65c9f3816015c9f3e0cde0006".equals(cells[1].toString())){
                    ss.setJoinLift("2995");
                }else{*/
                    ss.setJoinLift(cells[7].toString());
/*                }*/
                ss.setUploadCount(cells[3].toString());
                ss.setCoverLift(cells[4].toString());
                ss.setCreateTime(cells[5].toString().split(" ")[0]);
                ss.setUpdateTime(cells[6].toString().split(" ")[0]);
                result.add(ss);
            }
        }

        int total = totalList.size();

        Pageable pageable = new PageRequest(page-1,pageSize);
        Page<StatisticAnalysis>  analysisListPage = new PageImpl<StatisticAnalysis>(result,pageable,total);
        return  analysisListPage;
    }




    /**
     * 以维保单位查询监控运行的电梯
     * @param maintDeptid
     * @param page
     */
    public Page<StatisticAnalysis> getLiftListGroupByMaintDepartment (String maintDeptid,int page){
        StringBuffer hql = new StringBuffer();

        hql.append("" +
                "SELECT a.*,count(ww.DEPTID) AS joinLift,ifnull(MIN(ww.createtime),\"\") AS createTime,\n" +
                "ifnull(MAX(ww.updatetime),\"\") AS updateTime FROM " +
                "(SELECT t.maintName,t.ownLift,t.deptId,t.maintId,t.maintAddress from \n" +
                "(SELECT m.MAINTAIN_NAME AS maintName,m.officeaddr AS maintAddress,count(*) AS ownLift," +
                "m.deptid AS deptId,b.maintainid AS maintId FROM base_lift b  " +
                "RIGHT JOIN base_maintain m ON b.MAINTAINID = m.ROW_ID  " +
                "GROUP BY m.MAINTAIN_NAME)t " +
                "LEFT JOIN department d ON t.deptId = d.ROW_ID)a  " +
                "RIGHT JOIN " +
                "(SELECT w.* FROM wlw_runstatus w LEFT JOIN base_lift bl ON w.regcode = bl.regcode \n" +
                "WHERE bl.row_id IS NOT NULL AND bl.maintainid in \n" +
                "(SELECT b.row_id FROM base_maintain b RIGHT JOIN department d \n" +
                "ON b.deptid = d.row_id " +
                "WHERE b.row_id is not null and b.maint_monitor = '1' )) ww " +
                "on ww.DEPTID = a.deptId " +
                "WHERE a.deptId like '%"+maintDeptid+"%'  " +
                "GROUP BY ww.DEPTID  " +
                "ORDER BY a.deptId DESC");


        /*hql.append("" +
                "SELECT a.*,count(w.DEPTID) AS joinLift,ifnull(MIN(w.createtime),\"\") AS createTime,\n" +
                "ifnull(MAX(w.updatetime),\"\") AS updateTime FROM\n" +
                "(SELECT t.maintName,t.ownLift,t.deptId,t.maintId,t.maintAddress from \n" +
                "(SELECT m.MAINTAIN_NAME AS maintName,m.officeaddr AS maintAddress,count(*) AS ownLift," +
                "m.deptid AS deptId,b.maintainid AS maintId FROM base_lift b \n" +
                "RIGHT JOIN base_maintain m ON b.MAINTAINID = m.ROW_ID \n" +
                "GROUP BY m.MAINTAIN_NAME)t\n" +
                "LEFT JOIN department d ON t.deptId = d.ROW_ID)a \n" +
                "RIGHT JOIN wlw_runstatus w on w.DEPTID = a.deptId\n" +
                "WHERE a.deptId like '%"+maintDeptid+"%'  \n" +
                "GROUP BY w.DEPTID  \n" +
                "ORDER BY a.deptId DESC");*/

        /*hql.append("" +
                "\n" +
                "SELECT tt.* FROM \n" +
                "(SELECT a.*,count(w.DEPTID) AS joinLift,ifnull(MIN(w.createtime),\"\") AS createTime,\n" +
                "ifnull(MAX(w.updatetime),\"\") AS updateTime FROM \n" +
                "(SELECT t.maintName,t.ownLift,t.deptId,t.maintId,t.maintAddress from \n" +
                "(SELECT m.MAINTAIN_NAME AS maintName,m.officeaddr AS maintAddress,count(*) AS ownLift, \n" +
                "m.deptid AS deptId,b.maintainid AS maintId FROM base_lift b  \n" +
                "RIGHT JOIN base_maintain m ON b.MAINTAINID = m.ROW_ID  \n" +
                "GROUP BY m.MAINTAIN_NAME)t \n" +
                "RIGHT JOIN department d ON t.deptId = d.ROW_ID)a \n" +
                "LEFT JOIN wlw_runstatus w on w.DEPTID = a.deptId \n" +
                "WHERE a.deptId like '%"+ maintDeptid +"%'  \n" +
                "GROUP BY w.DEPTID \n" +
                "ORDER BY a.deptId DESC\n" +
                ")tt INNER JOIN wlw_runstatus wr \n" +
                "ON tt.deptId = wr.deptid \n" +
                "GROUP BY wr.deptid \n" +
                "ORDER BY wr.deptId DESC\n" +
                "\n");*/

        Query query = em.createNativeQuery(hql.toString());

        List totalList = query.getResultList();

        int start = (page-1) * 10;
        query.setFirstResult(start);
        query.setMaxResults(10);

        List resultlist = query.getResultList();

        List<StatisticAnalysis> result = new ArrayList<>();

        if(!CollectionUtils.isEmpty(resultlist)){
            for(int i=0;i<resultlist.size();i++){
                StatisticAnalysis aa = new StatisticAnalysis();
                Object[] cells = (Object[]) resultlist.get(i);
                aa.setMaintName(cells[0].toString());
                aa.setDeptId(cells[2].toString());
                aa.setOwnLift(cells[1].toString());
                aa.setMaintId(cells[3].toString());
                aa.setMaintAddress(cells[4].toString());
                aa.setJoinLift(cells[5].toString());
                aa.setCreateTime(cells[6].toString());
                aa.setUpdateTime(cells[7].toString());
                result.add(aa);
            }
        }
        int total = totalList.size();

        Pageable pageable = new PageRequest(page-1,10);
        Page<StatisticAnalysis>  liftListPage = new PageImpl<StatisticAnalysis>(result,pageable,total);
        return  liftListPage;
    }




    /**
     * 以使用单位查询监控运行的电梯
     * @param companyId
     * @param page
     */
    public Page<StatisticAnalysis> getLiftListGroupByUseCompanyId (String companyId,int page){



        StringBuffer hql = new StringBuffer();
        hql.append("" +
                "SELECT t.companyId,MAX(t.updateTime) AS updateTime, m.company_name AS companyName,\n" +
                "ifnull(m.companyaddr,\"\") AS companyAddress,count(*) AS joinLift FROM\n" +
                "(SELECT b.regcode AS regcode,b.companyid AS companyId," +
                "b.liftcode AS liftCode,b.liftaddress AS liftAddress,\n" +
                "b.placetype AS placeType,ifnull(w.updatetime,\"\") AS updateTime \n" +
                "FROM wlw_runstatus w LEFT JOIN base_lift b\n" +
                "ON w.regcode = b.regcode)t LEFT JOIN base_company m\n" +
                "ON t.companyId = m.row_id WHERE t.companyId like '%" + companyId + "%' \n" +
                "GROUP BY m.row_id \n" +
                "ORDER BY t.companyId ASC "
        );

        Query query = em.createNativeQuery(hql.toString());

        List totalList = query.getResultList();

        int start = (page-1) * 10;
        query.setFirstResult(start);
        query.setMaxResults(10);

        List resultlist = query.getResultList();

        List<StatisticAnalysis> result = new ArrayList<>();

        if(!CollectionUtils.isEmpty(resultlist)){
            for(int i=0;i<resultlist.size();i++){
                StatisticAnalysis sa = new StatisticAnalysis();
                Object[] cells = (Object[]) resultlist.get(i);
                sa.setCompanyId(cells[0].toString());
                sa.setUpdateTime(cells[1].toString());
                sa.setCompanyName(cells[2].toString());
                sa.setCompanyAddress(cells[3].toString());
                sa.setJoinLift(cells[4].toString());
                result.add(sa);
            }
        }

       int total = totalList.size();


        Pageable pageable = new PageRequest(page-1,10);

        Page<StatisticAnalysis>  liftListPage = new PageImpl<StatisticAnalysis>(result,pageable,total);
        return  liftListPage;
    }




    /**
     * 以覆盖区域监控运行的电梯
     * @param areaId
     * @param page
     */
    public Page<StatisticAnalysis> getLiftListGroupByAreaId (String areaId,int page){
        StringBuffer hql = new StringBuffer();
        hql.append("" +
                "SELECT f.areaId,ba.area_name AS liftAreaName,MAX(f.updateTime) AS updateTime,count(*) AS joinLift FROM\n" +
                "(SELECT b.areaid AS areaId,ifnull(w.updatetime,\"\") AS updateTime \n" +
                "FROM wlw_runstatus w LEFT JOIN base_lift b\n" +
                "ON w.regcode = b.regcode)f LEFT JOIN base_area ba \n" +
                "ON f.areaId = ba.row_id  \n" +
                "WHERE f.areaId like '%" + areaId + "%' \n" +
                "GROUP BY f.areaId\n" +
                "ORDER BY f.areaId ASC");

        Query query = em.createNativeQuery(hql.toString());
        List totalList = query.getResultList();

        int start = (page-1) * 10;
        query.setFirstResult(start);
        query.setMaxResults(10);

        List resultlist = query.getResultList();

        List<StatisticAnalysis> result = new ArrayList<>();

        if(!CollectionUtils.isEmpty(resultlist)){
            for(int i=0;i<resultlist.size();i++){
                StatisticAnalysis aList = new StatisticAnalysis();
                Object[] cells = (Object[]) resultlist.get(i);
                aList.setAreaId(cells[0].toString());
                aList.setLiftAreaName(cells[1].toString());
                aList.setUpdateTime(cells[2].toString());
                aList.setJoinLift(cells[3].toString());
                result.add(aList);
            }
        }

        int total = totalList.size();

        Pageable pageable = new PageRequest(page-1,10);
        Page<StatisticAnalysis>  liftListPage = new PageImpl<StatisticAnalysis>(result,pageable,total);
        return  liftListPage;
    }


    /**
     * 根据维保单位或使用单位或区域查询所有监控运行的电梯
     * @param searchValue
     * @param page
     */
    public Page<StatisticAnalysis> getAllLiftsByDifferentTypeId (String searchValue,int page){
        StringBuffer hql = new StringBuffer();
        hql.append("" +
                "\n" +
                "SELECT ttt.*,ba.area_name AS liftAreaName FROM \n" +
                "(\n" +
                "SELECT tt.*,ifnull(bc.company_name,\"\") AS compayName,ifnull(bc.companyaddr,\"\") AS companyAddress FROM\n" +
                "(\n" +
                "SELECT t.*,bm.maintain_name AS maintName,bm.officeaddr AS maintAddress FROM\n" +
                "(\n" +
                "SELECT bl.regcode AS regcode,bl.maintainid AS maintId,ifnull(bl.companyid,\"\") AS companyId,bl.areaid AS areaId,\n" +
                "wr.deptid AS deptId,bl.liftcode AS liftCode,bl.liftaddress AS liftAddress,\n" +
                "bl.placetype AS placeType,ifnull(wr.updatetime,\"\") AS updateTime  \n" +
                "FROM wlw_runstatus wr LEFT JOIN base_lift bl\n" +
                "ON wr.regcode = bl.regcode AND bl.row_id is not null  ORDER BY bl.isDemand DESC \n" +
                ")t LEFT JOIN base_maintain bm \n" +
                "ON t.maintId = bm.row_id)tt LEFT JOIN base_company bc \n" +
                "ON tt.companyId = bc.row_id)ttt LEFT JOIN base_area ba \n" +
                "ON ttt.areaId = ba.row_id\n" +
                "WHERE ttt.maintId like '%" + searchValue + "%'  \n" +
                "OR ttt.companyId like '%" + searchValue + "%' \n" +
                "OR ttt.areaId like '%" + searchValue + "%' \n");
                /*"ORDER BY ttt.updateTime DESC");*/

        Query query = em.createNativeQuery(hql.toString());
        List totalList = query.getResultList();

        int start = (page-1) * 6;
        query.setFirstResult(start);
        query.setMaxResults(6);

        List resultlist = query.getResultList();

        List<StatisticAnalysis> result = new ArrayList<>();

        if(!CollectionUtils.isEmpty(resultlist)){
            for(int i=0;i<resultlist.size();i++){
                StatisticAnalysis allList = new StatisticAnalysis();
                Object[] cells = (Object[]) resultlist.get(i);
                allList.setRegcode(cells[0].toString());
                allList.setMaintId(cells[1].toString());
                allList.setCompanyId(cells[2].toString());
                allList.setAreaId(cells[3].toString());
                allList.setDeptId(cells[4].toString());
                allList.setLiftCode(cells[5].toString());
                allList.setLiftAddress(cells[6].toString());
                allList.setPlaceType(cells[7].toString());
                allList.setUpdateTime(cells[8].toString());
                allList.setMaintName(cells[9].toString());
                allList.setMaintAddress(cells[10].toString());
                allList.setCompanyName(cells[11].toString());
                allList.setCompanyAddress(cells[12].toString());
                allList.setLiftAreaName(cells[13].toString());
                result.add(allList);
            }
        }

        int total = totalList.size();

        Pageable pageable = new PageRequest(page-1,6);
        Page<StatisticAnalysis>  liftListPage = new PageImpl<StatisticAnalysis>(result,pageable,total);
        return  liftListPage;
    }



    /**
     * 查询电梯最近报警信息
     */

    public List<StatisticAnalysis> getLiftFaultAlarmList(){

        StringBuffer hql = new StringBuffer();
        hql.append("" +
                "SELECT t.*,ifnull(bl.liftaddress,\"\") AS liftAddress,ifnull(bl.liftcode,\"\") AS liftCode FROM\n" +
                "(\n" +
                "SELECT ifnull(wf.createtime,\"\") AS createTime,ifnull(wf.regcode,\"\") AS regcode,\n" +
                "ifnull(wf.deptid,\"\") AS deptId,ifnull(wf.fault_type,\"\") AS falutType,\n" +
                "we.content,we.memo,ifnull(wf.starttime,\"\") as startTime from wlw_faultrecord wf \n" +
                "LEFT JOIN wlw_faultcode we ON wf.fault_type = we.row_id \n" +
                "ORDER BY wf.starttime DESC\n" +
                ")t LEFT JOIN base_lift bl ON \n" +
                "t.regcode = bl.regcode ORDER BY t.startTime DESC LIMIT 0,150");

        Query query = em.createNativeQuery(hql.toString());
        List resultlist = query.getResultList();

        List<StatisticAnalysis> result = new ArrayList<>();

        if(!CollectionUtils.isEmpty(resultlist)){
            for(int i=0;i<resultlist.size();i++){
                StatisticAnalysis la = new StatisticAnalysis();
                Object[] cells = (Object[]) resultlist.get(i);
                la.setCreateTime(cells[0].toString());
                la.setRegcode(cells[1].toString());
                la.setDeptId(cells[2].toString());
                la.setFaultType(cells[3].toString());
                la.setContent(cells[4].toString());
                la.setMemo(cells[5].toString());
                String startTime = cells[6].toString();
                if (startTime != null && startTime.length() >= 8) {
                    startTime = startTime.substring(0,4)+"-"+startTime.substring(4,6)+"-"+startTime.substring(6,8);
                }
                la.setStartTime(startTime);
                la.setLiftAddress(cells[7].toString());
                la.setLiftCode(cells[8].toString());
                result.add(la);
            }
        }
        return  result;
    }








}
