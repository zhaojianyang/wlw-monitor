package com.jshx.domain;

import com.jshx.entity.WBForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ZHAO on 2017/11/30.
 */
@Repository
public interface WBFormRepository extends JpaRepository<WBForm,String>{


    Page<WBForm> findByLiftId(String id, Pageable pageable);

}
