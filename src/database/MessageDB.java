package database;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class MessageDB extends Database {

    public static void main(String[] args) throws Exception{
        MessageDB db = MessageDB.getMessageDB();
        db.addThread("1", "2", "Hello there buttface!");
    }

    public static MessageDB getMessageDB() {
        return database;
    }

    public void addThread(String to, String from, String message) throws Exception {
        Document document = getDocument(XML_PATH);
        Element threadElm = createThread(document, to, from);
        document.getDocumentElement().appendChild(threadElm);
        saveDocument(document);
        addMessage(to + "_" + from, from, message);
    }

    public void addMessage(String threadID, String author, String message) throws Exception {
        Document document = getDocument(XML_PATH);
        addMessage(document, threadID, author, message);
    }

    public void addMessage(Document document, String threadID, String author, String message) throws Exception {
        Element messageElm = createMessage(document, author, "00:00", message); // todo implement timestamp
        getElementById(document, threadID).appendChild(messageElm);
        saveDocument(document);
    }

    public NodeList getThreadsByRecipient(String recipientID) throws Exception {
        String queryString = "//thread[@to='"+recipientID+"']";
        return getNodeListByXPath(queryString);
    }

    public NodeList getThreadsBySender(String senderID) throws Exception {
        String queryString = "//thread[@from='"+senderID+"']";
        return getNodeListByXPath(queryString);
    }

    public NodeList getThreadsById(String threadID) throws Exception {
        String queryString = "//thread[@id='"+threadID+"']";
        return getNodeListByXPath(queryString);
    }

    public NodeList getThreadsByPair(String userA, String userB, boolean getUnion) throws Exception {
        String queryString = "//thread[@to='"+userA+"' and @from='"+userB+"'";
        queryString += (getUnion) ? " or @to='"+userB+"' and @from='"+userA+"']" : "]";
        return getNodeListByXPath(queryString);
    }

    private Element createThread(Document document, String to, String from) {
        Element threadElm = document.createElement("thread");
        threadElm.setAttribute("id", to+"_"+from);
        threadElm.setAttribute("to", to);
        threadElm.setAttribute("from", from);
        return threadElm;
    }

    private Element createMessage(Document document, String author, String timestamp, String message) {
        Element messageElm = document.createElement("message");
        messageElm.setTextContent(message);
        messageElm.setAttribute("author", author);
        messageElm.setAttribute("timestamp", timestamp);
        return messageElm;
    }

    private static MessageDB database = new MessageDB();
    private MessageDB() {XML_PATH = "src/database/messages.xml";}
}
