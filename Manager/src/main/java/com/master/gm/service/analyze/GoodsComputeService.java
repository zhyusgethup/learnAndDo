package com.master.gm.service.analyze;

import com.master.analyze.analyeBean.CGoodsBean;

import java.util.List;
import java.util.Map;

public interface GoodsComputeService {
    public void replaceAndSave(long start, long end) ;
    public void generHeadAndData(Map<String,String> data);
    public void combineAndUpdate(List<CGoodsBean> beans, long start, long end);
    public List<CGoodsBean> getBeans(long start, long end);
}
