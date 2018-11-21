package com.jshx.service;

import com.jshx.domain.LiftRepository;
import com.jshx.domain.MaintainRepository;
import com.jshx.entity.Lift;
import com.jshx.entity.Maintain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zjy on 2017/10/20.
 */
@Service
public class JudgeLiftService {

    @Autowired
    LiftRepository liftRepository;

    @Autowired
    MaintainRepository maintainRepository;

    @PersistenceContext
    private EntityManager em;

    /**
     * 判断该电梯是否在96333库,即是否属于监控的电梯,是就允许入库,否则不允许
     * @param regcode
     * @return
     */
    public Lift judgeBelongToMonitorLifts(String regcode){
        Lift lift = liftRepository.findByRegcode(regcode);
        return  lift;
    }

    /**
     * 判断该电梯是否属于其他维保单位维保
     * @param id
     * @return
     */
    public Maintain judgeBelongToOtherMainter(String id){
        Maintain maintain = maintainRepository.findById(id);
        return  maintain;
    }

}

