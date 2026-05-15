/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service;

import com.constant.Constants;
import com.dao.DateDAO;
import com.model.Date;
import com.utils.CommonUtil;
import com.utils.MessageUtil;

public class DateService {

    public static int add(java.sql.Date date) {
        Date dateObject = DateDAO.findByDate(date);
        if (dateObject != null) {
            MessageUtil.showError(String.format(Constants.MESSAGE.DATE_EXISTS, CommonUtil.getDateFormat(date)));
            return -1;
        }
        int newId = DateDAO.add(date);
        if (newId > -1) {
            MessageUtil.showInfo(String.format(Constants.MESSAGE.ADDED_DATE, CommonUtil.getDateFormat(date)));
        } else {
            MessageUtil.showError(Constants.MESSAGE.INSERT_FAIL);
        }
        return newId;
    }
}
