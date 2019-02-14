package com.zj.o2o.util;

/**
 * @author zj
 * @create 2019-02-12 17:45
 */
public class PageCalculator {
    public static int calculateRowIndex(int pageIndex, int pageSize) {
        return (pageIndex > 0) ? (pageIndex - 1) * pageSize : 0;
    }
}
