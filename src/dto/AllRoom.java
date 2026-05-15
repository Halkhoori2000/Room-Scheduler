/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dto;

import com.constant.Constants;
import com.model.Room;

public class AllRoom extends Room {

    public AllRoom() {
        this.setId(Constants.ROOM.ALL_ROOM_ID);
        this.setName(Constants.ROOM.ALL_ROOM_LABEL);
    }

    private static final AllRoom instance = new AllRoom();

    public static AllRoom getInstance() {
        return instance;
    }
}
