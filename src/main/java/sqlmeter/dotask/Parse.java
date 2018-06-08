package sqlmeter.dotask;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Leonid Ivanov
 */
public class Parse {
    private Properties propConnection;
    private String sql;

    public Parse(String text, String sql) throws ParserConfigurationException, SAXException, IOException {
        this.sql=sql;
        InputSource source = new InputSource(new StringReader(text));
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        factory.setIgnoringElementContentWhitespace(true);
        
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(source);
        
        getNodeList(document,0);
    }
    
    private void getNodeList(Node doc, int level) { 
    NodeList list = doc.getChildNodes(); 
    for (int i = 0; i < list.getLength(); i++) { 
      Node node = list.item(i); // текущий нод 
        String nodeName = node.getNodeName();
        System.out.println(nodeName);
        
        if (nodeName.equals("connection")) {
            propConnection = new Properties();
            analizConnection(node);
        } else if (nodeName.equals("orderby")) {           
            analizOrderBy(node);
        }        
    } 
  } 
    
    private void analizConnection(Node node) {
        NamedNodeMap attributes = node.getAttributes();
        int numAttrs = attributes.getLength();
        for (int i = 0; i < numAttrs; i++) {
            Node attr = attributes.item(i);
            String attrName = attr.getNodeName();
            String attrValue = attr.getNodeValue();

            System.out.println("Found attribute: " + attrName + " with value: " + attrValue);
            propConnection.put(attrName, attrValue);
        }
    }
    
    private void analizOrderBy(Node node) {
        NamedNodeMap attributes = node.getAttributes();
        Node attr = attributes.getNamedItem("column");
        if (attr != null) {
            sql = sql + " ORDER BY " + attr.getNodeValue();
            System.out.println("Change sql: " + sql);
        }
    }
       
    public Properties getPropConnection(){
        return propConnection;
    }
    
    public String getSql(){
        return sql;
    }
    
}
