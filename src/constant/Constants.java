/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.constant;

import com.dto.ComboboxItem;
import java.util.ArrayList;
import java.util.List;

public class Constants {

    public static class ROLE {

        public static final String ADMIN = "admin";
        public static final String USER = "user";
    }

    public static class RESERVASION_STATUS {

        public static final String ACTIVE = "active";
        public static final String WAITING = "waiting";
        public static final String ALL_LABEL = "All";

        private static final List<ComboboxItem> lstStatus = new ArrayList<>();

        static {
            lstStatus.add(new ComboboxItem("", ALL_LABEL));
            lstStatus.add(new ComboboxItem(ACTIVE, ACTIVE));
            lstStatus.add(new ComboboxItem(WAITING, WAITING));
        }

        public static List<ComboboxItem> getLstStatus() {
            return lstStatus;
        }
    }

    public static class ROOM_STATUS {

        public static final String ACTIVE = "active";
        public static final String DELETE = "delete";
    }

    public static class MESSAGE {

        public static final String INSERT_SUCESS = "Insert success!!!";
        public static final String INSERT_FAIL = "Insert fail. Please view the log for more detail!";
        public static final String UPDATE_SUCESS = "Update success!!!";
        public static final String UPDATE_FAIL = "Update fail. Please view the log for more detail!";
        public static final String DELETE_SUCESS = "Delete success!!!";
        public static final String DELETE_FAIL = "Delete fail. Please view the log for more detail!";
        public static final String FILL_ALL_FIELDS = "Please fill all input fields!!!";

        public static final String NAME_EXISTS = "The name '%s' already exists. Please choose another name!";
        public static final String DATE_EXISTS = "The date '%s' already exists. Please choose another date!";
        public static final String ADDED_FACULTY = "Successfully inserted faculty '%s'";
        public static final String ADDED_ROOM = "Successfully inserted room '%s'";
        public static final String DELETED_ROOM = "Successfully deleted room '%s'";
        public static final String ADDED_DATE = "Successfully inserted date '%s'";
        public static final String ADDED_RESERVATION = "Successfully reserved - %s";
        public static final String CANCELED_RESERVATION = "Successfully canceled reservation - %s";
        public static final String UPDATED_RESERVATION = "Successfully updated reservation - %s";
        public static final String ADDED_ROOM_UPDATE_WAITING_LIST = "Successfully inserted room '%s'. The waiting list has updated!";
        public static final String CONFIRM_UPDATE_RESERVATION = "Faculty '%s' made a reservation for date %s.\nYou can update it but the reservation may be placed in waiting list.\nDo you want to update it?";
        public static final String LOST_ACTIVE_RESERVATIONS = "\nSome active reservations have been put on the waiting list:\n";
        public static final String HACE_ACTIVE_RESERVATIONS = "\nSome waiting reservations have been changed status to active:\n";
    }

    public static class DATE {

        public static final int ALL_DATE_ID = -1000;
        public static final String ALL_DATE_LABEL = "All";
    }

    public static class FACULTY {

        public static final int ALL_FACULTY_ID = -1000;
        public static final String ALL_FACULTY_LABEL = "All";
    }
    
    public static class ROOM {

        public static final int ALL_ROOM_ID = -1000;
        public static final String ALL_ROOM_LABEL = "All";
    }

}
