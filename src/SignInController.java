import database.MusicianDB;
import database.UserDB;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class SignInController { // todo possibly change name, not really a controller yet

    public static void main(String[] args) throws  Exception{
        SignInController sic = new SignInController();
        sic.addUser("Ronald McDonald", "password", "musician");
    }

    public SignInController() {
        userDB = UserDB.getUserDB();
        musicianDB = MusicianDB.getMusicianDB();
    }

    public void addUser(String name, String password, String type) throws Exception {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        Element newUser = userDB.addUser(name, hashedPassword, type);
        if (type.equals("musician")) {
            musicianDB.addMusician(newUser);
        } else if (type.equals("band")) {
            System.out.println("No band db exists"); // todo bandDB.addBand(newUser)
        } else {
            throw new IllegalArgumentException("user type invalid");
        }
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
    private static UserDB userDB;
    private static MusicianDB musicianDB;
}
