package br.gov.ma.detran.examespraticosmobile.util;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Xml {

    public static String letTagXml(Element elem, String tagName) throws Exception {
        NodeList children = elem.getElementsByTagName(tagName);
        String result = null;
        //children, a tag pai que estamos lendo,
        // por exemplo a tag
        if (children == null) {
            return result;
        }
        //child, a tag que queremos recuperar o valor, por exemplo
        //a tag
        Element child = (Element) children.item(0);

        if (child == null) {
            return result;
        }
        //recuperamos o texto contido na tagName
        result = child.getTextContent();

        return result;
    }

}
