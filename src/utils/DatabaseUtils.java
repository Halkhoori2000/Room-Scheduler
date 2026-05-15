/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUtils {

//    private static final String JDBC_DRIVER_NAME = "org.apache.derby.jdbc.ClientDriver";
    private static final String JDBC_DRIVER_NAME = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final int PORT = 1527;

    public static Connection getConnection() {
        try {
            Class.forName(JDBC_DRIVER_NAME);
            String dbURL1 = "jdbc:derby://localhost:" + PORT + "/RoomScheduler;user=java;password=java;create=false";
            return DriverManager.getConnection(dbURL1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Data> executeQuery(String query) {
        return executeQuery(query, null);
    }

    public static List<Data> executeQuery(String query, List<Object> valueList) {
        return DatabaseUtils.executeQuery(null, query, valueList);
    }

    public static List<Data> executeQuery(Connection connectionInput, String query, List<Object> valueList) {
        if (StringUtils.isEmpty(query)) {
            return new ArrayList<>();
        }
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        List<Data> list = new ArrayList<>();
        Connection connection;
        connection = connectionInput != null ? connectionInput : DatabaseUtils.getConnection();
        if (connection == null) {
            return list;
        }
        ResultSet rs = null;
        try {
            if (valueList == null) {
                // create Statement
                statement = getStatement(connection);
                rs = statement.executeQuery(query);
            } else {
                preparedStatement = connection.prepareStatement(query);
                mappingValues(valueList, preparedStatement);
                rs = preparedStatement.executeQuery();
                valueList.clear();
            }
            mappingResultSet(list, rs);
            return list;
        } catch (Exception e) {
            System.out.println("executeQuery sql:" + query);
            System.out.println("executeQuery error:" + e.getMessage());
            return list;
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (Exception ignored) {
            }
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (Exception ignored) {
            }
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception ignored) {
            }
            if (connectionInput == null)
				try {
                connection.close();
            } catch (Exception error) {
                error.printStackTrace();
            }
        }
    }

    public static Statement getStatement(Connection connection) throws SQLException {
        return connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    }

    public static void mappingValues(List<Object> valueList, PreparedStatement preparedStatement) throws SQLException {
        try {
            int count = 1;
            for (Object value : valueList) {
                if (StringUtils.isEmpty("" + value)) {
                    preparedStatement.setObject(count, null);
                } else {
                    preparedStatement.setObject(count, value);
                }
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            int count = 1;
            for (Object value : valueList) {
                System.out.println("Value" + count + "=" + value);
                count++;
            }
            throw e;
        }
    }

    public static void mappingResultSet(List<Data> list, ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int count = metaData.getColumnCount();
        List<String> columnName = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            String colum = metaData.getColumnLabel(i);
            columnName.add(colum);
        }
        while (rs.next()) {
            list.add(map(rs, columnName));
        }
    }

    public static Data map(ResultSet resultSet, List<String> columnName) throws SQLException {
        Data data = new Data();
        for (String colName : columnName) {
            data.put(colName.toUpperCase(), resultSet.getObject(colName));
        }
        return data;
    }

    public static List<Object> prepareParamsStatement(Object... params) {
        List<Object> lst = new ArrayList<>();
        for (Object param : params) {
            lst.add(param);
        }
        return lst;
    }

    public static Integer executeInsert(String query, List<Object> valueList) {
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        Connection connection = DatabaseUtils.getConnection();
        if (connection == null) {
            return -1;
        }
        int idObjet = -1;
        ResultSet rs = null;
        try {

            if (valueList == null) {
                // create Statement
                statement = getStatement(connection);
                statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
                rs = statement.getGeneratedKeys();

            } else {
                preparedStatement = getPreparedStatement(query, connection);
                mappingValues(valueList, preparedStatement);
                preparedStatement.executeUpdate();
                rs = preparedStatement.getGeneratedKeys();
            }
            if (rs.next()) {
                idObjet = rs.getInt(1);
            }
            return idObjet;
        } catch (Exception e) {
            System.out.println("executeInsert error:" + e.getMessage());
            return -1;
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (Exception ignored) {
            }
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (Exception ignored) {
            }
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception ignored) {
            }
            try {
                connection.close();
            } catch (Exception ignored) {
            }
            if (valueList != null) {
                valueList.clear();
            }
        }
    }

    public static PreparedStatement getPreparedStatement(String query, Connection connection) throws SQLException {
        return isInsertSQL(query) ? connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
                : connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    }

    private static boolean isInsertSQL(String query) {
        return query.toUpperCase().trim().startsWith("INSERT");
    }

    public static List<Object> executeUpdate(String query, List<Object> valueList) {
        return executeUpdate(null, query, valueList);
    }

    public static List<Object> executeUpdate(Connection connectionInput, String query) {
        return executeUpdate(connectionInput, query, null);
    }

    public static List<Object> executeUpdate(Connection connectionInput, String query, List<Object> valueList) {
        Connection connection = connectionInput == null ? DatabaseUtils.getConnection() : connectionInput;
        List<Object> keys = new ArrayList<>();
        if (connection == null) {
            return null;
        }
        ResultSet rs = null;
        try {
            if (valueList == null) {
                // create Statement
                try ( Statement statement = getStatement(connection)) {
                    int result = statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
                    if (isInsertSQL(query)) {
                        rs = statement.getGeneratedKeys();
                        while (rs.next()) {
                            keys.add(rs.getInt(1));
                        }
                    } else {
                        keys.add(result);
                    }
                    return keys;
                } catch (Exception e) {
                    System.out.println("executeUpdate error 1:" + e.getMessage());
                    return null;
                }

            } else {
                try ( PreparedStatement preparedStatement = getPreparedStatement(query, connection)) {
                    mappingValues(valueList, preparedStatement);
                    int result = preparedStatement.executeUpdate();
                    if (isInsertSQL(query)) {
                        rs = preparedStatement.getGeneratedKeys();
                        while (rs.next()) {
                            keys.add(rs.getInt(1));
                        }
                    } else {
                        keys.add(result);
                    }
                    return keys;
                } catch (Exception e) {
                    System.out.println("executeUpdate error 2:" + e.getMessage());
                    return null;
                }

            }
        } catch (Exception e) {
            System.out.println("executeUpdate error 3:" + e.getMessage());
            return null;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception ignored) {
            }
            try {
                if (connectionInput == null) {
                    connection.close();
                }
            } catch (Exception ignored) {
                System.out.println("executeUpdate error 4:" + ignored.getMessage());
            }
            if (valueList != null) {
                valueList.clear();
            }
        }
    }

    public static Object executeUpdateReturnKey(String query) {
        return executeUpdateReturnKey(query, null);
    }

    public static Object executeUpdateReturnKey(String query, List<Object> valueList) {
        return executeUpdateReturnKey(null, query, valueList);
    }

    public static Object executeUpdateReturnKey(Connection connectionInput, String query, List<Object> valueList) {
        Connection connection = connectionInput == null ? DatabaseUtils.getConnection() : connectionInput;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet rs = null;
        if (connection == null) {
            return null;
        }
        try {

            if (valueList == null) {
                // create Statement
                statement = getStatement(connection);
                int result = statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
                if (isInsertSQL(query)) {
                    rs = statement.getGeneratedKeys();
                }

            } else {
                preparedStatement = getPreparedStatement(query, connection);
                mappingValues(valueList, preparedStatement);
                int result = preparedStatement.executeUpdate();
                if (isInsertSQL(query)) {
                    rs = preparedStatement.getGeneratedKeys();
                }
            }
            if (rs != null && rs.next()) {
                Object generatedKey = rs.getObject(1);
                System.out.println("executeUpdateReturnKey generatedKey:" + generatedKey);
                return generatedKey;
            }
            return null;
        } catch (Exception e) {
            System.out.println("executeUpdateReturnKey error1:" + e.getMessage());
            return null;
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (Exception ignored) {
            }
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (Exception ignored) {
            }
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception ignored) {
            }
            try {
                if (connectionInput == null) {
                    connection.close();
                }
            } catch (Exception ignored) {
                System.out.println("executeUpdateReturnKey error2:" + ignored.getMessage());
            }
        }
    }

    public static boolean checkUpdateSuccess(List<Object> lst) {
        if (lst == null || lst.isEmpty()) {
            return false;
        }

        return (Integer) lst.get(0) > 0;
    }
}
