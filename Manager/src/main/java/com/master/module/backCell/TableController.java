package com.master.module.backCell;

import com.alibaba.fastjson.JSON;
import com.gmdesign.bean.GroupBean;
import com.gmdesign.bean.MenuBean;
import com.gmdesign.bean.other.UserForService;
import com.gmdesign.exception.GmException;
import com.master.analyze.analyeBean.AbstractTable;
import com.master.analyze.analyeBean.AnalyeException;
import com.master.analyze.analyeBean.ResultBean;
import com.master.analyze.analyeBean.TableEnum;
import com.master.analyze.manager.TableManager;
import com.master.analyze.table.ComprehensiveTable;
import com.master.analyze.table.MyTable;
import com.master.bean.dispoly.Channel;
import com.master.bean.dispoly.GameServer;
import com.master.cache.InitTableDataCache;
import com.master.gm.service.login.impl.ManagerLoginService;
import com.master.module.GmModule;
import com.master.util.TimeUtil;
import com.sun.org.glassfish.gmbal.GmbalException;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.lang.util.PageInfo;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.upload.UploadAdaptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Map;

/**
 * Created by DJL on 2017/3/3.
 *
 * @ClassName GM
 * @Description
 */
@IocBean
@Ok("json:full")
@At(GmModule.TABLE)
@Fail("http:500")
@Filters
public class TableController {

    @Inject
    private ManagerLoginService managerLoginService;

    private static Logger log = LoggerFactory.getLogger(TableController.class);
    @Inject
    private TableManager tableManager;

    @At
    @Ok("json:{ignoreNull:true}")
    public ResultBean getTableState() {
        try {
            return new ResultBean(tableManager.getTableStates());
        } catch (AnalyeException e1) {
            return new ResultBean(e1);
        } catch (Exception e) {
            return ResultBean.getDefaultWrongBean();
        }
    }

    @At
    @Ok("json:{ignoreNull:true}")
    public Object initTable(@Param("tableName") String tableName) {
        try {
            TableManager tableManager = Mvcs.getIoc().get(TableManager.class);
            AbstractTable tableByTableStr = tableManager.getTableByTableStr(tableName);
            tableByTableStr.clearTable();
            StringBuilder stringBuilder = tableByTableStr.startupLoad(tableManager.getService());
            return new ResultBean(stringBuilder.toString());
        } catch (AnalyeException e1) {
            return new ResultBean(e1);
        } catch (Exception e) {
            return ResultBean.getDefaultWrongBean();
        }
    }

    @At
    @Ok("json:{ignoreNull:true}")
    public Object queryGraphicData(@Param("tableName") String tableName,@Param("time") String time, HttpServletResponse res) {
        try {
            if(null == time)
                return new ResultBean("时间参数错误");
            Map map = tableManager.getTableByTableStr(tableName).toGraphicJsonStr(time);
            HttpServletResponse response = (HttpServletResponse) res;
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
            return new ResultBean(map);
        } catch (AnalyeException e1) {
            return new ResultBean(e1);
        } catch (Exception e) {
            return ResultBean.getDefaultWrongBean();
        }
    }

    @At
    @Ok("json:{ignoreNull:true}")
    @AdaptBy(type = UploadAdaptor.class, args = {"${app.root}/WEB-INF/filesUpload"})
    public String uploadFile(@Param("id") int id, @Param("file") File f, HttpServletResponse res) {
        try {
            HttpServletResponse response = (HttpServletResponse) res;
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
            return ResultBean.getEmptyBean().toJsonStr();
        } catch (AnalyeException e1) {
            return new ResultBean(e1).toJsonStr();
        } catch (Exception e) {
            return ResultBean.getDefaultWrongBean().toJsonStr();
        }
    }





    @At
    @Ok("json:{ignoreNull:true}")
    public Object queryNowData(@Param("tableName")String tableName,@Param("pageSize")int pageSize, @Param("pageIndex")int pageIndex,HttpServletResponse res){
        try{
            log.info("params  tableName={}, paseSize={},pageIndex={}",tableName, pageSize, pageIndex);
            HttpServletResponse response = (HttpServletResponse) res;
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
            AbstractTable tableByTableStr = tableManager.getTableByTableStr(tableName);
            if(null == tableByTableStr)
                throw new AnalyeException("没用找到表: " + tableName);
            ResultBean bean = tableByTableStr.getJsonStrByPageSizeAndPageIndex(pageSize, pageIndex);
            log.info("resp :{}", Json.toJson(bean).replaceAll("\\s*|\t|\r|\n","").trim());
            return bean;

        }catch (AnalyeException e1) {
            e1.printStackTrace();
            return new ResultBean(e1);
        }catch (Exception e){
            e.printStackTrace();
            return ResultBean.getDefaultWrongBean();
        }
    }

}
