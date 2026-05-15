/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

public class Faculty {

    private int id;
    private String name;
    private String role;
    private String status;

    public Faculty() {

    }

    public Faculty(String name) {
        this.name = name;
    }

    public Faculty(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Faculty(int id, String name, String role, String status) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.status = status;
    }

    public Faculty(String name, String role, String status) {
        this.name = name;
        this.role = role;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Faculty other = (Faculty) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

}
