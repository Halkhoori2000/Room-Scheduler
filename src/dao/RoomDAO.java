/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.constant.Constants;
import com.model.Room;
import com.utils.Data;
import com.utils.DatabaseUtils;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {

    public static List<Room> getAll() {
        List<Room> lst = new ArrayList<>();
        String sql = "SELECT * FROM ROOM WHERE STATUS = '" + Constants.ROOM_STATUS.ACTIVE + "'";
        List<Data> result = DatabaseUtils.executeQuery(sql);
        result.stream().forEach(data -> {
            Room room = new Room(data.getInt("ID"), data.getString("NAME"), data.getInt("Seats"));
            lst.add(room);
        });
        return lst;
    }

    public static int add(String name, int seats) {
        String sql = "INSERT INTO ROOM(NAME, SEATS) VALUES(?,?)";
        List<Object> values = new ArrayList<>();
        values.add(name);
        values.add(seats);
        return DatabaseUtils.executeInsert(sql, values);
    }

    public static boolean delete(int id) {
        String sql = "DELETE FROM ROOM WHERE ID = ?";
        List<Object> values = new ArrayList<>();
        values.add(id);
        List<Object> result = DatabaseUtils.executeUpdate(sql, values);
        return DatabaseUtils.checkUpdateSuccess(result);
    }

    public static Room findByName(String name) {
        String sql = "SELECT * FROM ROOM WHERE NAME = ?";
        List<Object> values = new ArrayList<>();
        values.add(name);
        List<Data> result = DatabaseUtils.executeQuery(sql, values);
        if (!result.isEmpty()) {
            Data data = result.get(0);
            return new Room(data.getInt("ID"), data.getString("NAME"), data.getInt("SEATS"));
        }
        return null;
    }
}
