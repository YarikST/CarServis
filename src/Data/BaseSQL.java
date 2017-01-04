package Data;


import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.lang.ref.SoftReference;
import java.sql.*;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;


/*
Клас являє собою функціонал бази даних.
 */
public class BaseSQL implements AutoCloseable {
    private static final String GET_TABLE;
    private static final String ADD_TABLE;
    private static final String UPDATE_TABLE;
    private static final String SET_TABLE;
    private static final String REMOVE_TABLE;
    private static final String CREATE_DATA_BASE;
    private static final String REMOVE_DATA_BASE;


    private static final List<SoftReference<BaseSQL>> SOFT_REFERENCES = new CopyOnWriteArrayList<>();

    private static int size = 5;

    static {
        GET_TABLE = "SELECT ? FROM ? WHERE ?";
        ADD_TABLE = "CREATE TABLE ?(?)";
        UPDATE_TABLE = "UPDATE ? SET ?=? WHERE ?";
        SET_TABLE = "INSERT INTO ? (?)VALUES(?)";
        REMOVE_TABLE = "DELETE FROM ? WHERE ?";
        CREATE_DATA_BASE = "CREATE DATABASE ?";
        REMOVE_DATA_BASE = "DROP DATABASE ?";

        try {
            rowSetFactory = RowSetProvider.newFactory();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Properties properties = System.getProperties();
        String nameDriver = "jdbc.drivers";
        String classDriver = "com.mysql.jdbc.Driver";

            properties.setProperty(nameDriver, classDriver);

        try {
            Class.forName(classDriver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        properties.put("useUnicode","true");
        properties.put("characterEncoding","UTF-8");
    }

    private String inetAddress;
    private String login,pass;

    private Connection connection;
    private static RowSetFactory rowSetFactory;

    private Logger log = Logger.getLogger("BeseSQL");

    private BaseSQL(String inetAddress, String login, String pass) {
        log.log(Level.INFO,"new BeseSQL "+inetAddress);
        this.inetAddress = inetAddress;
        this.login = login;
        this.pass = pass;

        try {
            log.log(Level.INFO, "login "+login+":pass "+pass);
            connection = getConnection();
        } catch (SQLException e) {
            if (log.isTraceEnabled()) {
                log.log(Level.TRACE, "Exception", e);
            }
        }
        SOFT_REFERENCES.add(new SoftReference<BaseSQL>(this));
    }

    private Connection getConnection() throws SQLException {
            return DriverManager.getConnection(inetAddress, login, pass);
    }

    public static BaseSQL getBase(String inetAddress,String login, String pass) {
        BaseSQL baseSQL = new BaseSQL(inetAddress, login, pass);

        BaseSQL baseSQLL = baseSQL;
        for (SoftReference<BaseSQL> reference : SOFT_REFERENCES) {
            if (baseSQL.equals(reference.get())) {
                baseSQL = reference.get();
                break;
            }

        }
        if (baseSQL == null) {
                baseSQL = baseSQLL;
        }

        return baseSQL;
    }


    public synchronized CachedRowSet getTable(String[] mas) {
        try {

            CachedRowSet cachedRowSet = rowSetFactory.createCachedRowSet();
            cachedRowSet.setUrl(inetAddress);
            cachedRowSet.setUsername(login);
            cachedRowSet.setPassword(pass);

                cachedRowSet.setPageSize(size);

            cachedRowSet.setCommand(set(mas, GET_TABLE));
            cachedRowSet.execute();

            return cachedRowSet;
        } catch (Exception e) {
            if (log.isTraceEnabled()) {
                log.log(Level.TRACE, "Exception", e);
            }
        }

        return null;
    }

    public synchronized void addTable(String[]mas) {
        try {
         Statement   preparedStatement = connection.createStatement();
            preparedStatement.executeUpdate(set(mas, ADD_TABLE));
        } catch (SQLException e) {
            if (log.isTraceEnabled()) {
                log.log(Level.TRACE, "Exception", e);
            }
        }
    }

    public synchronized void updateTable(String mas[]) {
        try {
            Statement   preparedStatement = connection.createStatement();
            preparedStatement.executeUpdate(set(mas, UPDATE_TABLE));
        } catch (SQLException e) {
            if (log.isTraceEnabled()) {
                log.log(Level.TRACE, "Exception", e);
            }
        }
    }

    public synchronized void setTable(String[]mas) {
        try {
            Statement   preparedStatement = connection.createStatement();
            preparedStatement.executeUpdate(set(mas, SET_TABLE));
        } catch (SQLException e) {
            if (log.isTraceEnabled()) {
                log.log(Level.TRACE, "Exception", e);
            }
        }
    }

    public synchronized void removeTable(String [] mas) {
        try {
            Statement   preparedStatement = connection.createStatement();
            preparedStatement.executeUpdate(set(mas, REMOVE_TABLE));
        } catch (SQLException e) {
            if (log.isTraceEnabled()) {
                log.log(Level.TRACE, "Exception", e);
            }
        }
    }


    public synchronized void createDataBase(String[] mas) {
        try {
            Statement   preparedStatement = connection.createStatement();
            preparedStatement.executeUpdate(set(mas, CREATE_DATA_BASE));
        } catch (SQLException e) {
            if (log.isTraceEnabled()) {
                log.log(Level.TRACE, "Exception", e);
            }
        }
    }

    public synchronized void removeDataBase(String[] mas) {
        try {
            Statement   preparedStatement = connection.createStatement();
            preparedStatement.executeUpdate(set(mas,REMOVE_TABLE));
        } catch (SQLException e) {
            if (log.isTraceEnabled()) {
                log.log(Level.TRACE, "Exception", e);
            }
        }
    }

    private String set(String mas[],String str) throws SQLException {
        for (int i = 0; i < mas.length; i++) {
            if (!mas[i].equals("null")) {
                str = str.replaceFirst("\\?", mas[i]);
            } else {
                str = str.replaceFirst("\\s\\w+.\\?", "");
            }
        }
        System.out.println(str);
      log.log(Level.DEBUG,str);
        return str;
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            if (log.isTraceEnabled()) {
                log.log(Level.TRACE, "Exception", e);
            }
        }
    }

    @Override
    public String toString() {
        return inetAddress + " " + login + " " + pass;
    }
}
