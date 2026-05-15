/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.component.ui;

import java.util.Collection;
import javax.swing.JTable;

public class CustomTable extends JTable {

    public CustomTable() {
        super();
        initColsSize();
    }

    protected void initColsSize() {
//        TableColumnModel model = this.getColumnModel();
//        model.getColumn(0).setCellRenderer();
//        model.getColumn(0).setPreferredWidth(20);
//        model.getColumn(0).setWidth(20);
    }

    public void updateData(Collection<? extends Object> objs) {

    }
}
