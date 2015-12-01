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

    /**
     * Create a thread of messages between two users, and add it to the database.
     * @param to userID of the recipient
     * @param from userID of the sender
     * @param message contents of the first message
     * @throws Exception
     * @see #createThread(Document, String, String)
     */
    public void addThread(String to, String from, String message) throws Exception {
        Document document = getDocument(XML_PATH);
        Element threadElm = createThread(document, to, from);
        document.getDocumentElement().appendChild(threadElm);
        saveDocument(document);
        addMessage(to + "_" + from, from, message);
    }

    /**
     * Add a new message to an existing thread.
     * @param threadID ID of the thread (thread ID format: recipientID_senderID)
     * @param author userID of the message sender
     * @param message contents of the message
     * @throws Exception
     * @see #addMessage(Document, String, String, String)
     */
    public void addMessage(String threadID, String author, String message) throws Exception {
        Document document = getDocument(XML_PATH);
        addMessage(document, threadID, author, message);
    }

    /**
     * Same as other addMessage, but it expects a document as the first parameter
     * @param document XML document
     * @param threadID ID of the thread
     * @param author userID of the message sender
     * @param message contents of the message
     * @throws Exception
     * @see #createMessage(Document, String, String, String)
     */
    public void addMessage(Document document, String threadID, String author, String message) throws Exception {
        Element messageElm = createMessage(document, author, "00:00", message); // todo implement timestamp
        getElementById(document, threadID).appendChild(messageElm);
        saveDocument(document);
    }

    public NodeList getThreadsById(String threadID) throws Exception {//todo should return one Node
        String queryString = "//thread[@id='"+threadID+"']";
        return getNodeListByXPath(queryString);
    }

    public NodeList getThreadsByRecipient(String recipientID) throws Exception {
        String queryString = "//thread[@to='"+recipientID+"']";
        return getNodeListByXPath(queryString);
    }

    public NodeList getThreadsBySender(String senderID) throws Exception {
        String queryString = "//thread[@from='"+senderID+"']";
        return getNodeListByXPath(queryString);
    }

    /**
     * Get all threads where the sender sends the first message to the recipient
     * @param recipientID userID of the user receiving the first message in the thread
     * @param senderID userID of the user who sent the first message in the thread
     * @return NodeList containing message threads
     * @throws Exception
     * @see #getThreadsByPair(String, String, boolean)
     */
    public NodeList getThreadsToFrom(String recipientID, String senderID) throws Exception {
        return getThreadsByPair(recipientID, senderID, false);
    }

    /**
     * Get all threads where user A is the recipient while user B is the sender, or vice versa.
     * @param userA userID of one of the users in the message thread
     * @param userB userID of the other user in the message thread
     * @return NodeList containing threads
     * @throws Exception
     * @see #getThreadsByPair(String, String, boolean)
     */
    public NodeList getThreadsByPair(String userA, String userB) throws Exception {
        return getThreadsByPair(userA, userB, true);
    }

    private NodeList getThreadsByPair(String userA, String userB, boolean getUnion) throws Exception {
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
