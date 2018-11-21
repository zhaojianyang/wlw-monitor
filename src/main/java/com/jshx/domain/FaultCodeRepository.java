package com.jshx.domain;

import com.jshx.entity.FaultCode;
import com.jshx.entity.LiftFault;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by YAO on 2017/6/12.
 */
@Repository
public interface FaultCodeRepository extends JpaRepository<FaultCode,String>{

        FaultCode findByCode(String code);

}
