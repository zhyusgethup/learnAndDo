package com.master.analyze.analyeBean;

import java.util.HashMap;
import java.util.Map;

public class PageObject {

    private Map<String, Integer> page;
    private Object table;
    public static final String PAGE_SIZE = "pageSize";
    public static final String PAGE_INDEX = "pageIndex";
    public static final String TOTAL_SIZE = "totalSize";

    /***
     *     private int pageSize;//每页大小
     *     private int pageIndex;//页数 从1开始计算
     *     private int totalSize;//除了标题外的总行数;
     * @param pageSize
     * @param pageIndex
     * @param totalSize
     */
    public PageObject(int pageSize, int pageIndex, int totalSize, Object table) {
        page = new HashMap<>();
        page.put(PAGE_SIZE, pageSize);
        page.put(PAGE_INDEX, pageIndex);
        page.put(TOTAL_SIZE, totalSize);
        this.table = table;
    }
}
