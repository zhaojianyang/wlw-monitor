package com.jshx.service;


import com.jshx.domain.LiftRepository;
import com.jshx.entity.LiftDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by YAO on 2017/6/13.
 */
@Service
public class LiftService {

    @Autowired
    LiftRepository liftRepository;

    @PersistenceContext
    private  EntityManager em;


    public  List<LiftDto> findAllMintorLiftDto(String regcode, String liftcode, String liftaddress,String maintdeptid) {
        StringBuffer hql = new StringBuffer();
        hql.append("SELECT new com.jshx.entity.LiftDto(l.regcode,l.liftcode ,l.liftaddress,l.latitude,l.longitude,m.maintainName) FROM Lift l ,Maintain m  where l.maintainid = m.id " );
        if(!StringUtils.isEmpty(regcode)){
            hql.append("and l.regcode like '%"+regcode+"%'");
        }
        if(!StringUtils.isEmpty(liftcode)){
            hql.append("and l.liftcode like '%"+liftcode+"%'");
        }
        if(!StringUtils.isEmpty(liftaddress)){
            hql.append("and l.liftaddress like '%"+liftaddress+"%'");
        }
        if(!StringUtils.isEmpty(maintdeptid)){
            hql.append("and l.regcode in (SELECT distinct(r.regcode) FROM LiftRun r where r.deptId like '%"+maintdeptid+"%') ");
        }else{
            hql.append("and l.regcode in (SELECT distinct(r.regcode) FROM LiftRun r) ");
        }
        Query query = em.createQuery(hql.toString());
        List<LiftDto> list =query.getResultList();
        return list;
    }

    public  Page<LiftDto> findAllMintorLiftDtoByPage(String regcode, String liftcode, String liftaddress,int page, int pageSize) {
        StringBuffer hql = new StringBuffer();
        hql.append("SELECT new com.jshx.entity.LiftDto(l.regcode,l.liftcode ,l.liftaddress,l.latitude,l.longitude,m.maintainName) FROM Lift l ,Maintain m where l.maintainid = m.id " +
                "and l.regcode in (SELECT distinct(r.regcode) FROM LiftRun r) ");
        if(!StringUtils.isEmpty(regcode)){
            hql.append("and l.regcode like '%"+regcode+"%'");
        }
        if(!StringUtils.isEmpty(liftcode)){
            hql.append("and l.liftcode like '%"+liftcode+"%'");
        }
        if(!StringUtils.isEmpty(liftaddress)){
            hql.append("and l.liftaddress like '%"+liftaddress+"%'");
        }
        Query query = em.createQuery(hql.toString());
        int total = query.getResultList().size();
        List<LiftDto> list =query.getResultList();
        int start = (page - 1) * pageSize;
        query.setFirstResult(start);
        query.setMaxResults(pageSize);
        Pageable pageable = new PageRequest(page,pageSize);
        Page<LiftDto>  liftDtoPage = new PageImpl<LiftDto>(query.getResultList(),pageable,total);
        return liftDtoPage;
    }

    public LiftDto getDtjbxx (String regcode){
        StringBuffer hql = new StringBuffer();
        hql.append("SELECT t.regcode ,t.liftcode,t.liftaddress, t.MAINTAIN_NAME,IFNULL(t.LASTINSPECT,''),DATE_FORMAT(IFNULL(r.updatetime,r.createtime),'%Y-%m-%d %H:%i:%s') FROM wlw_runstatus r  " +
                "LEFT JOIN (SELECT l.regcode,l.liftcode ,l.liftaddress ,l.LASTINSPECT,m.MAINTAIN_NAME FROM base_lift l  " +
                "LEFT JOIN base_maintain m on l.MAINTAINID = m.ROW_ID )t " +
                "ON r.REGCODE = t.REGCODE");
        if(!StringUtils.isEmpty(regcode)){
            hql.append(" WHERE r.regcode = '"+regcode+"'");
        }
        Query query = em.createNativeQuery(hql.toString());
        List list =query.getResultList();
        LiftDto liftDto = new LiftDto();
        if(!CollectionUtils.isEmpty(list)){
            Object[] cells = (Object[]) list.get(0);
            liftDto.setRegcode(cells[0].toString());
            liftDto.setLiftcode(cells[1].toString());
            liftDto.setLiftaddress(cells[2].toString());
            liftDto.setMaintainName(cells[3].toString());
            liftDto.setLastInspecttime(cells[4].toString());
            liftDto.setLastUptime(cells[5].toString());
        }
        return  liftDto;
    }



    public  Page<LiftDto> fuzzyQueryAllMintorLift(String searchValue,int page) {
        StringBuffer hql = new StringBuffer();
        hql.append("SELECT new com.jshx.entity.LiftDto(l.regcode,l.liftcode ,l.liftaddress,l.latitude,l.longitude,m.maintainName) FROM Lift l ,Maintain m ,LiftRun lr  where l.maintainid = m.id and l.regcode = lr.regcode" );

        if(!StringUtils.isEmpty(searchValue)){
            hql.append(" AND (l.regcode like '%"+searchValue+"%'" +
                    " OR l.liftcode like '%"+searchValue+"%'" +
                    " OR l.liftaddress like '%"+searchValue+"%'" +
                    " OR m.maintainName like '%"+searchValue+"%')");
        }

        Query query = em.createQuery(hql.toString());
        List<LiftDto> totalList =query.getResultList();

        int pageSize = 10;
        int start = (page - 1) * pageSize;

        query.setFirstResult(start);
        query.setMaxResults(pageSize);

        List<LiftDto> resultList =query.getResultList();

        int total = totalList.size();

        Pageable pageable = new PageRequest(page-1,pageSize);
        Page<LiftDto>  liftDtoPage = new PageImpl<LiftDto>(resultList,pageable,total);
        return liftDtoPage;

        //return resultList;
    }

}
