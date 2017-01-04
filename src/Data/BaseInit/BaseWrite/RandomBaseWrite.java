package Data.BaseInit.BaseWrite;

import java.util.Collections;
import java.util.LinkedList;

/**
 * Created by admin-iorigins on 27.11.16.
 */
public class RandomBaseWrite {
    private BaseWrite baseWrite;
    private LinkedList<String>listConstant, listParameter;

    public RandomBaseWrite(BaseWrite baseWrite, LinkedList<String> listConstant, LinkedList<String>[] listParameter) {
        this.baseWrite = baseWrite;
        this.listConstant = listConstant;
        this.listParameter = new LinkedList<>();


        for (LinkedList<String> strings : listParameter) {
            Collections.rotate(strings, 1);
        }

        init(listParameter);
    }

    private void init(LinkedList<String>[] listParameter) {
        int n = listParameter[0].size();
        for (LinkedList<String> strings : listParameter) {
            if (strings.size() < n) {
                n = strings.size();
            }
        }


        int i = 0;
        int k = 0;
        while (true) {
            if (i == listParameter.length) {
                k++;

                if (k == n) {
                    break;
                }
                this.listParameter.add(null);
                i = 0;
            }
            LinkedList<String> strings = listParameter[i++];
            this.listParameter.add(strings.poll());
        }

    }

    public void write() {
        for (String s : listConstant) {
            baseWrite.addConst(s);
        }

        while(listParameter.size()!=0) {
            StringBuffer buffer = new StringBuffer();

            for (String s = listParameter.poll();s!=null;s=listParameter.poll()) {
                buffer.append(s + ",");
            }
            buffer.deleteCharAt(buffer.length() - 1);
            baseWrite.addParam(buffer.toString());
            if (listParameter.size() != 0) {
                baseWrite.addParam(null);
            }
        }
        baseWrite.write();
    }
}



