/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service;

import com.constant.Constants;
import com.dao.ReservationDAO;
import com.dto.ReservationDTO;
import com.model.Date;
import com.model.Faculty;
import com.model.Reservation;
import com.model.Room;
import com.utils.MessageUtil;
import java.sql.Timestamp;
import java.util.Map;

public class ReservationService {

    public static int addReservation(Date date, int seats, Faculty faculty) {
        int newId = insertReservation(faculty, date, seats);
        if (newId > -1) {
            ReservationDTO newReservation = ReservationDAO.findDtoById(newId);
            if (newReservation != null) {
                MessageUtil.showInfo(String.format(Constants.MESSAGE.ADDED_RESERVATION, newReservation.toStringInfo()));
            } else {
                MessageUtil.showInfo(Constants.MESSAGE.INSERT_SUCESS);
            }
            return newId;
        } else {
            MessageUtil.showError(Constants.MESSAGE.INSERT_FAIL);
        }
        return newId;
    }

    private static int insertReservation(Faculty faculty, Date date, int seats) {
        Timestamp currentTime = new Timestamp(new java.util.Date().getTime());
        Reservation reservation = new Reservation(faculty.getId(), date.getId(), seats);
        reservation.setDateRequested(currentTime);
        Room room = ReservationDAO.getRoomAvailable(date.getId(), seats);
        if (room != null) {
            reservation.setRoomId(room.getId());
            reservation.setStatus(Constants.RESERVASION_STATUS.ACTIVE);
        } else {
            reservation.setStatus(Constants.RESERVASION_STATUS.WAITING);
        }
        return ReservationDAO.add(reservation);
    }

    public static int updateReservation(Date date, int seats, int reservationId, Faculty faculty) {
        boolean deleted = ReservationDAO.delete(reservationId);
        if (deleted) {
            System.out.println("updateReservation date.getId():" + date.getId());
            ReservationDAO.setActiveReservations(date.getId());
            int newId = insertReservation(faculty, date, seats);
            if (newId > -1) {
                ReservationDTO newReservation = ReservationDAO.findDtoById(newId);
                if (newReservation != null) {
                    MessageUtil.showInfo(String.format(Constants.MESSAGE.UPDATED_RESERVATION, newReservation.toStringInfo()));
                } else {
                    MessageUtil.showInfo(Constants.MESSAGE.INSERT_SUCESS);
                }
                return newId;
            } else {
                MessageUtil.showError(Constants.MESSAGE.UPDATE_FAIL);
            }
        }
        return -1;
    }

    public static boolean deleteReservation(int reservationId) {
        ReservationDTO selectedReservation = ReservationDAO.findDtoById(reservationId);
        if (selectedReservation != null) {
            boolean deleted = ReservationDAO.delete(reservationId);
            StringBuilder message = new StringBuilder("");
            if (deleted) {
                message.append(String.format(Constants.MESSAGE.CANCELED_RESERVATION, selectedReservation.toStringBasicInfo()));
                Map<Integer, ReservationDTO> newActiveReservations = ReservationDAO.setActiveReservations(selectedReservation.getDate().getId());
                if (!newActiveReservations.isEmpty()) {
                    message.append(Constants.MESSAGE.HACE_ACTIVE_RESERVATIONS);
                    newActiveReservations.keySet().stream().forEach(key -> {
                        ReservationDTO newActiveReservation = newActiveReservations.get(key);
                        message.append(newActiveReservation.toStringInfo()).append("\n");
                    });
                }
            }
            if (deleted) {
                MessageUtil.showInfo(message.toString());
            } else {
                MessageUtil.showError(Constants.MESSAGE.DELETE_FAIL);
            }
            return deleted;
        }
        return false;
    }
}
