package com.jshx.service;

import com.jshx.entity.StatisticAnalysis;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZHAO on 2017/8/17.
 */
@Service
public class BaseAreaService {

    @PersistenceContext
    private EntityManager em;


    /**
     * 查询电梯区域列表
     * @return
     */
    public List<StatisticAnalysis> findBaseAreaList(){
        StringBuffer hql = new StringBuffer();
        hql.append("" +
                "SELECT f.areaId,ifnull(ba.area_name,' ') AS liftAreaName,MAX(f.updateTime) AS updateTime,count(*) AS joinLift FROM\n" +
                "(SELECT ifnull(b.areaid,' ') AS areaId,ifnull(w.updatetime,\"\") AS updateTime \n" +
                "FROM wlw_runstatus w LEFT JOIN base_lift b\n" +
                "ON w.regcode = b.regcode)f LEFT JOIN base_area ba \n" +
                "ON f.areaId = ba.row_id  \n" +
                "GROUP BY f.areaId\n" +
                "ORDER BY f.areaId ASC");

        Query query = em.createNativeQuery(hql.toString());
        List resultlist = query.getResultList();

        List<StatisticAnalysis> result = new ArrayList<>();

        if(!CollectionUtils.isEmpty(resultlist)){
            for(int i=0;i<resultlist.size();i++){
                StatisticAnalysis aList = new StatisticAnalysis();
                Object[] cells = (Object[]) resultlist.get(i);
                aList.setLiftAreaName(cells[1].toString());
                aList.setAreaId(cells[0].toString());
                aList.setUpdateTime(cells[2].toString());
                aList.setJoinLift(cells[3].toString());
                result.add(aList);
            }
        }

        return  result;

    }

}
