package com.jshx.web;

import com.jshx.domain.TestRepository;
import com.jshx.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by YAO on 2017/5/25.
 */
@Controller
public class TestController {

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private DepartmentService departmentService;

    @RequestMapping("/test")
    public ModelAndView test() {

        ModelAndView test = new ModelAndView("test");
        System.out.println("aaaaaaaaaaaa");

        test.addObject("testList", testRepository.findByName("abc"));

        return test;
    }

//    @RequestMapping("/dept")
//    public  ModelAndView dept() {
//
//        ModelAndView dept = new ModelAndView("dept");
//        System.out.println("aaaaaaaaaaaa");
//
//        dept.addObject("deptList", departmentService.findAllByDelflag());
//
//        return dept;
//    }


}
