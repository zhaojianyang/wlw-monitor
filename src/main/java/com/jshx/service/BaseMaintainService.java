package com.jshx.service;

import com.jshx.domain.MaintainRepository;
import com.jshx.entity.Maintain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YAO on 2017/6/13.
 */
@Service
public class BaseMaintainService {

    @Autowired
    MaintainRepository maintainRepository;

    public List<Maintain> findMaintainDeptidList(){

        List<Maintain> maintainList = maintainRepository.findAllDeptMaintain();


        return maintainList;

    }

}
