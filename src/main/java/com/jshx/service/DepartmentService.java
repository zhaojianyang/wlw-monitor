package com.jshx.service;

import com.jshx.domain.DepartmentRepository;
import com.jshx.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by YAO on 2017/6/13.
 */
@Service
public class DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;

    public Department findById(String deptId) {
        return departmentRepository.findById(deptId);
    }

    public List<Department> findAllByDelflag(){
        List<Department> deptList = departmentRepository.findAll();
        return deptList;
    }
}
