package Data.BaseInit.BaseRead;


import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by admin-iorigins on 26.11.16.
 */
public class FileGenerator implements Generator {
    private Path path;
    private ArrayList<String> listConstants;
    private LinkedList<String> listParameters;
    private boolean boo;

    public FileGenerator(Path path) {
        this.path = path;

        listConstants = new ArrayList<>();
        listParameters = new LinkedList<>();

        try {
            Document document = new SAXBuilder().build(path.toFile());
            init(document);
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void init(Document document) {
        Element rootElement = document.getRootElement();

        Element constant = rootElement.getChild("constant");

        for (Element element : constant.getChildren()) {
            listConstants.add(element.getValue());
        }

        Element parameter = rootElement.getChild("parameter");

        for (Element par : parameter.getChildren()) {
            listParameters.add(par.getChildText("element"));
        }
    }

    @Override
    public String[] get() {
        if (boo) {
            return null;
        }

        StringBuffer buffer = new StringBuffer();
        String mas[] = listConstants.toArray(new String[listConstants.size()+1]);

        String s = listParameters.poll();

        mas[mas.length - 1] = s;

        if (listParameters.size() == 0) {
            boo = true;
        }


        return mas;
    }
}
