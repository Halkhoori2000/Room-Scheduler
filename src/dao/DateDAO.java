/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.model.Date;
import com.utils.Data;
import com.utils.DatabaseUtils;
import java.util.ArrayList;
import java.util.List;

public class DateDAO {
    
    public static List<Date> getAll() {
        List<Date> lst = new ArrayList<>();
        String sql = "select * from Date where status = 'active' ORDER BY DATE DESC";
        List<Data> results = DatabaseUtils.executeQuery(sql);
        results.stream().forEach(data -> lst.add(new Date(data.getInt("id"), data.getDate("date"))));
        return lst;
    }
    
    public static int add(java.sql.Date date) {
        String sql = "INSERT INTO DATE(DATE) VALUES(?)";
        List<Object> values = new ArrayList<>();
        values.add(date);
        return DatabaseUtils.executeInsert(sql, values);
    }
    
    public static Date findByDate(java.sql.Date date) {
        String sql = "SELECT * FROM DATE WHERE DATE = ?";
        List<Object> values = new ArrayList<>();
        values.add(date);
        List<Data> result = DatabaseUtils.executeQuery(sql, values);
        if (!result.isEmpty()) {
            Data data = result.get(0);
            return new Date(data.getInt("ID"), data.getDate("DATE"));
        }
        return null;
    }
}
