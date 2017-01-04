package Test;

import Data.BaseSQL;

import javax.sql.rowset.CachedRowSet;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * Created by admin-iorigins on 21.11.16.
 */
public class TestSQL {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, SQLException {
        BaseSQL baseSQL = BaseSQL.getBase("jdbc:mysql://localhost/ProjectCar_CTO", "root", "root");

      /*  String arg[] = {"Замовлення","id_фірми,id_замовника,id_послуги,Дата_час,Опис","6,5,5,\"2016-05-02 00-00-00\",\"opus\""};

        baseSQL.setTable(arg);*/

        String[] arg = new String[]{"Password", "Автентифікація", "Email=\"e1\""};
        CachedRowSet table = baseSQL.getTable(arg);

        try {
            do {
                out(table);
            } while (table.nextPage());
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private static void out(CachedRowSet cachedRowSet) {

        try {;
            while (cachedRowSet.next()) {
                ResultSetMetaData metaData = cachedRowSet.getMetaData();

                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    System.out.println(metaData.getColumnName(i)+" "+ cachedRowSet.getString(i));
                }
            }
            System.out.println();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
