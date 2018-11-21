package com.jshx.service;

import com.jshx.domain.MaintainRepository;
import com.jshx.entity.Maintain;
import com.jshx.entity.StatisticAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
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
public class BaseCompanyService {

    @PersistenceContext
    private EntityManager em;

    public List<StatisticAnalysis> findUseCompanyList(){
        StringBuffer hql = new StringBuffer();
        hql.append("" +
                "SELECT t.companyId,MAX(t.updateTime) AS updateTime, ifnull(m.company_name,' ') AS companyName,\n" +
                "ifnull(m.companyaddr,' ') AS companyAddress,count(*) AS joinLift FROM\n" +
                "(SELECT b.regcode AS regcode,ifnull(b.companyid,' ') AS companyId," +
                "b.liftcode AS liftCode,b.liftaddress AS liftAddress,\n" +
                "b.placetype AS placeType,ifnull(w.updatetime,\"\") AS updateTime \n" +
                "FROM wlw_runstatus w LEFT JOIN base_lift b\n" +
                "ON w.regcode = b.regcode)t LEFT JOIN base_company m\n" +
                "ON t.companyId = m.row_id  \n" +
                "GROUP BY m.row_id \n" +
                "ORDER BY t.companyId ASC");

        Query query = em.createNativeQuery(hql.toString());
        List resultlist = query.getResultList();

        List<StatisticAnalysis> result = new ArrayList<>();

        if(!CollectionUtils.isEmpty(resultlist)){
            for(int i=0;i<resultlist.size();i++){
                StatisticAnalysis sa = new StatisticAnalysis();
                Object[] cells = (Object[]) resultlist.get(i);
                sa.setCompanyId(cells[0].toString());
                sa.setCompanyName(cells[2].toString());
                sa.setCompanyAddress(cells[3].toString());
                result.add(sa);
            }
        }

        return  result;

    }

}
