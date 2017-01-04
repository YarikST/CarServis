package Test;

import Data.BaseSQL;

import javax.json.*;
import javax.sql.rowset.CachedRowSet;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * Created by admin-iorigins on 03.11.16.
 */
public class MyClass {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException, SQLException, ClassNotFoundException, IOException {
        BaseSQL baseSQL = BaseSQL.getBase("jdbc:mysql://localhost/TestSQL", "root", "root");

        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        CachedRowSet cachedRowSet = baseSQL.getTable(new String[]{"*", "CTO", "null","0"});
        while (cachedRowSet.next()) {
            System.out.println("next");
            ResultSetMetaData metaData = cachedRowSet.getMetaData();



            JsonObjectBuilder builder = Json.createObjectBuilder();
            for(int i = 1;i<=metaData.getColumnCount();i++) {
                builder.add(metaData.getColumnName(i), cachedRowSet.getString(i));
            }
            arrayBuilder.add(builder.build());
        }
        objectBuilder.add("rows", arrayBuilder.build());
        baseSQL.close();
        JsonObject build = objectBuilder.build();

        StringWriter stringWriter = new StringWriter();
        JsonWriter jsonWriter = Json.createWriter(stringWriter);
        jsonWriter.writeObject(build);
        jsonWriter.close();

        String data = stringWriter.toString();
        System.out.println(data);

    }
}
