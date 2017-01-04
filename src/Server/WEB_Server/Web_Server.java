package Server.WEB_Server;

import Data.BaseSQL;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by admin-iorigins on 03.11.16.
 */
@WebService
public class Web_Server {
    private BaseSQL baseSQL;


    @WebMethod
    public void mathod(String name,Object []arg) {
        Class<? extends BaseSQL> aClass = baseSQL.getClass();
        Class<?> mas[] = new Class[arg.length];
        for (int i = 0; i < arg.length; i++) {
            mas[i] = arg[i].getClass();
        }
        try {
            Method method = aClass.getMethod(name, mas);
            method.invoke(method, arg);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
