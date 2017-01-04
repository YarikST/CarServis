package Data.BaseInit;

import Data.BaseInit.BaseRead.Generator;
import Data.BaseSQL;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by admin-iorigins on 26.11.16.
 */
public class Task {
    private BaseSQL baseSQL;
    private Method method;
    private Generator generator;

    public Task(BaseSQL baseSQL, Method method, Generator generator) {
        this.baseSQL = baseSQL;
        this.method = method;
        this.generator = generator;
    }

    public void run() {
        String mas[];
            try {
                while ((mas = generator.get()) != null) {
                    method.invoke(baseSQL, (Object) mas);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

    }
}
