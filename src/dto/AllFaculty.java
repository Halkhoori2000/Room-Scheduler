/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dto;

import com.constant.Constants;
import com.model.Faculty;

public class AllFaculty extends Faculty {

    private AllFaculty() {
        this.setId(Constants.FACULTY.ALL_FACULTY_ID);
        this.setName(Constants.FACULTY.ALL_FACULTY_LABEL);
    }

    private static final AllFaculty instance = new AllFaculty();

    public static AllFaculty getInstance() {
        return instance;
    }
}
