package com.master.module.login;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gmdesign.bean.GroupBean;
import com.gmdesign.bean.MenuBean;
import com.gmdesign.bean.other.UserForService;
import com.master.bean.dispoly.Channel;
import com.master.bean.dispoly.GameServer;
import com.master.cache.InitTableDataCache;
import com.master.gm.service.login.impl.ManagerLoginService;
import com.master.module.GmModule;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.*;

/**
 * Created by DJL on 2017/3/3.
 *
 * @ClassName GM
 * @Description
 */
@IocBean
@Ok("json:full")
@At(GmModule.BASE_URL)
@Fail("http:500")
@Filters
public class MenuDataModule {

    @Inject
    private ManagerLoginService managerLoginService;

    private static Log logger = Logs.get();
    @At
    @Ok("re")
//    public String come( @Param("menuData")String menuData, @Param("..") UserForService user, HttpServletRequest request,
    public String come(@Param("menuData")String menuData, @Param("user") UserForService user, HttpServletRequest request,
                       HttpSession session, @Param("versionName")String versionName, HttpServletResponse response){
//        logger.info(user.toString());
        if(menuData.equals("1")) {
            List<GroupBean> menus = initMenu();
            session.setAttribute("menu", ManagerLoginService.convertMenu(menus, new StringBuilder(1024)));
            return "jsp:jsp/main/logindata";
        }
        try{
//            String origin = request.getHeader("Origin");
//            response.setHeader("Access-Control-Allow-Origin", origin);
//            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//            response.setHeader("Access-Control-Max-Age", "3600");
//            response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization");
//            response.setHeader("Access-Control-Allow-Credentials", "true");

            session.setAttribute("GmBase","../../../../");
            session.setAttribute("menu", this.managerLoginService.judgeUserData(menuData, user));
            session.setAttribute("vName", versionName);
            session.setAttribute("GM",user);
            session.setAttribute("channl",managerLoginService.getDao().query(Channel.class,null));
            session.setAttribute("serverConfig",managerLoginService.getDao().query(GameServer.class,null));

            if(InitTableDataCache.judge(InitTableDataCache.getReport())){
                InitTableDataCache.setReport(managerLoginService.initWindowComprehensive());
            }
            if(InitTableDataCache.judge(InitTableDataCache.getRate())){
                InitTableDataCache.setRate(managerLoginService.initWindowRate());
            }
            if(InitTableDataCache.getComprehensiveData().size() == 0)
                InitTableDataCache.setComprehensiveData(managerLoginService.initComprehensive());
            request.setAttribute("comprehensive", InitTableDataCache.getReport());
            request.setAttribute("rate",InitTableDataCache.getRate());
            request.setAttribute("nowData",InitTableDataCache.getNowData());
            request.setAttribute("nowTime",System.currentTimeMillis());
            request.setAttribute("comprehensiveData",InitTableDataCache.getComprehensiveData());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            request.setAttribute("dataErorr", e.getMessage());
            return "jsp:jsp/error/error";
        }
        return "jsp:jsp/main/logindata";
    }

    private List<GroupBean> initMenu() {
        List<GroupBean> groupBeans = new ArrayList<>();
        GroupBean g1 = new GroupBean();
        g1.setGid(1);
        g1.setGroupIcon("img2");
        g1.setGroupName("测试组");
        MenuBean menuBean = new MenuBean();
        List<MenuBean> list = new ArrayList<>();
        list.add(menuBean);
        menuBean.setUrl("");
        g1.setMenu(list);
        groupBeans.add(g1);
        g1.setVid(3123);
        return groupBeans;
    }

    @At
    @Ok("json:full")
    public List<String[]> queryNowData(){
        return InitTableDataCache.getNowData();
    }

}
