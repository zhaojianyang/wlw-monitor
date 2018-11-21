package com.jshx.domain;


import com.jshx.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Zhao on 2017/6/13.
 */
@Repository
public interface UsersRepository extends JpaRepository<Users, String>,JpaSpecificationExecutor<Users> {



    List<Users> findAll();


    List<Users> findUserByLoginId(String loginId);



}
