/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.component.ui;

import com.component.model.RoomTableModel;
import java.util.Collection;

public class RoomTable extends CustomTable {

    public RoomTable() {
        super();
    }

    @Override
    public void updateData(Collection<? extends Object> objs) {
        ((RoomTableModel) this.getModel()).updateData(objs);
    }
}
