/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.model.Faculty;
import com.utils.Data;
import com.utils.DatabaseUtils;
import java.util.ArrayList;
import java.util.List;

public class FacultyDAO {

    public static Faculty getByName(String name) {
        String sql = "Select * from faculty where name = ?";
        List<Object> values = new ArrayList<>();
        values.add(name);
        List<Data> result = DatabaseUtils.executeQuery(sql, values);
        if (result.size() > 0) {
            return new Faculty(result.get(0).getInt("id"), result.get(0).getString("name"), result.get(0).getString("role"), result.get(0).getString("status"));
        }
        return null;
    }

    public static List<Faculty> getAll() {
        List<Faculty> lst = new ArrayList<>();
        String sql = "SELECT * FROM FACULTY";
        List<Data> result = DatabaseUtils.executeQuery(sql);
        result.stream().forEach(data -> {
            Faculty faculty = new Faculty(data.getInt("ID"), data.getString("NAME"));
            lst.add(faculty);
        });
        return lst;
    }

    public static Faculty findByName(String name) {
        String sql = "SELECT * FROM FACULTY WHERE NAME = ?";
        List<Object> values = new ArrayList<>();
        values.add(name);
        List<Data> result = DatabaseUtils.executeQuery(sql, values);
        if (result.size() > 0) {
            Data data = result.get(0);
            return new Faculty(data.getInt("ID"), data.getString("name"));
        }
        return null;
    }

    public static int add(String name) {
        String sql = "INSERT INTO faculty (name) values (?)";
        List<Object> values = new ArrayList<>();
        values.add(name);
        return DatabaseUtils.executeInsert(sql, values);
    }
}
