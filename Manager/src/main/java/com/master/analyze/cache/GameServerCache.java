package com.master.analyze.cache;

import com.master.analyze.http.TableNeed;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
public class GameServerCache {
    @Getter
    private static List<Integer> instanceCids = new ArrayList<>();
    @Getter
    private static List<Integer> activityCids = new ArrayList<>();

    public static void init() {
        instanceCids = TableNeed.getInstanceCid("inst");
        activityCids = TableNeed.getInstanceCid("act");
    }
}
