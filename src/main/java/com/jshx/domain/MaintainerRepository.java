package com.jshx.domain;

import com.jshx.entity.Maintainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by YAO on 2017/6/12.
 */
@Repository
public interface MaintainerRepository extends JpaRepository<Maintainer, String> {

    Maintainer findById(String id);

    @Query()
    List<Maintainer> findByMaintainIdAndDelflag(String maintainId,String delflag);

    List<Maintainer> findAllByDelflag(String delflag);

}
