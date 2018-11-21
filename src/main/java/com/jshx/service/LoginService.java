package com.jshx.service;


import com.alibaba.fastjson.JSONObject;
import com.jshx.domain.UsersRepository;
import com.jshx.entity.UserInfo;
import com.jshx.entity.Users;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 *  Created by Zhao on 2017/7/17.
 */
@Service
public class LoginService {


    @Autowired
    UsersRepository usersRepository;

    @PersistenceContext
    private EntityManager em;


    /**
     * 登录校验
     * @param loginId
     * @param password
     */
    public JSONObject checkLogin(String loginId,String password,String remFlag,
                             HttpServletResponse response,HttpServletRequest request) throws IOException {

        //记住用户名、密码功能(注意：cookie存放密码会存在安全隐患)
        rememberUserInfo(loginId,password,remFlag,response);

        String newPassword = DigestUtils.md5Hex(password);
        List<Users> userList = usersRepository.findUserByLoginId(loginId);

        HttpSession session = request.getSession();
        JSONObject json = new JSONObject();
        if (userList.size() != 0) {//判断是否有这个用户
            Users user = userList.get(0);
            if (user.getPassword().equals(newPassword)) {
                session.setAttribute("njLiftMonitorUser", loginId);
                /*session.setMaxInactiveInterval(20*60);//设置单位为秒，设置为-1永不过期.该处为20分钟*/
                session.setMaxInactiveInterval(-1);//设置单位为秒，设置为-1永不过期.该处为20分钟
                String deptId = user.getDeptId();
                if(deptId == null){
                    deptId = "";
                }
                Cookie loginIdCookie = new Cookie("njLiftMonitorUserDeptId",deptId);
                loginIdCookie.setMaxAge(30 * 24 * 60 * 60);   //存活期为一个月 30*24*60*60
                loginIdCookie.setPath("/");
                response.addCookie(loginIdCookie);

                UserInfo userInfo = checkUserType(deptId,loginId);
                String parentDeptId = userInfo.getParentDeptId();
                String deptCode = userInfo.getDeptCode();
                session.setAttribute("njLiftMonitorParentDeptId", parentDeptId);
                session.setAttribute("njLiftMonitorDeptCode", deptCode);
                json.put("msg","success");
                json.put("deptId",deptId);
                json.put("parentDeptId",parentDeptId);
                json.put("loginId",loginId);
                json.put("deptCode",deptCode);

                return json;
            } else {
                session = request.getSession(false);//防止创建Session
                session.invalidate();
                json.put("msg","fault");
                return json;
            }
        } else {
            session = request.getSession(false);//防止创建Session
            session.invalidate();
            json.put("msg","fault");
            return json;
        }

    }

    public void rememberUserInfo(String loginId,String password,String remFlag,HttpServletResponse response){

        if ("1".equals(remFlag)) { //"1"表示用户勾选记住密码

            Cookie loginIdCookie = new Cookie("njLiftMonitorLoginId",loginId);
            Cookie passwordCookie = new Cookie("njLiftMonitorPassword",password);

            loginIdCookie.setMaxAge(30 * 24 * 60 * 60);   //存活期为一个月 30*24*60*60
            passwordCookie.setMaxAge(30 * 24 * 60 * 60);   //存活期为一个月 30*24*60*60

            loginIdCookie.setPath("/");
            passwordCookie.setPath("/");

            response.addCookie(loginIdCookie);
            response.addCookie(passwordCookie);

        }else{

            Cookie loginIdCookie = new Cookie("njLiftMonitorLoginId",loginId);
            Cookie passwordCookie = new Cookie("njLiftMonitorPassword",null);

            loginIdCookie.setMaxAge(30 * 24 * 60 * 60);   //方便登录后获取用户名
            passwordCookie.setMaxAge(0);   //立即删除型

            loginIdCookie.setPath("/");//项目所有目录均有效，这句很关键，否则不敢保证删除
            passwordCookie.setPath("/");//项目所有目录均有效，这句很关键，否则不敢保证删除

            response.addCookie(loginIdCookie);
            response.addCookie(passwordCookie);

        }

    }

    /**
     * 退出登录
     */
    public void checkLoginOut(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute("njLiftMonitorUser");
        session.removeAttribute("njLiftMonitorParentDeptId");
        session.removeAttribute("njLiftMonitorDeptCode");
        session.invalidate();
    }


    public UserInfo checkUserType(String deptId,String loginId){


        StringBuffer hql = new StringBuffer();
        hql.append("SELECT new com.jshx.entity.UserInfo(u.deptId,d.deptCode,d.deptName ,u.displayName,d.parentDeptId) FROM Users u ,Department d where u.deptId = d.id and u.deptId = '"+ deptId +"' and u.loginId = '"+ loginId +"'" );


        Query query = em.createQuery(hql.toString());

        List<UserInfo> userInfoList = query.getResultList();

        //UserInfo userInfo = (UserInfo)query.getSingleResult();//没用这个参数的原因是因为创建用户的时候用户可以相同,
        // 部门可以相同,没有对用户名做限制,所以可能导致用户名相同

        JSONObject result = new JSONObject();
        result.put("userInfoList",userInfoList);

        UserInfo userInfo = new UserInfo();
        if(userInfoList.size()>0){
            userInfo = userInfoList.get(0);
        }

        return userInfo;
    }


}
