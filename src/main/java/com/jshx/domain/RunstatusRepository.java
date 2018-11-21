package com.jshx.domain;

import com.jshx.entity.LiftRun;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by YAO on 2017/6/12.
 */
@Repository
public interface RunstatusRepository extends JpaRepository<LiftRun,String>,JpaSpecificationExecutor {



    public LiftRun findByRegcode(String regcode);



}
