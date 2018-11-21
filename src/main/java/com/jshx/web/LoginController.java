package com.jshx.web;



import com.alibaba.fastjson.JSONObject;
import com.jshx.entity.UserInfo;
import com.jshx.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by Zhao on 2017/7/17.
 */
@Controller
public class LoginController {
    @Autowired
    private LoginService loginService;

    @RequestMapping("/login")
    public  ModelAndView login() {
        ModelAndView login = new ModelAndView("login");
        return  login;
    }

/*    @RequestMapping(value="/checkLogin",method= RequestMethod.GET)
    @ResponseBody
    public String checkUserLogin(@RequestParam(name = "loginId") String loginId,
                                    @RequestParam(name = "password") String password,
                                    HttpServletResponse response)
            throws IOException ,JsonProcessingException {
        System.out.println("loginId:"+loginId);
        System.out.println("password:"+password);
        return loginService.checkLogin(loginId,password,response);
    }*/

    @RequestMapping(value="/checkLogin",method= RequestMethod.POST)
    @ResponseBody
    public JSONObject checkUserLogin(HttpServletRequest request, HttpServletResponse response)
            throws IOException{
        String loginId = request.getParameter("loginId");
        String password = request.getParameter("password");
        String remFlag = request.getParameter("remFlag");

        return loginService.checkLogin(loginId,password,remFlag,response,request);
    }

    @RequestMapping(value="/loginOut")
    @ResponseBody
    public void checkLoginOut(HttpServletRequest request)
            throws IOException{

        loginService.checkLoginOut(request);
    }


    @RequestMapping(value="/checkUserType")
    @ResponseBody
    public UserInfo checkUserType(@RequestParam(name = "deptId") String deptId,@RequestParam(name = "loginId") String loginId, HttpServletResponse response)
            throws IOException{

        UserInfo result = loginService.checkUserType(deptId,loginId);
        return result;
    }

}
