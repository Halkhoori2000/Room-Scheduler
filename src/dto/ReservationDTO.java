/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dto;

import com.constant.Constants;
import com.model.Date;
import com.model.Faculty;
import com.model.Room;
import com.utils.CommonUtil;
import java.sql.Timestamp;

public class ReservationDTO {

    private int id;
    private Faculty faculty;
    private Date date;
    private Timestamp dateRequested;
    private int seats;
    private String status;
    private Room room;

    public ReservationDTO() {

    }

    public ReservationDTO(int id, Faculty faculty, Date date, Timestamp dateRequested, int seats, String status, Room room) {
        this.id = id;
        this.faculty = faculty;
        this.date = date;
        this.dateRequested = dateRequested;
        this.seats = seats;
        this.status = status;
        this.room = room;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Timestamp getDateRequested() {
        return dateRequested;
    }

    public void setDateRequested(Timestamp dateRequested) {
        this.dateRequested = dateRequested;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String toStringInfo() {
        return "faculty: " + (faculty != null ? faculty.getName() : "null") + ", reserved date: " + (date != null ? CommonUtil.getDateFormat(date.getDate()) : "null")
                + ", seats: " + seats + ", status: " + status + (room != null && room.getId() != -1 ? (", room: " + room.getName()) : "");
    }

    public String toStringBasicInfo() {
        return "faculty: " + (faculty != null ? faculty.getName() : "null") + ", reserved date: " + (date != null ? CommonUtil.getDateFormat(date.getDate()) : "null");
    }

    public String waitingInfo() {
        return "faculty: " + (faculty != null ? faculty.getName() : "null") + ", reserved date: " + (date != null ? CommonUtil.getDateFormat(date.getDate()) : "null")
                + ", seats: " + seats + ", status: " + Constants.RESERVASION_STATUS.WAITING + ", room: ";
    }
}
