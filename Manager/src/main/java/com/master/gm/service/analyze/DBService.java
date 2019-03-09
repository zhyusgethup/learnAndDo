package com.master.gm.service.analyze;

import com.master.analyze.analyeBean.Row;
import com.master.analyze.table.MyTable;
import com.master.analyze.table.StayTable;

public interface DBService {
    public void save(String tableName, Row[] rows, StringBuilder msg);

    public int query(MyTable table, String start, String end, StringBuilder msg);

    public boolean hasRecordInMysqlNow(String tableName,String timeStr);

    public void saveStay(StayTable table);

    public void getStay(StayTable table);
}
