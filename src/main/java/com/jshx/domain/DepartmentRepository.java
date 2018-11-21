package com.jshx.domain;

import com.jshx.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by YAO on 2017/6/13.
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department,String>{

    Department findById(String deptId);

    List<Department> findAll();
}
