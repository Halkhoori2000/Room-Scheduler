package com.utils;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedHashMap;

public class Data extends LinkedHashMap<String, Object> {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public String getString(String key) {
        key = key.toUpperCase();
        if (this.containsKey(key)) {
            Object val = this.get(key);
            return val != null ? val.toString() : "";
        } else
            return "";
    }

    public String getString(int index) {
        return this.getString(this.keySet().toArray()[index] + "");
    }

    public Timestamp getTimestamp(String key) {
        key = key.toUpperCase();
        if (this.containsKey(key)) {
            Object val = this.get(key);
            if (val instanceof Timestamp)
                return (Timestamp) val;
        }
        return null;
    }
    
    public Date getDate(String key) {
        key = key.toUpperCase();
        if (this.containsKey(key)) {
            Object val = this.get(key);
            if (val instanceof Date)
                return (Date) val;
        }
        return null;
    }

    public int getInt(String key) {
        key = key.toUpperCase();
        if (this.containsKey(key)) {
            Object val = this.get(key);
            if (val != null) {
                if (val instanceof Long)
                    return ((Long) val).intValue();
                return (int) val;
            }
        }
        return -1;
    }

    public int getInt(int index) {
        return this.getInt(this.keySet().toArray()[index] + "");
    }

    public Object getObject(String key) {
        key = key.toUpperCase();
        return this.getOrDefault(key, null);
    }
}
