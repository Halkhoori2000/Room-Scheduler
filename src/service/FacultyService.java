/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service;

import com.constant.Constants;
import com.dao.FacultyDAO;
import com.model.Faculty;
import com.utils.MessageUtil;

public class FacultyService {

    public static int add(String name) {
        Faculty faculty = FacultyDAO.findByName(name);
        if (faculty != null) {
            MessageUtil.showError(String.format(Constants.MESSAGE.NAME_EXISTS, name));
            return -1;
        }
        int newId = FacultyDAO.add(name);
        if (newId > -1) {
            MessageUtil.showInfo(String.format(Constants.MESSAGE.ADDED_FACULTY, name));
        } else {
            MessageUtil.showError(Constants.MESSAGE.INSERT_FAIL);
        }
        return newId;
    }
}
