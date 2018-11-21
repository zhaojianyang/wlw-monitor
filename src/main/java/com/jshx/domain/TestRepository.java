package com.jshx.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by YAO on 2017/5/25.
 */
@Repository
public interface TestRepository extends JpaRepository<Test,String>{

    @Query("select t  from Test t where t.name = ?1")
    List<Test> findByName(String name);


    List<Test> findByNameAndAge(String name,Integer age);
}
