package com.jshx.domain;

import com.jshx.entity.LiftFault;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

/**
 * Created by YAO on 2017/6/12.
 */
@Repository
public interface FaultrecordRepository extends JpaRepository<LiftFault,String>,JpaSpecificationExecutor {

        public Page<LiftFault> findAllByRegcode(String regcode, Pageable pageable);

        public LiftFault findById(String id);

        public LiftFault findByFaultid(String faultid);

        public LiftFault findByFaultidAndRegcode(String faultid,String regcode);


}
