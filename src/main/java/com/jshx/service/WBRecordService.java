package com.jshx.service;

import com.jshx.domain.WBRecordRepository;
import com.jshx.entity.LiftFault;
import com.jshx.entity.WBRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by YAO on 2017/6/13.
 */
@Service
public class WBRecordService {

    @Autowired
    WBRecordRepository wbRecordRepository;

    public void saveWBRecordList(List<WBRecord> wbRecordList){
        wbRecordRepository.save(wbRecordList);
    }

    public Page<WBRecord> findAllByRegcode(String regcode, int page, int pageSize){
        Pageable pageable = new PageRequest(page,pageSize);
        Page<WBRecord> wbRecordPage = wbRecordRepository.findAllByRegcode(regcode,pageable);
        return  wbRecordPage;
    }
}
