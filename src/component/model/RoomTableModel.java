/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.component.model;

import com.model.Room;
import java.util.Collection;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class RoomTableModel extends DefaultTableModel {

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    public RoomTableModel() {
        super(
                new Object[][]{},
                getColumnsName()
        );
    }

    public void updateData(Collection<? extends Object> objs) {
        List<Room> rooms = (List) objs;
        this.clear();
        for (Room dto : rooms) {
            this.addRow(dto);
        }
    }

    private void addRow(Room room) {
        Object[] row = new Object[3];
        row[0] = room.getId();
        row[1] = room.getName();
        row[2] = room.getSeats();
        this.addRow(row);
    }

    private void clear() {
        int size = this.getRowCount();
        for (int i = size - 1; i >= 0; i--) {
            this.removeRow(i);
        }
    }

    private static String[] getColumnsName() {
        String[] cols = new String[3];
        cols[0] = "Id";
        cols[1] = "Name";
        cols[2] = "Seats";
        return cols;
    }
}
