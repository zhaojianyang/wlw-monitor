package com.jshx.domain;

import com.jshx.entity.Maintain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by YAO on 2017/6/12.
 */
@Repository
public interface MaintainRepository extends JpaRepository<Maintain,String>{

    @Query("select m  from Maintain m where m.delflag='0' " +
            "and m.deptId is not null and m.maitMonitor = '1' ")
    List<Maintain> findAllDeptMaintain();

    Maintain findById(String id);
}
