package com.jshx.domain;

import com.jshx.entity.WBRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by YAO on 2017/6/13.
 */
@Repository
public interface WBRecordRepository extends JpaRepository<WBRecord,String>{


    Page<WBRecord> findAllByRegcode (String regcode, Pageable pageable);

}
