package com.jshx.domain;


import com.jshx.entity.LiftRunHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.Date;


/**
 * Created by ZHAO on 2017/8/12.
 */
@Transactional
@Repository
public interface RunstatusHistoryRepository extends JpaRepository<LiftRunHistory,String>,JpaSpecificationExecutor {


    public Page<LiftRunHistory> findLiftRunHistoriesByRegcode(String regcode, Pageable pageable);

    //根据createtime删除运行记录
    @Modifying
    @Query(value = "delete from wlw_runstatus_history  where createtime < :nowDate ", nativeQuery = true)
    int deleteLiftRunHistoriesByCreateTime(@Param("nowDate") Date nowDate);

}
