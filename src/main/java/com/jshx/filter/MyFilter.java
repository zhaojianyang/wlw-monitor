package com.jshx.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by zjy on 2017/10/24.
 */

@SuppressWarnings("restriction")
public class MyFilter implements Filter {
    /**
     * 封装，不需要过滤的list列表
     */
    protected static List<Pattern> patterns = new ArrayList<Pattern>();
    /**
     * 封装，不需要过滤的维保单位请求list列表
     */
    protected static List<Pattern> maintPatterns = new ArrayList<Pattern>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Pattern p = Pattern.compile("login|checkLogin|checkUserType|loginOut|fuzzyQueryAllMintorLift" +
                "|getRealtimeList|getFaultrecordList|getLiftListGroupByMaintDepartment|getLiftListGroupByUseCompanyId|" +
                "getLiftListGroupByAreaId|rundetai|getDtjbxx|getDtyxzt|getDtlsyx|" +
                "getDtlsgz|getDtlswb|confirmexportexcel|exportFaultrecordListExcelData|" +
                "run|fault|token|maintain|rundetail");
        patterns.add(p);
        Pattern mp = Pattern.compile("checkUserType|realtimelistbymanit|faultrecordlistbymanit|" +
                ".css|.js|.jpg|.png|.gif|.ico");
        maintPatterns.add(mp);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        String url = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());


        if (url.startsWith("/") && url.length() > 1) {
            url = url.substring(1);
        }

        if (isInclude(url)){
            chain.doFilter(httpRequest, httpResponse);
            return;
        } else {
            HttpSession session = httpRequest.getSession();
            String user = (String)session.getAttribute("njLiftMonitorUser");

            if (user != null){
                // session存在
                String parentDeptId = (String)session.getAttribute("njLiftMonitorParentDeptId");
                String deptCode = (String)session.getAttribute("njLiftMonitorDeptCode");
                if("001".equals(deptCode) ||  "402880e92db5d2ee012db601b2220004".equals(parentDeptId)){
                    //系统管理员和南京质监局的请求不用拦截
                    chain.doFilter(httpRequest, httpResponse);
                }else if("8a8181f65c9f3816015c9f3ac0040003".equals(parentDeptId)){//维保单位拦截
                    if(!maintIsInclude(url)){
                        httpResponse.sendRedirect(httpRequest.getContextPath()+"/login");
                    }
                    chain.doFilter(httpRequest, httpResponse);

                }else{
                    httpResponse.sendRedirect(httpRequest.getContextPath()+"/login");//其他类型用户先暂时都拦截到登录页
                    chain.doFilter(httpRequest, httpResponse);
                }

                return;
            } else {
                // session不存在 准备跳转失败
                httpResponse.sendRedirect(httpRequest.getContextPath()+"/login");
                chain.doFilter(httpRequest, httpResponse);
                return;
            }
        }

    }

    @Override
    public void destroy() {

    }


    /**
     * 是否需要过滤
     * @param url
     * @return
     */
    private boolean isInclude(String url) {
        Pattern sp = Pattern.compile(".css|.js|.jpg|.png|.gif|.htm|.ico");
        for (Pattern pattern : patterns) {
            Matcher matcher = pattern.matcher(url);
            Matcher smatcher = sp.matcher(url);
            if (matcher.matches() || smatcher.find()) {
                return true;
            }
        }
        return false;
    }


    /**
     * 维保单位是否需要过滤
     * @param url
     * @return
     */
    private boolean maintIsInclude(String url) {
        for (Pattern pattern : maintPatterns) {
            Matcher matcher = pattern.matcher(url);
            if (matcher.find()) {
                return true;
            }
        }
        return false;
    }

}
