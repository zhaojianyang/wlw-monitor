package com.jshx.service;

import com.jshx.domain.FaultCodeRepository;
import com.jshx.domain.FaultrecordRepository;
import com.jshx.entity.FaultCode;
import com.jshx.entity.LiftFault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by YAO on 2017/6/12.
 */
@Service
public class FaultCodeService {

    @Autowired
    FaultCodeRepository faultCodeRepository;

    public FaultCode findByCode(String code){
        return  faultCodeRepository.findByCode(code);
    }

}
