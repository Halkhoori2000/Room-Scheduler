/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

public class Room {

    private int id;
    private String name;
    private int seats;
    private String status;

    public Room() {

    }

    public Room(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Room(int id, String name, int seats) {
        this.id = id;
        this.name = name;
        this.seats = seats;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return this.name;
    }
}
