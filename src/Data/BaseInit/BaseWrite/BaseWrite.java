package Data.BaseInit.BaseWrite;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.LinkedList;

/**
 * Created by admin-iorigins on 26.11.16.
 */
public class BaseWrite {
    private Path path;
    private Document document;
    private OutputStream outputStream;
    private LinkedList<String> listConstant;
    private LinkedList<String> listParameters;


    public BaseWrite(Path path) {
        this.path = path;
        listConstant = new LinkedList<>();
        listParameters = new LinkedList<>();
        try {
            outputStream = new FileOutputStream(path.toFile(),true);
            document = new Document();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addConst(String s) {
        listConstant.add(s);
    }
    public void addParam(String s) {
        listParameters.add(s);
    }

    public void write() {
        document();

        XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
        try {
            xmlOutputter.output(document, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void document() {
        Element root = new Element("root");
        Element constant = new Element("constant");

        for (String s : listConstant) {
            Element element = new Element("element");
            element.setText(s);
            constant.addContent(element);

        }

        Element parameter = new Element("parameter");

        Element par = new Element("par");
        for (String s : listParameters) {
            if (s != null) {
                Element element = new Element("element");
                element.setText(s);
                par.addContent(element);
            } else {
                parameter.addContent(par);
                par = new Element("par");
            }
        }
        parameter.addContent(par);

        root.addContent(constant);
        root.addContent(parameter);

        document.addContent(root);
    }


}
