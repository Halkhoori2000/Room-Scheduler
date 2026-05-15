/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.component.model;

import com.dto.ReservationDTO;
import com.utils.CommonUtil;
import java.util.Collection;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class ReservationTableModel extends DefaultTableModel {

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    public ReservationTableModel() {
        super(
                new Object[][]{},
                getColumnsName()
        );
    }

    public void updateData(Collection<? extends Object> objs) {
        List<ReservationDTO> reservations = (List) objs;
        this.clear();
        reservations.stream().forEach(dto -> this.addRow(dto));
    }

    private void addRow(ReservationDTO reservation) {
        Object[] row = new Object[7];
        row[0] = reservation.getId();
        row[1] = reservation.getFaculty().getName();
        row[2] = CommonUtil.getDateFormat(reservation.getDate().getDate());
        row[3] = CommonUtil.getTimeStampFormat(reservation.getDateRequested());
        row[4] = reservation.getSeats();
        row[5] = (reservation.getRoom() != null && reservation.getRoom().getId() != -1) ? reservation.getRoom().getName() : "";
        row[6] = reservation.getStatus();
        this.addRow(row);
    }

    private void clear() {
        int size = this.getRowCount();
        for (int i = size - 1; i >= 0; i--) {
            this.removeRow(i);
        }
    }

    private static String[] getColumnsName() {
        String[] cols = new String[7];
        cols[0] = "ID";
        cols[1] = "Faculty";
        cols[2] = "Reserved Date";
        cols[3] = "Resquested Date";
        cols[4] = "Seats";
        cols[5] = "Room";
        cols[6] = "Status";
        return cols;
    }
}
