package com.jshx.service;

import com.alibaba.fastjson.JSONObject;
import com.jshx.domain.DepartmentRepository;
import com.jshx.domain.FaultrecordRepository;
import com.jshx.entity.Department;
import com.jshx.entity.LiftFault;
import com.jshx.entity.LiftFaultAndLift;
import com.jshx.entity.LiftRunTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by YAO on 2017/6/12.
 */
@Service
public class FaultrecordService {

    @Autowired
    FaultrecordRepository faultrecordRepository;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private DepartmentRepository departmentRepository;


    public void saveFaultlist(List<LiftFault> liftFaults){
        faultrecordRepository.save(liftFaults);
    }

    public void saveFault(LiftFault liftFault){
        faultrecordRepository.save(liftFault);
    }

    public Page<LiftFault> findAllByRegcode(String regcode, int page, int pageSize){
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC,"starttime"));
        Pageable pageable = new PageRequest(page,pageSize,sort);
        Page<LiftFault> liftFaultPage = faultrecordRepository.findAllByRegcode(regcode,pageable);
        return  liftFaultPage;
    }

    public  JSONObject findAllLiftFaultPage(String regcode,String istrap,String maintdeptid, int page, int pageSize,String liftCode){

        StringBuffer hql = new StringBuffer();
        hql.append("SELECT new com.jshx.entity.LiftFaultAndLift(l.createTime,l.updateTime ,l.deptId,l.regcode,l.faultid," +
                "l.starttime,l.dealstatus,l.dealtime,l.dealperson,l.istrap,l.selfrepair,l.dealnote,l.maincontactor," +
                "l.runcontactor,l.circuit,l.carstatus,l.direction,l.iszone,l.floor,l.doorstatus,l.uplimit,l.downlimit," +
                "l.alarm,l.faultCode,l.hours,l.times,f.liftcode) FROM LiftFault l,Lift f WHERE l.regcode = f.regcode and l.delflag = 0  ");

        if (!StringUtils.isEmpty(regcode)) {
            hql.append(" AND l.regcode like '%" + regcode + "%'");
        }
        if (!StringUtils.isEmpty(istrap)) {
            hql.append(" AND l.istrap = '" + istrap + "'");
        }
        if (!StringUtils.isEmpty(maintdeptid)) {
            hql.append(" AND l.deptId = '" + maintdeptid + "'");
        }
        if (!StringUtils.isEmpty(liftCode)) {
            hql.append(" AND f.liftcode like '%" + liftCode + "%'");
        }
        hql.append(" ORDER BY l.starttime DESC ");

        Query query = em.createQuery(hql.toString());
        List<LiftFaultAndLift> resultlistTotal = query.getResultList();
        query.setFirstResult(page*pageSize);
        query.setMaxResults(pageSize);
        List<LiftFaultAndLift> liftfaults = query.getResultList();
        int pages = (int)Math.ceil((double)resultlistTotal.size()/pageSize);

        List<LiftFaultAndLift> result = new ArrayList<>();
        for(LiftFaultAndLift f : liftfaults){
            Department department= departmentRepository.findById(f.getDeptId());
            String deptName = "";
            if(department != null){
                deptName = department.getDeptName();
            }
            f.setDeptId(deptName);
            result.add(f);
        }


        JSONObject obj = new JSONObject();
        obj.put("total",resultlistTotal.size());
        obj.put("pages", pages);
        obj.put("page", page);
        obj.put("liftfaults", result);

        return  obj;
    }

    public LiftFault findById(String id){
        return  faultrecordRepository.findById(id);
    }
    public LiftFault findByFaultid(String id,String regcode){
        return  faultrecordRepository.findByFaultidAndRegcode(id,regcode);
    }


    /**
     * 获取历史故障的时间列表
     * @return
     */
    public  List<LiftRunTime> getLiftFaultRecordTimeList() {
        StringBuffer hql = new StringBuffer();
        hql.append("SELECT left(w.starttime,8) AS time FROM wlw_faultrecord w GROUP BY time ORDER BY w.starttime DESC;");

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




}
