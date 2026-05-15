/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dto;

import com.constant.Constants;
import com.model.Date;

public class AllDate extends Date {

    private AllDate() {
        this.setId(Constants.DATE.ALL_DATE_ID);
    }

    @Override
    public String toString() {
        return Constants.DATE.ALL_DATE_LABEL;
    }

    private static final AllDate instance = new AllDate();

    public static AllDate getInstance() {
        return instance;
    }
}
