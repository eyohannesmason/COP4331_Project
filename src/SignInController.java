import database.UserDB;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class SignInController { // todo possibly change name, not really a controller yet
    private static UserDB userDB;

    public SignInController() {
        userDB = UserDB.getUserDB();
    }

    public static void main(String[] args) throws  Exception{
        SignInController sic = new SignInController();
        userDB.addUser("Ronald McDonald", "password", "musician");
    }

    public void addUser(String name, String password, String type) throws Exception {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        Element newUser = userDB.addUser(name, hashedPassword, type);
        // todo create musician/band DB entry here
    }

    private Element getUser(String name) throws Exception {
        NodeList users = userDB.getUsers();
        Element user;
        String currentName;
        for (int i=0; i<users.getLength(); i++) {
            user = (Element) users.item(i);
            currentName = user.getElementsByTagName("name").item(0).getTextContent();
            if (currentName.equals(name)) {
                return user;
            }
        }
        return null;
    }

    public void logIn(String name, String password) throws Exception {
        Element user = getUser(name);
        if (user != null && checkPassword(name, password)) {
            userDB.setUserLoggedIn(user.getAttribute("id"));
        }
    }

    public boolean checkPassword(String name, String passAttempt) throws Exception {
        Element user = getUser(name);
        if (user != null) {
            String hash = user.getElementsByTagName("password").item(0).getTextContent();
            return BCrypt.checkpw(passAttempt, hash);
        }
        return false;
    }
}
