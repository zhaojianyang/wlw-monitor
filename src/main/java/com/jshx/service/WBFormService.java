package com.jshx.service;

import com.alibaba.fastjson.JSONObject;
import com.jshx.domain.LiftRepository;
import com.jshx.domain.WBFormRepository;
import com.jshx.domain.WBRecordRepository;
import com.jshx.entity.Lift;
import com.jshx.entity.WBForm;
import com.jshx.entity.WBRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import javax.persistence.Query;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by ZHAO on 2017/11/30.
 */
@Service
public class WBFormService {

    @Autowired
    WBFormRepository wbFormRepository;

    @Autowired
    LiftRepository liftRepository;

    @PersistenceContext
    private EntityManager em;


    public JSONObject findAllByRegcode(String regcode, int page, int pageSize){
        Pageable pageable = new PageRequest(page,pageSize);
        Lift lift = liftRepository.findByRegcode(regcode);
        Page<WBForm> wbFormPage = wbFormRepository.findByLiftId(lift.getId(),pageable);

        StringBuffer hql = new StringBuffer();
        hql.append("SELECT new com.jshx.entity.WBForm(l.maintStartTime,l.maintEndTime ,l.maintainId,l.clientId,l.maintType) FROM WBForm l " +
                " where l.liftId = '" + lift.getId() + "' and l.formsTATUS = '1' ");

        Query query = em.createQuery(hql.toString());
        List<WBForm> totalList =query.getResultList();

        query.setFirstResult(page);
        query.setMaxResults(pageSize);
        List<WBForm> result =query.getResultList();
        JSONObject json = new JSONObject();
        json.put("total",totalList.size());
        json.put("page",page);
        json.put("pages",Math.ceil(totalList.size()/pageSize));
        json.put("wbFormPage",result);
        return  json;
    }
}
