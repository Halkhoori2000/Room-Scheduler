/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.sql.Timestamp;

public class Reservation {

    private int id;
    private int facultyId;
    private int dateId;
    private java.sql.Timestamp dateRequested;
    private int seats;
    private String status;
    private int roomId;

    public Reservation() {
    }

    public Reservation(int facultyId, int dateId, int seats) {
        this.facultyId = facultyId;
        this.dateId = dateId;
        this.seats = seats;
    }

    public Reservation(int id, int facultyId, int dateId, Timestamp dateRequested, int seats, String status, int roomId) {
        this.id = id;
        this.facultyId = facultyId;
        this.dateId = dateId;
        this.dateRequested = dateRequested;
        this.seats = seats;
        this.status = status;
        this.roomId = roomId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public int getDateId() {
        return dateId;
    }

    public void setDateId(int dateId) {
        this.dateId = dateId;
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

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
}
