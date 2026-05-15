/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.component.ui;

import com.component.model.ReservationTableModel;
import java.util.Collection;

public class ReservationTable extends CustomTable {

    public ReservationTable() {
        super();
    }

    @Override
    public void updateData(Collection<? extends Object> objs) {
        ((ReservationTableModel) this.getModel()).updateData(objs);
    }
}
