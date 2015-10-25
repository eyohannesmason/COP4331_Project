import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class SignInController {
    public SignInController() {

    }

    public static void main(String[] args) throws  Exception{
        String name="Jack Black", password = "mYpA5sw0Rd", type="musician";
        SignInController controller = new SignInController();
        controller.addUser(name, password, type);

        if (!controller.checkPassword("Jack Black", "badpass")) {
            System.out.println("Your password sucks");
        }

    }

    public void addUser(String name, String password, String type) throws Exception {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        Database.addUser(name, hashedPassword, type);
    }

    private Element getUser(String name) throws Exception {
        NodeList users = Database.getUsers();
        Element user = null;
        String currentName;
        for (int i=0; i<users.getLength(); i++) {
            user = (Element) users.item(i);
            currentName = user.getElementsByTagName("name").item(0).getTextContent();
            if (currentName.equals(name)) {
                return user;
            }
        }
        return user;
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
