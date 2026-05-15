/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dto;

public class Message {

    private int newId;
    private String msg = null;

    public Message(int newId) {
        this.newId = newId;
    }

    public Message(String msg) {
        this.msg = msg;
    }

    public Message(int newId, String msg) {
        this.newId = newId;
        this.msg = msg;
    }

    public int getNewId() {
        return newId;
    }

    public void setNewId(int newId) {
        this.newId = newId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
