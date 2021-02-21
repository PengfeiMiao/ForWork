package com.mpf.study.test;

import java.util.Date;

/**
 * @author mpf
 * @version 1.0
 * @date 2020/5/15 11:31
 */
public class DateInterval extends Pair<Date> {
    public void setVal(Date date) {
        super.setVal(date);
    }
}

class Pair<T> {
    Object val = null;

    public void setVal(Object obj) {
        this.val = obj;
    }
}
