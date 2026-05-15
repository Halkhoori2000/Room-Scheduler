/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service;

import com.constant.Constants;
import com.dao.ReservationDAO;
import com.dao.RoomDAO;
import com.dto.ReservationDTO;
import com.model.Reservation;
import com.model.Room;
import com.utils.MessageUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RoomService {

    public static int add(String name, int seats) {
        Room room = RoomDAO.findByName(name);
        if (room != null) {
            MessageUtil.showError(String.format(Constants.MESSAGE.NAME_EXISTS, name));
            return -1;
        }
        int newId = RoomDAO.add(name, seats);
        if (newId > -1) {
            //success
            List<Reservation> reservations = ReservationDAO.getHighestWaitingReservation(seats);
            if (!reservations.isEmpty()) {
                boolean updateSuccess = ReservationDAO.activeWaitingReservations(newId, reservations);
                if (updateSuccess) {
                    MessageUtil.showInfo(String.format(Constants.MESSAGE.ADDED_ROOM_UPDATE_WAITING_LIST, name));
                } else {
                    MessageUtil.showInfo(String.format(Constants.MESSAGE.ADDED_ROOM, name));
                }
            }
        } else {
            MessageUtil.showError(Constants.MESSAGE.INSERT_FAIL);
        }
        return newId;
    }

    public static boolean delete(int id, String name) {
        List<ReservationDTO> oldActiveReservations = ReservationDAO.getActiveReservationsByRoom(id);
        boolean deleteSuccess = RoomDAO.delete(id);
        StringBuilder successMessage = new StringBuilder("");
        if (deleteSuccess) {
            successMessage.append(String.format(Constants.MESSAGE.DELETED_ROOM, name));
            List<Reservation> reservations = ReservationDAO.getReservationsByRoom(id);
            if (!reservations.isEmpty()) {
                Map<Integer, ReservationDTO> hmOfActiveReservation = new HashMap<>();
                List<Integer> dateIds = reservations.stream().map(reservation -> reservation.getDateId()).collect(Collectors.toList());
                ReservationDAO.setWaitingReservations(reservations);
                dateIds.stream().forEach(dateId -> {
                    Map<Integer, ReservationDTO> changedActiveReservations = ReservationDAO.setActiveReservations(dateId);
                    hmOfActiveReservation.putAll(changedActiveReservations);
                });

                List<ReservationDTO> lostActiveReservations = new ArrayList<>();
                oldActiveReservations.stream().forEach(oldActiveReservation -> {
                    if (!hmOfActiveReservation.containsKey(oldActiveReservation.getId())) {
                        lostActiveReservations.add(oldActiveReservation);
                    }
                });
                if (!lostActiveReservations.isEmpty()) {
                    successMessage.append(Constants.MESSAGE.LOST_ACTIVE_RESERVATIONS);
                    lostActiveReservations.stream().forEach(lostActiveReservation -> {
                        successMessage.append("\n").append(lostActiveReservation.waitingInfo());
                    });
                }
            }
        }
        if (deleteSuccess) {
            MessageUtil.showInfo(successMessage.toString());
        } else {
            MessageUtil.showError(Constants.MESSAGE.DELETE_FAIL);
        }
        return deleteSuccess;
    }
}
