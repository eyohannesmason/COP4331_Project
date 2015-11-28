package database;

import junit.framework.TestCase;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DatabaseTest extends TestCase {

    private final Database db = new Database(); // default xml file is users.xml

    public void testGetItems() throws Exception {
        NodeList items = db.getItems();
        boolean allElements = false;
        Node currentNode = null;
        if (items != null) {
            allElements = true;
            for(int i=0; i<items.getLength(); i++) {
                currentNode = items.item(i);
                if (currentNode.getNodeType() != Node.ELEMENT_NODE) {
                    allElements = false;
                    break;
                }
            }
        }
        assertTrue(allElements);
    }

    public void testGetElementById() throws Exception {
        Element johnSmith = db.getElementById("1");
        boolean valid = johnSmith != null;
        String name = "";
        if (valid) {
            name = johnSmith.getElementsByTagName("name").item(0).getTextContent();
        }
        assertEquals("John Smith", name);
    }


}