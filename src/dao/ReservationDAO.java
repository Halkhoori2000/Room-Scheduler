/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.constant.Constants;
import com.dto.ReservationDTO;
import com.model.Date;
import com.model.Faculty;
import com.model.Reservation;
import com.model.Room;
import com.utils.Data;
import com.utils.DatabaseUtils;
import com.utils.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservationDAO {

    public static int add(Reservation reservation) {
        List<Object> values = new ArrayList<>();
        values.add(reservation.getFacultyId());
        values.add(reservation.getDateId());
        values.add(reservation.getSeats());
        StringBuilder columnsPart = new StringBuilder("INSERT INTO reservation (FACULTY_ID, DATE_ID, SEATS");
        StringBuilder valuesPart = new StringBuilder("VALUES(?, ?, ?");
        if (StringUtils.isNotEmpty(reservation.getStatus())) {
            columnsPart.append(", STATUS");
            valuesPart.append(", ?");
            values.add(reservation.getStatus());
        }
        if (reservation.getDateRequested() != null) {
            columnsPart.append(", DATE_REQUESTED");
            valuesPart.append(", ?");
            values.add(reservation.getDateRequested());
        }
        if (reservation.getRoomId() > 0) {
            columnsPart.append(", ROOM_ID");
            valuesPart.append(", ?");
            values.add(reservation.getRoomId());
        }
        columnsPart.append(")");
        valuesPart.append(")");
        String sql = columnsPart.toString() + valuesPart.toString();
        int newId = DatabaseUtils.executeInsert(sql, values);
        return newId;
    }

    public static Reservation getById(int id) {
        String sql = "SELECT * FROM RESERVATION WHERE ID = ?";
        List<Object> values = new ArrayList<>();
        values.add(id);
        List<Data> result = DatabaseUtils.executeQuery(sql, values);
        if (result.size() > 0) {
            Data data = result.get(0);
            return new Reservation(data.getInt("ID"), data.getInt("FACULTY_ID"), data.getInt("DATE_ID"), data.getTimestamp("DATE_REQUESTED"),
                    data.getInt("SEATS"), data.getString("STATUS"), data.getInt("ROOM_ID"));
        }
        return null;
    }

    public static java.sql.Timestamp getMinDate(int dateId) {
        StringBuilder sql = new StringBuilder("");
        sql.append("SELECT MIN(RESERVATION.DATE_REQUESTED) AS MINDATE, DATE.DATE")
                .append(" FROM RESERVATION, DATE WHERE RESERVATION.DATE_ID = DATE.ID")
                .append(" AND DATE.ID = ? GROUP BY DATE.DATE");
        List<Object> values = new ArrayList<>();
        values.add(dateId);
        List<Data> result = DatabaseUtils.executeQuery(sql.toString(), values);
        if (result.size() > 0) {
            return result.get(0).getTimestamp("MINDATE");
        }
        return null;
    }

    public static Room getRoomAvailable(int dateId, int seats) {
        Room room = null;
        String sql = "SELECT * FROM ROOM WHERE"
                + " ROOM.SEATS >= ? AND ROOM.STATUS = 'active' AND ROOM.ID NOT IN"
                + " ("
                + " SELECT RESERVATION.ROOM_ID FROM RESERVATION"
                + " WHERE RESERVATION.DATE_ID = ?)"
                + " ORDER BY ROOM.SEATS ASC"
                + " FETCH FIRST ROW ONLY";
        List<Object> values = new ArrayList<>();
        values.add(seats);
        values.add(dateId);
        List<Data> result = DatabaseUtils.executeQuery(sql, values);
        if (result.size() > 0) {
            room = new Room(result.get(0).getInt("ID"), result.get(0).getString("NAME"), result.get(0).getInt("SEATS"));
        }
        return room;
    }

    public static int checkReservationExists(int dateId, int facultyId) {
        String sql = "SELECT * FROM RESERVATION WHERE DATE_ID = ? AND FACULTY_ID = ?";
        List<Object> values = new ArrayList<>();
        values.add(dateId);
        values.add(facultyId);
        List<Data> result = DatabaseUtils.executeQuery(sql, values);
        if (result.size() > 0) {
            return result.get(0).getInt("ID");
        }
        return -1;
    }

//    public static List<ReservationDTO> getReservedListDTO() {
//        return getReservedListDTO(null);
//    }
//
//    public static List<ReservationDTO> getReservedListDTO(Faculty user) {
//        List<ReservationDTO> reservations = new ArrayList<>();
//        StringBuilder sql = new StringBuilder("");
//        sql.append("SELECT RESERVATION.*, FACULTY.NAME AS FACULTY_NAME, ROOM.NAME AS ROOM_NAME, DATE.DATE AS RESERVED_DATE FROM RESERVATION, FACULTY, ROOM, DATE")
//                .append(" WHERE RESERVATION.STATUS = 'active' and RESERVATION.ROOM_ID = ROOM.ID AND RESERVATION.FACULTY_ID = FACULTY.ID AND RESERVATION.DATE_ID = DATE.ID")
//                .append(user != null ? " AND RESERVATION.FACULTY_ID = ?" : "")
//                .append(" ORDER BY RESERVED_DATE ASC, RESERVATION.DATE_REQUESTED ASC");
//        List<Object> values = new ArrayList<>();
//        if (user != null) {
//            values.add(user.getId());
//        }
//        List<Data> result = DatabaseUtils.executeQuery(sql.toString(), values);
//        result.stream().forEach(data -> {
//            Room room = new Room(data.getInt("ROOM_ID"), data.getString("ROOM_NAME"));
//            Faculty faculty = new Faculty(data.getInt("FACULTY_ID"), data.getString("FACULTY_NAME"));
//            Date date = new Date(data.getInt("DATE_ID"), data.getDate("RESERVED_DATE"));
//            ReservationDTO reservation = new ReservationDTO(data.getInt("ID"), faculty, date, data.getTimestamp("DATE_REQUESTED"), data.getInt("SEATS"), data.getString("STATUS"), room);
//            reservations.add(reservation);
//        });
//        return reservations;
//    }
//
//    public static List<ReservationDTO> getWaitingListDTO() {
//        return getWaitingListDTO(null);
//    }
//
//    public static List<ReservationDTO> getWaitingListDTO(Faculty user) {
//        List<ReservationDTO> reservations = new ArrayList<>();
//        StringBuilder sql = new StringBuilder("");
//        sql.append("SELECT RESERVATION.*, FACULTY.NAME AS FACULTY_NAME, DATE.DATE AS RESERVED_DATE FROM RESERVATION, FACULTY, DATE")
//                .append(" WHERE RESERVATION.STATUS = 'waiting' AND RESERVATION.FACULTY_ID = FACULTY.ID AND RESERVATION.DATE_ID = DATE.ID")
//                .append(user != null ? " AND RESERVATION.FACULTY_ID = ?" : "")
//                .append(" ORDER BY RESERVED_DATE ASC, RESERVATION.DATE_REQUESTED ASC");
//
//        List<Object> values = new ArrayList<>();
//        if (user != null) {
//            values.add(user.getId());
//        }
//        List<Data> result = DatabaseUtils.executeQuery(sql.toString(), values);
//        result.stream().forEach(data -> {
//            Faculty faculty = new Faculty(data.getInt("FACULTY_ID"), data.getString("FACULTY_NAME"));
//            Date date = new Date(data.getInt("DATE_ID"), data.getDate("RESERVED_DATE"));
//            ReservationDTO reservation = new ReservationDTO(data.getInt("ID"), faculty, date, data.getTimestamp("DATE_REQUESTED"), data.getInt("SEATS"), data.getString("STATUS"), null);
//            reservations.add(reservation);
//        });
//        return reservations;
//    }
    public static boolean delete(int reservationId) {
        String sql = "DELETE FROM RESERVATION WHERE ID = ?";
        List<Object> values = new ArrayList<>();
        values.add(reservationId);
        List<Object> result = DatabaseUtils.executeUpdate(sql, values);
        return DatabaseUtils.checkUpdateSuccess(result);
    }

    private static List<ReservationDTO> getWaitingByDate(int dateId) {
        List<ReservationDTO> reservations = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT RESERVATION.*, FACULTY.NAME AS FACULTY_NAME, DATE.DATE AS RESERVED_DATE FROM RESERVATION, FACULTY, DATE")
                .append(" WHERE RESERVATION.FACULTY_ID = FACULTY.ID AND RESERVATION.DATE_ID = DATE.ID AND RESERVATION.DATE_ID = ? AND RESERVATION.STATUS = ? ORDER BY DATE_REQUESTED ASC");
        List<Object> values = new ArrayList<>();
        values.add(dateId);
        values.add(Constants.RESERVASION_STATUS.WAITING);
        List<Data> result = DatabaseUtils.executeQuery(sql.toString(), values);
        result.stream().forEach(data -> {
            Room room = new Room(-1, null);
            Faculty faculty = new Faculty(data.getInt("FACULTY_ID"), data.getString("FACULTY_NAME"));
            Date date = new Date(data.getInt("DATE_ID"), data.getDate("RESERVED_DATE"));
            reservations.add(new ReservationDTO(data.getInt("ID"), faculty, date, data.getTimestamp("DATE_REQUESTED"), data.getInt("SEATS"), data.getString("STATUS"), room));
        });
        return reservations;
    }

    public static Map<Integer, ReservationDTO> setActiveReservations(int dateId) {
        Map<Integer, ReservationDTO> hmOfActiveReservation = new HashMap<>();
        //waitingReservations ordered by DATE_REQUESTED
        List<ReservationDTO> waitingReservations = getWaitingByDate(dateId);
        waitingReservations.stream().forEach(reservation -> {
            Room room = ReservationDAO.getRoomAvailable(dateId, reservation.getSeats());
            if (room != null) {
                String sql = "UPDATE RESERVATION SET STATUS = ?, ROOM_ID = ? WHERE ID = ?";
                List<Object> values = new ArrayList<>();
                values.add(Constants.RESERVASION_STATUS.ACTIVE);
                values.add(room.getId());
                values.add(reservation.getId());
                DatabaseUtils.executeUpdate(sql, values);
                reservation.setStatus(Constants.RESERVASION_STATUS.ACTIVE);
                reservation.setRoom(room);
                hmOfActiveReservation.put(reservation.getId(), reservation);
            }
        });
        return hmOfActiveReservation;
    }

    public static List<Reservation> getHighestWaitingReservation(int seats) {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT RESERVATION.* FROM RESERVATION,"
                + " (SELECT MIN(DATE_REQUESTED) AS MIN_DATE, DATE_ID FROM RESERVATION"
                + " WHERE STATUS = ? AND SEATS <= ?"
                + " GROUP BY DATE_ID) AS SUBTABLE"
                + " WHERE RESERVATION.DATE_REQUESTED = SUBTABLE.MIN_DATE AND RESERVATION.DATE_ID = SUBTABLE.DATE_ID";
        List<Object> values = new ArrayList<>();
        values.add(Constants.RESERVASION_STATUS.WAITING);
        values.add(seats);
        List<Data> result = DatabaseUtils.executeQuery(sql, values);
        result.stream().forEach(data -> {
            Reservation reservation = new Reservation(data.getInt("ID"), data.getInt("FACULTY_ID"), data.getInt("DATE_ID"), data.getTimestamp("DATE_REQUESTED"),
                    data.getInt("SEATS"), data.getString("STATUS"), data.getInt("ROOM_ID"));
            reservations.add(reservation);
        });
        return reservations;
    }

    public static boolean activeWaitingReservations(int roomId, List<Reservation> reservations) {
        List<Object> values = new ArrayList<>();
        values.add(Constants.RESERVASION_STATUS.ACTIVE);
        values.add(roomId);
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE RESERVATION SET STATUS = ?, ROOM_ID = ? WHERE ID IN(");
        boolean first = true;
        for (Reservation reservation : reservations) {
            if (!first) {
                sql.append(",");
            }
            values.add(reservation.getId());
            sql.append("?");
            first = false;
        }
        sql.append(")");
        List<Object> result = DatabaseUtils.executeUpdate(sql.toString(), values);
        return DatabaseUtils.checkUpdateSuccess(result);
    }

    public static List<Reservation> getReservationsByRoom(int roomId) {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT RESERVATION.* FROM RESERVATION WHERE ROOM_ID = ?";
        List<Object> values = new ArrayList<>();
        values.add(roomId);
        List<Data> result = DatabaseUtils.executeQuery(sql, values);
        result.stream().forEach(data -> {
            Reservation reservation = new Reservation(data.getInt("ID"), data.getInt("FACULTY_ID"), data.getInt("DATE_ID"), data.getTimestamp("DATE_REQUESTED"),
                    data.getInt("SEATS"), data.getString("STATUS"), data.getInt("ROOM_ID"));
            reservations.add(reservation);
        });
        return reservations;
    }

    public static List<ReservationDTO> getActiveReservationsByRoom(int roomId) {
        List<ReservationDTO> reservations = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT RESERVATION.*, FACULTY.NAME AS FACULTY_NAME, ROOM.NAME AS ROOM_NAME, DATE.DATE AS RESERVED_DATE FROM RESERVATION, FACULTY, DATE, ROOM")
                .append(" WHERE RESERVATION.FACULTY_ID = FACULTY.ID AND RESERVATION.DATE_ID = DATE.ID AND RESERVATION.ROOM_ID = ROOM.ID AND RESERVATION.ROOM_ID = ? AND RESERVATION.STATUS = ?");
        List<Object> values = new ArrayList<>();
        values.add(roomId);
        values.add(Constants.RESERVASION_STATUS.ACTIVE);
        List<Data> result = DatabaseUtils.executeQuery(sql.toString(), values);
        result.stream().forEach(data -> {
            Room room = new Room(data.getInt("ROOM_ID"), data.getString("ROOM_NAME"));
            Faculty faculty = new Faculty(data.getInt("FACULTY_ID"), data.getString("FACULTY_NAME"));
            Date date = new Date(data.getInt("DATE_ID"), data.getDate("RESERVED_DATE"));
            ReservationDTO reservation = new ReservationDTO(data.getInt("ID"), faculty, date, data.getTimestamp("DATE_REQUESTED"), data.getInt("SEATS"), data.getString("STATUS"), room);
            reservations.add(reservation);
        });
        return reservations;
    }

    public static boolean setWaitingReservations(List<Reservation> reservations) {
        List<Object> values = new ArrayList<>();
        values.add(Constants.RESERVASION_STATUS.WAITING);
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE RESERVATION SET STATUS = ?, ROOM_ID = -1 WHERE ID IN(");
        boolean first = true;
        for (Reservation reservation : reservations) {
            if (!first) {
                sql.append(",");
            }
            values.add(reservation.getId());
            sql.append("?");
            first = false;
        }
        sql.append(")");
        List<Object> result = DatabaseUtils.executeUpdate(sql.toString(), values);
        return DatabaseUtils.checkUpdateSuccess(result);
    }

    public static List<ReservationDTO> search(int facultyId, int dateId, String status, int roomId) {
        List<ReservationDTO> lst = new ArrayList<>();
        List<Data> result;
        if (Constants.ROOM.ALL_ROOM_ID == roomId) {
            result = searchWithoutRoom(facultyId, dateId, status);
        } else {
            result = searchWithRoom(facultyId, dateId, status, roomId);
        }
        result.stream().forEach(data -> {
            Room room = new Room(data.getInt("ROOM_ID"), data.getString("ROOM_NAME"));
            Faculty faculty = new Faculty(data.getInt("FACULTY_ID"), data.getString("FACULTY_NAME"));
            Date date = new Date(data.getInt("DATE_ID"), data.getDate("RESERVED_DATE"));
            ReservationDTO reservation = new ReservationDTO(data.getInt("ID"), faculty, date, data.getTimestamp("DATE_REQUESTED"), data.getInt("SEATS"), data.getString("STATUS"), room);
            lst.add(reservation);
        });
        return lst;
    }

    public static List<Data> searchWithoutRoom(int facultyId, int dateId, String status) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT SUBTABLE.*, ROOM.NAME AS ROOM_NAME FROM")
                .append("(SELECT RESERVATION.*, FACULTY.NAME AS FACULTY_NAME, DATE.DATE AS RESERVED_DATE FROM RESERVATION, FACULTY, DATE");
        StringBuilder whereClause = new StringBuilder("");
        whereClause.append(" WHERE RESERVATION.FACULTY_ID = FACULTY.ID AND RESERVATION.DATE_ID = DATE.ID");
        List<Object> values = new ArrayList<>();
        if (Constants.FACULTY.ALL_FACULTY_ID != facultyId) {
            whereClause.append(" AND RESERVATION.FACULTY_ID = ?");
            values.add(facultyId);
        }
        if (Constants.DATE.ALL_DATE_ID != dateId) {
            whereClause.append(" AND RESERVATION.DATE_ID = ?");
            values.add(dateId);
        }
        if (StringUtils.isNotEmpty(status)) {
            whereClause.append(" AND RESERVATION.STATUS = ?");
            values.add(status);
        }
        sql.append(whereClause.toString()).append(" ORDER BY RESERVED_DATE ASC, RESERVATION.DATE_REQUESTED ASC) AS SUBTABLE")
                .append(" LEFT JOIN ROOM ON SUBTABLE.ROOM_ID = ROOM.ID");
        List<Data> result = DatabaseUtils.executeQuery(sql.toString(), values);
        return result;
    }

    private static List<Data> searchWithRoom(int facultyId, int dateId, String status, int roomId) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT RESERVATION.*, FACULTY.NAME AS FACULTY_NAME, ROOM.NAME AS ROOM_NAME, DATE.DATE AS RESERVED_DATE FROM RESERVATION, FACULTY, DATE, ROOM");
        StringBuilder whereClause = new StringBuilder("");
        whereClause.append(" WHERE RESERVATION.ROOM_ID = ROOM.ID AND RESERVATION.FACULTY_ID = FACULTY.ID AND RESERVATION.DATE_ID = DATE.ID");
        List<Object> values = new ArrayList<>();
        if (Constants.FACULTY.ALL_FACULTY_ID != facultyId) {
            whereClause.append(" AND RESERVATION.FACULTY_ID = ?");
            values.add(facultyId);
        }
        if (Constants.DATE.ALL_DATE_ID != dateId) {
            whereClause.append(" AND RESERVATION.DATE_ID = ?");
            values.add(dateId);
        }
        if (StringUtils.isNotEmpty(status)) {
            whereClause.append(" AND RESERVATION.STATUS = ?");
            values.add(status);
        }
        values.add(roomId);
        sql.append(whereClause.toString()).append(" AND ROOM.ID = ? ORDER BY RESERVED_DATE ASC, RESERVATION.DATE_REQUESTED ASC");
        List<Data> result = DatabaseUtils.executeQuery(sql.toString(), values);
        return result;
    }

    public static ReservationDTO findDtoById(int id) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT SUBTABLE.*, ROOM.NAME AS ROOM_NAME FROM")
                .append("(SELECT RESERVATION.*, FACULTY.NAME AS FACULTY_NAME, DATE.DATE AS RESERVED_DATE FROM RESERVATION, FACULTY, DATE")
                .append(" WHERE RESERVATION.FACULTY_ID = FACULTY.ID AND RESERVATION.DATE_ID = DATE.ID AND RESERVATION.ID = ?) AS SUBTABLE")
                .append(" LEFT JOIN ROOM ON SUBTABLE.ROOM_ID = ROOM.ID");
        List<Object> values = new ArrayList<>();
        values.add(id);
        List<Data> result = DatabaseUtils.executeQuery(sql.toString(), values);
        if (!result.isEmpty()) {
            Data data = result.get(0);
            Room room = new Room(data.getInt("ROOM_ID"), data.getString("ROOM_NAME"));
            Faculty faculty = new Faculty(data.getInt("FACULTY_ID"), data.getString("FACULTY_NAME"));
            Date date = new Date(data.getInt("DATE_ID"), data.getDate("RESERVED_DATE"));
            return new ReservationDTO(data.getInt("ID"), faculty, date, data.getTimestamp("DATE_REQUESTED"), data.getInt("SEATS"), data.getString("STATUS"), room);
        }
        return null;
    }
}
